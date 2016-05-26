package com.android.housingonitoringagent.homeworryagent.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.housingonitoringagent.homeworryagent.R;


/**
 * Created by Administrator on 2015/11/16 0016.
 */
public class UIUtils {

    private UIUtils() { throw new UnsupportedOperationException("cannot be instantiated"); }


    /**
     * 返回当前屏幕是否为竖屏。
     * @param context
     * @return 当且仅当当前屏幕为竖屏时返回true,否则返回false。
     */
    public static boolean isScreenOrientationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }


    /**
     * 点击小图显示大图
     * @param context
     * @param view 需放大的图片
     * @param style dialog样式
     */
    public static void showBigImg(final Context context,final ImageView view,final int style) {
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final Dialog dialog = new Dialog(context,style);
                ImageView imgView = getView(context,view);
                dialog.setContentView(imgView);
                dialog.show();

                imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    /**
     * 显示的大图
     *
     * @param context
     * @param view 点击的图片
     * @return 大图控件
     */
    private static ImageView getView(Context context, ImageView view) {
        ImageView imgView = new ImageView(context);
        imgView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        view.setDrawingCacheEnabled(true);
        Drawable drawable = view.getDrawable();
        imgView.setImageDrawable(drawable);

        return imgView;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (pxValue / scale + 0.5f);
    }

    public static TextView createToolbarPrimaryTextButton(Context context, Integer id, Integer textSize, String text) {
        TextView button = createTextButton(context, id, textSize, text);

        // 设置在父布局右对齐
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        button.setLayoutParams(layoutParams);

        return button;
    }

    public static TextView createToolbarSecondaryTextButton(Context context, int anchor, Integer id, Integer textSize, String text) {
        TextView button = createTextButton(context, id, textSize, text);

        // 对齐到锚点
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.LEFT_OF, anchor);
        button.setLayoutParams(layoutParams);

        return button;
    }

    public static ImageView createToolbarPrimaryImageButton(Context context, Integer id, int src) {
        ImageView button = createImageButton(context, id, src);

        // 设置在父布局右对齐
        RelativeLayout.LayoutParams layoutParams
                = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        button.setLayoutParams(layoutParams);

        return button;
    }

    public static ImageView createToolbarSecondaryImageButton(Context context, int anchor, Integer id, int src) {
        ImageView button = createImageButton(context, id, src);

        // 对齐到锚点
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.LEFT_OF, anchor);
        button.setLayoutParams(layoutParams);

        return button;
    }

    public static TextView createTextButton(Context context, Integer id, Integer textSize, String text) {
        TextView button = new TextView(context);

        if (id != null) {
            button.setId(id);
        }
        if (textSize == null) {
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.abc_text_size_subtitle_material_toolbar));
        } else {
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        }
        // 设置文字
        button.setText(text);
        // 设置文字颜色
        button.setTextColor(context.getResources().getColorStateList(R.color.white));
        // 设置文字居中
        button.setGravity(Gravity.CENTER);
        // 设置内间距
        int padding = UIUtils.dip2px(context, 12);
        button.setPadding(padding, 0, padding, 0);
        // 设置最小宽度
        button.setMinWidth(UIUtils.dip2px(context, 44));

        return button;
    }

    public static ImageView createImageButton(Context context, Integer id, int src) {
        ImageView button = new ImageView(context);

        if (id != null) {
            button.setId(id);
        }
        // 设置按钮图片
        button.setImageResource(src);
        button.setAdjustViewBounds(true);
        // 设置按钮背景
        //noinspection ResourceType
        button.setBackgroundResource(R.drawable.selector_transparent);

        return button;
    }


    public static int getWindowHeight(Activity activity){
        WindowManager wm = activity.getWindowManager();
        return wm.getDefaultDisplay().getHeight();
    }
    public static int getWindowWidth(Activity activity){
        WindowManager wm = activity.getWindowManager();
        return wm.getDefaultDisplay().getWidth();
    }
}
