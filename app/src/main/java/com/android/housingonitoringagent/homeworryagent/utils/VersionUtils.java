package com.android.housingonitoringagent.homeworryagent.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.android.housingonitoringagent.homeworryagent.App;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class VersionUtils {
    public static String getVersionName(){
        String versionName = "";
        PackageManager pm = App.getInstance().getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(App.getInstance().getPackageName(), 0);
            versionName = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
    public static int getVersionCode(){
        int versionCode = 0 ;
        PackageManager pm = App.getInstance().getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(App.getInstance().getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
