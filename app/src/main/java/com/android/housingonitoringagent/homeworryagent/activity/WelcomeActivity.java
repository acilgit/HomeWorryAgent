package com.android.housingonitoringagent.homeworryagent.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.android.housingonitoringagent.homeworryagent.App;
import com.android.housingonitoringagent.homeworryagent.Const;
import com.android.housingonitoringagent.homeworryagent.R;
import com.android.housingonitoringagent.homeworryagent.User;
import com.android.housingonitoringagent.homeworryagent.cache.PreferencesKey;
import com.android.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.android.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.android.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/22 0022.
 */
public class WelcomeActivity extends FragmentActivity {
    public static Activity instance;
    @Bind(R.id.ivSplash)
    ImageView mSplash;
    RequestQueue mVolleyRequestQueue;
    // 显示时间：3 秒
    private final int DISPLAY_TIME = 3 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        if (User.isLogin()) {
            verificationAccount();
        }else {
            nextStep();
        }
    }

    private void verificationAccount() {
        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.CHECKSESSIONID,
                new VolleyResponseListener() {
                    @Override
                    public void handleJson(JSONObject json) {
                        super.handleJson(json);
                        int result = json.getIntValue("resultCode");
                        switch (result){
                            case 0:
                                QBLToast.show("账号已过期，需重新登录");
                                User.logOut();
                                nextStep();
                                break;
                            case 1:
                               /*int content = json.getIntValue("content");
                                switch (content){
                                    case -1:
                                        QBLToast.show("账号已过期，需重新登录");
                                        User.logOut();
                                        nextStep();
                                        break;
                                    case 1:
                                        nextStep();
                                        break;
                                }*/
                                nextStep();
                                break;
                        }
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                QBLToast.show("账号已过期，需重新登录");
                User.logOut();
                nextStep();
            }
        }){
            @Override
            protected Map<String, String> getParams()  throws AuthFailureError {
                Map<String,String> params = super.getParams();
                params.put("sessionId",User.getSessionId());
                return params;
            }
        };
        getVolleyRequestQueue().add(request);
    }

    private void nextStep() {
        mSplash.postDelayed(new Runnable() {
            @Override
            public void run() {
                finishInstance();
                MainActivity.start(WelcomeActivity.this);
              /*  if (getPregetPreferences().getBoolean(PreferencesKey.Guide.READ, false)) {
                    MainActivity.start(WelcomeActivity.this);
                } else {
                    GuideActivity.start(WelcomeActivity.this);
                }*/
            }
        }, DISPLAY_TIME);
    }

    public static void finishInstance() {
        if (instance != null) {
            instance.finish();
        }
        instance = null;
    }

    private SharedPreferences getPregetPreferences() {
        return App.getInstance().getPreferences(PreferencesKey.Guide.NAME);
    }

    public RequestQueue getVolleyRequestQueue() {
        if (mVolleyRequestQueue == null) {
            mVolleyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mVolleyRequestQueue;
    }

    public void cancelVolleyRequestQueue() {
        if (mVolleyRequestQueue != null) {
            mVolleyRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelVolleyRequestQueue();
    }
}
