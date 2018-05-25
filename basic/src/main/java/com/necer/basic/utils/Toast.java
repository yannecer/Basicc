package com.necer.basic.utils;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 *
 * 主项目Application添加Toast.init(this);
 *
 */

public class Toast {

    private static android.widget.Toast mToast;

    @SuppressLint("ShowToast")
    public static void init(Context context) {
        mToast = android.widget.Toast.makeText(context, "", android.widget.Toast.LENGTH_SHORT);
    }

    public static void show(String text) {
        mToast.setText(text);
        mToast.show();
    }

}
