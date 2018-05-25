package com.necer.basic.view.picker;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.necer.basic.R;

public abstract class BasePicker extends Dialog {

    public BasePicker(Context context) {
        super(context, R.style.PickerDialog);

        setCanceledOnTouchOutside(true);//触摸屏幕取消窗体
        setCancelable(true);//按返回键取消窗体

        View pickView = LayoutInflater.from(context).inflate(getPickerView(), null);
        setContentView(pickView);

        initPickerView(pickView);

        TextView tv_sure = (TextView) pickView.findViewById(R.id.tv_sure);
        TextView tv_cancle = (TextView) pickView.findViewById(R.id.tv_cancle);

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSure();
                dismiss();
            }
        });

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


    protected abstract int getPickerView();
    protected abstract void initPickerView(View pickView);
    protected abstract void onSure();


    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

    }

}
