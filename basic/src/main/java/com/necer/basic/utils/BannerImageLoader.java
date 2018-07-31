package com.necer.basic.utils;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

public class BannerImageLoader extends ImageLoader {

    private int defaultDrawable;

    public BannerImageLoader(int defaultDrawable) {
        this.defaultDrawable = defaultDrawable;
    }

    public BannerImageLoader() {

    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageUtil.bind(path.toString(),imageView);
    }
}
