package com.necer.basic.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.necer.basic.R;
import com.necer.basic.utils.ScreenUtils;


/**
 * Created by necer on 2017/11/20.
 */

public class NDialog extends AlertDialog {


    LinearLayout llContent;

    OnSureListenrer onSureListenrer;
    OnCancleListener onCancleListener;

    TextView tv_title;
    TextView tv_message;
    TextView tv_cancel;
    TextView tv_sure;

    public NDialog(@NonNull Context context) {
        super(context, R.style.AlertDialogStyle);

        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_n, null);
        setView(inflate);

        setCanceledOnTouchOutside(false);

        llContent = inflate.findViewById(R.id.ll_content);
        tv_title = inflate.findViewById(R.id.tv_title);
        tv_message = inflate.findViewById(R.id.tv_message);

        tv_cancel = inflate.findViewById(R.id.tv_cancel);
        tv_sure = inflate.findViewById(R.id.tv_sure);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCancleListener != null) {
                    onCancleListener.onCancel(NDialog.this);
                }
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSureListenrer != null) {
                    onSureListenrer.onSure(NDialog.this);
                }
            }
        });


        int with = ScreenUtils.widthPixels(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(with * 3 / 4, LinearLayout.LayoutParams.WRAP_CONTENT);

        llContent = inflate.findViewById(R.id.ll_content);
        llContent.setLayoutParams(layoutParams);


        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                tv_cancel.setVisibility(onCancleListener == null ? View.GONE : View.VISIBLE);
            }
        });

    }


    public NDialog setMessage(String title, String message) {
        tv_title.setVisibility(title.isEmpty() ? View.GONE : View.VISIBLE);
        tv_message.setVisibility(message.isEmpty() ? View.GONE : View.VISIBLE);
        tv_title.setText(title);
        tv_message.setText(message);
        return this;
    }

    public NDialog setOnSureListenrer(OnSureListenrer onSureListenrer) {
        this.onSureListenrer = onSureListenrer;
        return this;
    }

    public NDialog setOnCancleListener(OnCancleListener onCancleListener) {
        this.onCancleListener = onCancleListener;
        return this;
    }


    public interface OnSureListenrer {
        void onSure(NDialog nDialog);
    }

    public interface OnCancleListener{
        void onCancel(NDialog nDialog);
    }


}


