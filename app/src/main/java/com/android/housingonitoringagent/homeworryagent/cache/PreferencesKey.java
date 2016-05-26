package com.android.housingonitoringagent.homeworryagent.cache;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
public class PreferencesKey {
    public interface Guide {
        String NAME = "Guide";

        // 是否已经读过
        String READ = "Read";
    }
    // 用户信息
    public interface User {
        String NAME = "User";
        String LOGIN_STATE = "LoginState";    //boolean，是否已登录
        String SESSIONID = "sessionId"; //用户唯一标识
        String PHOTOS = "photos";
        String TYPE = "type";
        String MOBILEPHONE = "mobilephone";
        String NAMES = "name";
        String NICKNAME = "nickname";
        String ID = "id";
        String ACCOUNT = "account";
        String YZ = "yz";
        String sex = "sex";
        String OWNER = "owner";
        String RENTER = "renter";
    }
}
