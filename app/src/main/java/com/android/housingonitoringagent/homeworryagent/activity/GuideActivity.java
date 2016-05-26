package com.android.housingonitoringagent.homeworryagent.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.housingonitoringagent.homeworryagent.App;
import com.android.housingonitoringagent.homeworryagent.R;
import com.android.housingonitoringagent.homeworryagent.adapter.GuideImageAdapter;
import com.android.housingonitoringagent.homeworryagent.cache.PreferencesKey;
import com.android.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.android.housingonitoringagent.homeworryagent.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity {
    @Bind(R.id.btnLogin)
    TextView mLogin;

    @Bind(R.id.guidePager)
    ViewPager mGuidePager;
    @Bind(R.id.dotBar)
    LinearLayout mDotBar;

    Animation mTranslateAnim;
    Animation mAlphaAnim;

//    public static void start(Activity activity) {
//        Intent intent = new Intent();
//        intent.setClass(activity, GuideActivity.class);
//
//        activity.startActivity(intent);
//        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initAnimations();
        initContentView();
        setListener();
    }

    private void initAnimations() {
        mTranslateAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mTranslateAnim.setDuration(getResources().getInteger(R.integer.medium_anim_time));

        mAlphaAnim = new AlphaAnimation(0.0f, 1.0f);
        mAlphaAnim.setDuration(getResources().getInteger(R.integer.medium_anim_time));
    }

    private void initContentView() {
        List<Integer> images = getImages();
        GuideImageAdapter adapter = new GuideImageAdapter(images);
        mGuidePager.setAdapter(adapter);

        if (images.size() <= 1) {
            mDotBar.setVisibility(View.GONE);
        }

        int dotPadding = UIUtils.dip2px(this, 16);
        for (int i = 0; i < mGuidePager.getAdapter().getCount(); i++) {
            ImageView dot = new ImageView(this);
            dot.setPadding(dotPadding, dotPadding, dotPadding, dotPadding);
            dot.setImageResource(R.drawable.dot);

            mDotBar.addView(dot);
        }

        setCurrentDot(0);
    }

    private void setListener() {
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                GuideActivity.this.onClick(v);
            }
        };
        mLogin.setOnClickListener(onClickListener);
        mGuidePager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                MainActivity.start(GuideActivity.this);
                finish();
                break;
        }
    }

    public List<Integer> getImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.guide_one);
        images.add(R.mipmap.guide_two);
        images.add(R.mipmap.guide_three);
        return images;
    }

    private void setCurrentDot(int position) {
        for (int i = 0; i < mDotBar.getChildCount(); i++) {
            ImageView dot = (ImageView) mDotBar.getChildAt(i);

            dot.setEnabled(i == position);
        }

        if (position == mGuidePager.getAdapter().getCount() - 1) {// 最后一张图
            mLogin.setVisibility(View.VISIBLE);
            mLogin.startAnimation(mAlphaAnim);
            onGuideRead();
        } else {
            mLogin.setVisibility(View.GONE);
        }
    }

    private void onGuideRead() {
        getPregetPreferences()
                .edit()
                .putBoolean(PreferencesKey.Guide.READ, true)
                .apply();
    }

    private SharedPreferences getPregetPreferences() {
        return App.getInstance().getPreferences(PreferencesKey.Guide.NAME);
    }
}