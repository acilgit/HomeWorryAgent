package com.android.housingonitoringagent.homeworryagent.extents;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/1/11 0011.
 */
public class BaseActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    RequestQueue mVolleyRequestQueue;
    BroadcastReceiver mFinishReceiver;
    static BaseActivity thisActivity;

    public static final String ACTION_FINISH_ACTIVITY = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        registerFinishReceiver();
//        App.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
//        App.getInstance().removeActivity(this);
        cancelVolleyRequestQueue();
    }

    /**
     *
     * @param activity
     * @param fromVisitor 是否来自游客用户的登录
     */
    public static void start(Activity activity, boolean fromVisitor) {
        if (thisActivity!=null) {
            Intent intent = new Intent();
            intent.setClass(thisActivity, activity.getClass());
            intent.putExtra("FromVisitor", fromVisitor);
            activity.startActivity(intent);
        }
    }

    public static void start(Activity activity) {
        if (thisActivity!=null) {
            Intent intent = new Intent();
            intent.setClass(thisActivity, activity.getClass());
            activity.startActivity(intent);
        }
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

    public void showProgressDialog(CharSequence text) {
        showProgressDialog(text, false);
    }

    public void showProgressDialog(CharSequence text, boolean cancelable) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        } else {
            mProgressDialog.dismiss();
        }

        mProgressDialog.setCanceledOnTouchOutside(cancelable);
        if (cancelable) {
            mProgressDialog.setOnCancelListener(null);
        } else {
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
        }

        mProgressDialog.setMessage(text);
        mProgressDialog.show();
    }

    public void setProgressDialogMessage(int resId) {
        setProgressDialogMessage(getString(resId));
    }

    public void setProgressDialogMessage(CharSequence text) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(text);
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void hideSoftInput() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public RequestQueue getVolleyRequestQueue() {
        if (mVolleyRequestQueue == null) {
            mVolleyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mVolleyRequestQueue;
    }

    // 注册广播接收器
    // 当接收到广播时，退出Activity
    protected void registerFinishReceiver() {
        mFinishReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_FINISH_ACTIVITY);
        // 注册广播监听器
        LocalBroadcastManager.getInstance(this).registerReceiver(mFinishReceiver, filter);
    }

    /**
     * @param activity               设置的activity
     * @param drawerLayout           设置的控件
     * @param displayWidthPercentage 滑动范围
     */
    /*protected static void setDrawerLeftEdgeSize(Activity activity,
                                                DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null)
            return;
        try {
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField(
                    "mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField
                    .get(drawerLayout);

            Field edgeSizeField = leftDragger.getClass().getDeclaredField(
                    "mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize,
                    (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
            Log.d("NoSuchFieldException", e.toString());
        } catch (IllegalArgumentException e) {
            Log.d("IllegalArgumentExceptio", e.toString());
        } catch (IllegalAccessException e) {
            Log.d("IllegalAccessException", e.toString());
        }
    }*/
}
