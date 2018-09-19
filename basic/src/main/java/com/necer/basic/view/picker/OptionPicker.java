package com.necer.basic.view.picker;

import android.content.Context;
import android.view.View;

import com.necer.basic.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionPicker extends BasePicker {

    WheelPicker wheelPicker;
    OnOptionSelectListener mOptionSelectListener;

    public OptionPicker(Context context) {
        super(context);
    }

    public OptionPicker setItem(Object[] items) {

        List<Object> objects = Arrays.asList(items);
        wheelPicker.replaceData(objects);
        return this;
    }

    public OptionPicker setItem(List list) {
        wheelPicker.replaceData(list);
        return this;
    }

    @Override
    protected int getPickerView() {
        return R.layout.picker_option;
    }

    @Override
    protected void initPickerView(View pickView) {
        wheelPicker = pickView.findViewById(R.id.wheel_picker);
        wheelPicker.setCyclic(true);

    }

    @Override
    protected void onSure() {

        if (mOptionSelectListener != null) {
            mOptionSelectListener.onSelect(wheelPicker.getCurrentItem(),wheelPicker.getCurrentPosition());
        }
    }

    public interface OnOptionSelectListener<T> {
        /**
         * 选择的内容和下标
         * @param index
         */
        void onSelect(T t, int index);
    }

    public OptionPicker setOnOptionSelectListener(OnOptionSelectListener onSelectListener) {
        this.mOptionSelectListener = onSelectListener;
        return this;
    }
}
