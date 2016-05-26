package com.android.housingonitoringagent.homeworryagent.utils.net;


import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class ResponseModel implements Serializable {

    @JSONField(name = "resultCode")
    private int resultCode = Integer.MIN_VALUE;
    @JSONField(name = "message")
    private String message;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
