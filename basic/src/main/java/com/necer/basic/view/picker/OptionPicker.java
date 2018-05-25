package com.necer.basic.view.picker;

import android.content.Context;
import android.view.View;

import com.necer.basic.R;

import java.util.ArrayList;
import java.util.List;

public class OptionPicker extends BasePicker {

    WheelPicker wheelPicker;
    List mList;
    OnOptionSelectListener mOptionSelectListener;

    public OptionPicker(Context context) {
        super(context);
    }

    public OptionPicker setItem(Object[] items) {
        mList.clear();
        for (int i = 0; i < items.length; i++) {
            mList.add(items[i]);
        }
        return this;
    }

    public OptionPicker setItem(List list) {
        mList.clear();
        mList.addAll(list);
        return this;
    }

    @Override
    protected int getPickerView() {
        return R.layout.picker_option;
    }

    @Override
    protected void initPickerView(View pickView) {
        wheelPicker = pickView.findViewById(R.id.wheel_picker);
        mList = new ArrayList<>();

        wheelPicker.setCyclic(true);

        wheelPicker.setDataList(mList);
    }

    @Override
    protected void onSure() {
        int currentPosition = wheelPicker.getCurrentPosition();
        Object o = mList.get(currentPosition);
        if (mOptionSelectListener != null) {
            mOptionSelectListener.onSelect(o,currentPosition);
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
