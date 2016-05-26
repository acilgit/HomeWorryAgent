package com.android.housingonitoringagent.homeworryagent;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.android.housingonitoringagent.homeworryagent.activity.LoginActivity;
import com.android.housingonitoringagent.homeworryagent.activity.MainActivity;
import com.android.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.android.housingonitoringagent.homeworryagent.utils.FileUtil;
import com.android.housingonitoringagent.homeworryagent.utils.ThreadPool;
import com.android.housingonitoringagent.homeworryagent.utils.net.FrescoFactory;
import com.android.housingonitoringagent.homeworryagent.utils.net.VolleyManager;
import com.android.volley.toolbox.ImageLoader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by XY on 2016/5/25.
 */
public class App extends Application {

    private static App instance;
    private List<Activity> activities = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);


        Fresco.initialize(this, FrescoFactory.getImagePipelineConfig(this));

        //初始化，初始化前请添加其它相关第三方代码
        EMClient.getInstance().init(getApplicationContext(), options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    public static App getInstance() {
        if (instance==null) {
            return new App();
        }
        return instance;
    }
    /**
     * 结束所有Activity
     */
    public void finishAllActivities() {
        // 发送广播，接收到广播的Activity将会执行finish()方法
        LocalBroadcastManager
                .getInstance(this)
                .sendBroadcast(new Intent(BaseActivity.ACTION_FINISH_ACTIVITY));
    }


    public void restart() {
        // 销毁线程池
        ThreadPool.restart();
        // 结束所有Activity
        finishAllActivities();
//         启动登录Activity
        LoginActivity.start(this);
    }

    public void restart(Activity activity) {
        // 销毁线程池
        ThreadPool.restart();
        // 结束所有Activity
        finishAllActivities();
//         启动登录Activity
        MainActivity.start(activity);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        Fresco.shutDown();
    }


    public SharedPreferences getPreferences(String name) {
        return getSharedPreferences(name, Context.MODE_PRIVATE);
    }


    /**
     * 清除应用缓存
     */
    public void clearAppCache() {
//        // 清除ImageLoader缓存的图片
//        ImageLoader.getInstance().clearDiskCache();
        // 删除Cache目录
        FileUtil.deleteFilesByDirectory(getCacheDir());
        FileUtil.deleteFilesByDirectory(getExternalCacheDir());
    }

    /**
     * 清除应用数据
     */
    public void clearAppData() {
        // 清除所有用户数据
        clearUserData();
        // 删除目录
        FileUtil.deleteFilesByDirectory(getFilesDir());
        FileUtil.deleteFilesByDirectory(getExternalFilesDir(null));
    }

    /**
     * 清除用户数据
     */
    public static void clearUserData() {
        // 清空用户信息
        User.clear();
        removeCookie(getInstance());
    }

    /**
     * 删除Cookie
     *
     * @param context 上下文
     */
    private static void removeCookie(Context context) {
        CookieSyncManager.createInstance(context);
        if (VolleyManager.getCookies() != null) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            CookieSyncManager.getInstance().sync();
        }
    }
}
