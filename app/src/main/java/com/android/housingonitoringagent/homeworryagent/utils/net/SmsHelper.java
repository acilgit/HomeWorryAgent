package com.android.housingonitoringagent.homeworryagent.utils.net;


import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.android.housingonitoringagent.homeworryagent.Const;
import com.android.housingonitoringagent.homeworryagent.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsHelper {

    private static final String SMS_URI = "content://sms/";
    private Context mContext;
    // Volley请求队列
    private RequestQueue mRequestQueue;

    // 短信内容观察者
    private SMSContentObserver mSMSObserver;

    private SmsListener mListener;

    public SmsHelper(Context context, RequestQueue requestQueue) {
        mContext = context;
        mRequestQueue = requestQueue;
    }

    public void setListener(SmsListener listener) {
        mListener = listener;
    }

    /**
     *
     * @param phone 电话号码
     */
    public void getVerificationCode(final String phone) {
        if (mListener == null) {
            Log.e(getClass().getName(), "The SMSListener is null !");

            return;
        }

        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.SENDSMSCAPTCHA,
                new VolleyResponseListener() {
                    @Override
                    public void handleJson(JSONObject json) {
                        super.handleJson(json);
                        try {
                            int result = json.getIntValue("resultCode");
                            String  msg = json.getString("message");
                            switch (result) {
                                case 1:
                                    mListener.onRequestVerificationCode(true, null);
                                    break;
                                default:
                                    mListener.onRequestVerificationCode(false, msg);
                                    break;
                            }

                        } catch (Exception e) {
                            mListener.onRequestVerificationCode(false, mContext.getString(R.string.get_verification_code_failure));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mListener.onNetworkException();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = super.getParams();
                params.put("phoneNumber", phone);
                return params;
            }
        };
        mRequestQueue.add(request);
    }

    /**
     * 开始监听短信
     */
    public void beginListenSms() {
        if (mListener == null) {
            Log.e(getClass().getName(), "The SMSListener is null !");

            return;
        }

        if (mSMSObserver == null) {
            mSMSObserver = new SMSContentObserver(new Handler());
            mContext.getContentResolver().registerContentObserver(Uri.parse(SMS_URI), true, mSMSObserver);
        }
    }

    /**
     * 结束监听短信
     */
    public void endListenSms() {
        if (mSMSObserver != null) {
            mContext.getContentResolver().unregisterContentObserver(mSMSObserver);
            mSMSObserver = null;
        }
    }

    /**
     * 回调：接收短信
     * @param smsBody 短信内容
     */
    private void onSMSReceived(String smsBody) {
        mListener.onReceivedSms(smsBody);
        String verificationCode = readVerificationCode(smsBody);
        mListener.onReceivedVerificationCode(verificationCode);
    }

    /**
     * 从字符串中截取连续4位数字组合 ([0-9]{" + 4 + "})截取4位数字 进行前后断言不能出现数字 用于从短信中获取动态密码
     *
     * @param sms 短信内容
     * @return 截取得到的6位动态密码
     */
    private String readVerificationCode(String sms) {
        // 4是验证码的位数
        Pattern continuousNumberPattern = Pattern.compile("(?<![0-9])([0-9]{"
                + 6 + "})(?![0-9])");
        Matcher m = continuousNumberPattern.matcher(sms);
        String verificationCode = "";
        while (m.find()) {
            verificationCode = m.group();
        }

        return verificationCode;
    }

    protected class SMSContentObserver extends ContentObserver {

        public SMSContentObserver(Handler handler) {
            super(handler);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

//            if (PermissionUtil.check(Manifest.permission.READ_SMS)) {
//                return;
//            }

            Cursor smsCursor = null;
            try {
                smsCursor = new CursorLoader(mContext, Uri.parse("content://sms/inbox"),
                        new String[]{"_id", "address", "read", "body"},
                        " address=? and read=?",
                        new String[]{"1069058310965", "0"}, "_id desc")// 按id排序，如果按date排序的话，修改手机时间后，读取的短信就不准了
                        .loadInBackground();
                if (smsCursor != null) {
                    if (smsCursor.getCount() > 0) {
                        ContentValues values = new ContentValues();
                        values.put("read", "1"); // 修改短信为已读模式
                        smsCursor.moveToNext();
                        int smsBodyColumn = smsCursor.getColumnIndex("body");
                        String smsBody = smsCursor.getString(smsBodyColumn);

                        onSMSReceived(smsBody);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (smsCursor != null) {
                    smsCursor.close();
                }
            }
        }
    }

    public interface SmsListener {
        void onRequestVerificationCode(boolean success, String reason);
        void onNetworkException();
        void onReceivedSms(String smsBody);
        void onReceivedVerificationCode(String verificationCode);
    }
}
