package com.android.housingonitoringagent.homeworryagent.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class GuideImageAdapter extends PagerAdapter {

    List<Integer> mImages;

    public GuideImageAdapter(List<Integer> images) {
        mImages = images;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        String imageUri = "drawable://" + mImages.get(position);
        imageView.setImageResource(mImages.get(position));
//        ImageLoader.getInstance().displayImage(imageUri, imageView, ImageLoaderUtil.displayOptions);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return mImages == null ? 0 : mImages.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
