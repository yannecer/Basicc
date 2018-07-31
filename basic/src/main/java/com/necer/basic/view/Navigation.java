package com.necer.basic.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.necer.basic.R;



/**
 * Created by necer on 2018/5/25.
 */

public class Navigation extends FrameLayout {

    ImageView iv_left;
    TextView tv_title;
    TextView tv_right;


    public Navigation(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_navigation, this);
        iv_left = findViewById(R.id.iv_left);
        tv_title = findViewById(R.id.tv_title);
        tv_right = findViewById(R.id.tv_right);
    }

    public Navigation title(String title) {
        tv_title.setText(title);
        tv_title.setVisibility(VISIBLE);
        return this;
    }

    public Navigation titleColor(int color) {
        tv_title.setTextColor(color);
        return this;
    }

    public Navigation rightTextColor(int color) {
        tv_right.setTextColor(color);
        return this;
    }

    public Navigation rightText(String rightText) {

        tv_right.setVisibility(VISIBLE);
        tv_right.setText(rightText);
        return this;
    }

    public Navigation left(boolean show) {
        iv_left.setVisibility(show ? VISIBLE : INVISIBLE);
        iv_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
        return this;
    }

    public Navigation left(boolean show, OnClickListener onClickListener) {
        iv_left.setVisibility(show ? VISIBLE : INVISIBLE);
        iv_left.setOnClickListener(onClickListener);
        return this;
    }

    public Navigation rightText(String rightText, final OnClickListener onClickListener) {

        tv_right.setVisibility(VISIBLE);
        tv_right.setText(rightText);
        tv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
            }
        });

        return this;
    }

    public Navigation backgroundColor(int color) {
        setBackgroundColor(color);
        return this;
    }


}
