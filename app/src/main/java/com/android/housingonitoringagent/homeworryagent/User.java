package com.android.housingonitoringagent.homeworryagent;

import android.app.Activity;

import com.android.housingonitoringagent.homeworryagent.activity.LoginActivity;
import com.android.housingonitoringagent.homeworryagent.beans.UserBean;
import com.android.housingonitoringagent.homeworryagent.cache.PreferencesKey;
import com.android.housingonitoringagent.homeworryagent.cache.SecurityStorage;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class User {
    // 安全存储对象
    public static SecurityStorage storage = new SecurityStorage(App.getInstance(), PreferencesKey.User.NAME);

    public static void clear() {
        storage.clear();
    }

    /**
     * @param activity
     * @return 是否已登录
     */
    public static boolean tryLogin(Activity activity) {
        if (isLogin()) {
            return true;
        } else {
            LoginActivity.start(activity,true);
            return false;
        }
    }

    /**
     *
     * @return 是否已登录
     */
    public static boolean isLogin() {
        return storage.getBoolean(PreferencesKey.User.LOGIN_STATE, false);
    }

    public static void logIn(String provider, UserBean loginResp) {
        setSessionId(loginResp.getContent().getSessionId());
        setHeadUrl(loginResp.getContent().getUser().getAvatar());
        setUsername(loginResp.getContent().getUser().getName());
        setNickname(loginResp.getContent().getUser().getNickname());
        setUserId(loginResp.getContent().getUser().getId());
        setAccount(loginResp.getContent().getUser().getMobilephone());
        setMobilephone(loginResp.getContent().getUser().getMobilephone());
        //setType(loginResp.getContent().getUser().);
        //setUserYZ(loginResp.getContent().getUser().get);
        setUserSex(String.valueOf(loginResp.getContent().getUser().getSex()));
        setIsOwner(loginResp.getContent().getUser().isOwner());
        setIsRenter(loginResp.getContent().getUser().isRenter());
        setLoginState(true);

    }


    protected static void setLoginState(boolean loginState) {
        storage.put(PreferencesKey.User.LOGIN_STATE, loginState);
    }

    public static String getNeighbours() {
        return storage.getString("neighboursJson", "");
    }
    public static void setNeighbours(String neighboursJson) {
        storage.put("neighboursJson", neighboursJson);
    }

    public static String getSessionId() {
        return storage.getString(PreferencesKey.User.SESSIONID, null);
    }
    public static void setSessionId(String memberId) {
        storage.put(PreferencesKey.User.SESSIONID, memberId);
    }

    public static void setType(int type) {
        storage.put(PreferencesKey.User.TYPE, type);
    }

    public static void setMobilephone(String mobilephone) {
        storage.put(PreferencesKey.User.MOBILEPHONE, mobilephone);
    }

    public static void setHeadUrl(String headUrl) {
        storage.put(PreferencesKey.User.PHOTOS, headUrl);
    }
    public static void setUserSex(String sex) {
        storage.put(PreferencesKey.User.sex, sex);
    }


    public static void setAccount(String account) {
        storage.put(PreferencesKey.User.ACCOUNT, account);
    }

    public static void setUserId(String userId) {
        storage.put(PreferencesKey.User.ID, userId);
    }

    public static void setNickname(String nickname) {
        storage.put(PreferencesKey.User.NICKNAME, nickname);
    }

    public static void setUsername(String username) {
        storage.put(PreferencesKey.User.NAMES, username);
    }

    public static void setUserYZ(boolean YZ) {
        storage.put(PreferencesKey.User.YZ, YZ);
    }

    // 涉及到清除缓存等IO操作，建议在子线程里调用
    public static void logOut() {
        App.self.clearAppCache();
        App.self.clearAppData();

    }

    public static int getType() {
        return storage.getInt(PreferencesKey.User.TYPE, 1);
    }

    public static String getUsername() {
        return storage.getString(PreferencesKey.User.NAMES, null);
    }

    public static String getMobilephone() {
        return storage.getString(PreferencesKey.User.MOBILEPHONE, null);
    }
    public static String getHeadUrl() {
        return storage.getString(PreferencesKey.User.PHOTOS, null);
    }
    public static String getAccount() {
        return storage.getString(PreferencesKey.User.ACCOUNT, null);
    }
    public static String getUserId() {
        return storage.getString(PreferencesKey.User.ID, null);
    }
    public static String getNickname() {
        return storage.getString(PreferencesKey.User.NICKNAME, null);
    }

    public static boolean getUserYZ(){
        return storage.getBoolean(PreferencesKey.User.YZ, null);
    }

    public static String getUserSex() {
        return storage.getString(PreferencesKey.User.sex, null);
    }

    public static void setIsOwner(boolean isOwner){
        storage.put(PreferencesKey.User.OWNER,isOwner);
    }
    public static boolean getIsOwner(){
        return storage.getBoolean(PreferencesKey.User.OWNER,false);
    }
    public static void setIsRenter(boolean isRenter){
        storage.put(PreferencesKey.User.RENTER,isRenter);
    }
    public static boolean getIsRenter(){
        return storage.getBoolean(PreferencesKey.User.RENTER,false);
    }
}
