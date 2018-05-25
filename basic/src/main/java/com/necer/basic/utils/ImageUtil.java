package com.necer.basic.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.necer.basic.R;
import com.necer.basic.base.BaseApplication;

import butterknife.Bind;

public class ImageUtil {


    public static void bind(String imageUrl, ImageView imageView, int placeDrawable, int errorDrawable) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeDrawable)
                .error(errorDrawable)
                .priority(Priority.HIGH);

        Glide.with(BaseApplication.application).load(imageUrl).apply(options).into(imageView);
    }


    public static void bind(String imageUrl, ImageView imageView, int placeDrawable) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeDrawable)
                .error(placeDrawable)
                .priority(Priority.HIGH);
        Glide.with(BaseApplication.application).load(imageUrl).apply(options).into(imageView);
    }

    public static void bind(String imageUrl, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .priority(Priority.HIGH);
        Glide.with(BaseApplication.application).load(imageUrl).apply(options).into(imageView);
    }


}
