package com.necer.basic.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.necer.basic.R;


public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.loading_dialog);
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
    }

}
