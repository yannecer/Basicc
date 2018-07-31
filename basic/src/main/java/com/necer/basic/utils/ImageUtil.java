package com.necer.basic.utils;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.necer.basic.base.BaseApplication;


public class ImageUtil {


    public static void bind(String imageUrl, ImageView imageView, int placeDrawable, int errorDrawable) {
        /*RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeDrawable)
                .error(errorDrawable)
                .priority(Priority.HIGH);

        Glide.with(BaseApplication.application).load(imageUrl).apply(options).into(imageView);*/

        Glide.with(BaseApplication.application)
                .load(imageUrl)
                .centerCrop()
                .placeholder(placeDrawable)//图片加载出来前，显示的图片
                .error(errorDrawable)//图片加载失败后，显示的图片
                .into(imageView);

    }


    public static void bind(String imageUrl, ImageView imageView, int placeDrawable) {
        /*RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeDrawable)
                .error(placeDrawable)
                .priority(Priority.HIGH);
        Glide.with(BaseApplication.application).load(imageUrl).apply(options).into(imageView);*/
        bind(imageUrl, imageView, placeDrawable, placeDrawable);
    }

    public static void bind(String imageUrl, ImageView imageView) {
        /*RequestOptions options = new RequestOptions()
                .centerCrop()
                .priority(Priority.HIGH);
        Glide.with(BaseApplication.application).load(imageUrl).apply(options).into(imageView);*/

        Glide.with(BaseApplication.application)
                .load(imageUrl)
                .centerCrop()
                .into(imageView);
    }


}
