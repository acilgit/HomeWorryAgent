package com.android.housingonitoringagent.homeworryagent.utils.net;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.housingonitoringagent.homeworryagent.App;
import com.android.housingonitoringagent.homeworryagent.User;
import com.android.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
import com.android.volley.Response;


public abstract class VolleyResponseListener implements Response.Listener<String> {

    // 状态码 : 登录已过期
    static final int NOT_LOGIN = -1;

    @Override
    public void onResponse(String response) {
        Log.d(VolleyResponseListener.class.getName(), "返回值：" + response);
        JSONObject json;


        try {
            json = JSON.parseObject(response);
        } catch (Exception e) {
            Log.e(VolleyResponseListener.class.getName(), e.getMessage());
            Log.e(VolleyResponseListener.class.getName(), "转换返回值为JSON时失败");
            e.printStackTrace();
            json = new JSONObject();
        }

        try {
            ResponseModel respModel = JSON.toJavaObject(json, ResponseModel.class);
            Log.d(VolleyResponseListener.class.getName()+"yyy", "message : " + respModel.getMessage());
            if (respModel.getResultCode()==NOT_LOGIN) {
                QBLToast.show("登录失败，请重新登录");
                // 退出用户登录
                User.logOut();
                // 重启应用
                App.getInstance().restart();
                return;
            }
            Log.d(VolleyResponseListener.class.getName(), "message : " + respModel.getMessage());
        } catch (Exception e) {
            Log.e(VolleyResponseListener.class.getName(), e.toString());
            e.printStackTrace();
        }

        try {
            handleJson(json);
        } catch (Exception e) {
            Log.e(VolleyResponseListener.class.getName(), e.toString());
            e.printStackTrace();
        }
    }

    public void handleJson(JSONObject json) {

    }
}
