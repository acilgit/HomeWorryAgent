package com.android.housingonitoringagent.homeworryagent.utils.net;

import com.android.housingonitoringagent.homeworryagent.User;
import com.android.housingonitoringagent.homeworryagent.utils.Md5Utils;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class VolleyStringRequest extends StringRequest {

    public VolleyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public VolleyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return VolleyRetryPolicy.getDefault();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new VolleyParams();
        if (User.isLogin()) {
            params.put("sessionId", User.getSessionId());
            params.put("sign", Md5Utils.md5Encrypt());
        }
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> header = new HashMap<>();
        return header;
    }
}
