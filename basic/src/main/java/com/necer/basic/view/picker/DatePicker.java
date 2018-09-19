package com.necer.basic.view.picker;

import android.content.Context;
import android.view.View;

import com.necer.basic.R;
import com.necer.basic.base.MyLog;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatePicker extends BasePicker {


    private int beganYear;
    private int endYear;


    private WheelPicker wheelYear;
    private WheelPicker wheelMonth;
    private WheelPicker wheelDay;

    private OnDatePickListener mDatePickListener;

    public DatePicker(Context context) {
        super(context);
    }

    @Override
    protected int getPickerView() {
        return R.layout.picker_date;
    }

    @Override
    protected void initPickerView(View pickView) {
        wheelYear = pickView.findViewById(R.id.wl_year);
        wheelMonth = pickView.findViewById(R.id.wl_month);
        wheelDay = pickView.findViewById(R.id.wl_day);


        wheelYear.setCyclic(false);
        wheelMonth.setCyclic(true);
        wheelDay.setCyclic(true);


        beganYear = 1900;
        endYear = 2050;
        initData();
    }

    private void initData() {

        setDateTime(new DateTime());


        wheelYear.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener<String>() {
            @Override
            public void onWheelSelected(WheelPicker wheelPicker, String item, int position) {
                calculateDay(wheelYear.getCurrentItem().toString(), wheelMonth.getCurrentItem().toString(), wheelDay.getCurrentItem().toString());
            }
        });

        wheelMonth.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener<String>() {
            @Override
            public void onWheelSelected(WheelPicker wheelPicker, String item, int position) {
                calculateDay(wheelYear.getCurrentItem().toString(), wheelMonth.getCurrentItem().toString(), wheelDay.getCurrentItem().toString());

            }
        });

        wheelDay.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener<String>() {
            @Override
            public void onWheelSelected(WheelPicker wheelPicker, String item, int position) {
                //selectDay = item;
            }
        });
    }

    private void setDateTime(DateTime dateTime) {

        List<String> yearsList = new ArrayList<>();
        List<String> monthsList = new ArrayList<>();
        List<String> daysList = new ArrayList<>();


        for (int i = beganYear; i <= endYear; i++) {
            yearsList.add(String.valueOf(i));
        }
        for (int i = 1; i <= 12; i++) {
            monthsList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        for (int i = 1; i <= dateTime.dayOfMonth().getMaximumValue(); i++) {
            daysList.add(i < 10 ? "0" + i : String.valueOf(i));
        }


        wheelYear.replaceData(yearsList);
        wheelMonth.replaceData(monthsList);
        wheelDay.replaceData(daysList);


        wheelYear.setCurrentPosition(yearsList.indexOf(dateTime.getYear() + ""));
        wheelMonth.setCurrentPosition(monthsList.indexOf(dateTime.getMonthOfYear() < 10 ? ("0" + dateTime.getMonthOfYear()) : dateTime.getMonthOfYear() + ""));
        wheelDay.setCurrentPosition(daysList.indexOf(dateTime.getDayOfMonth() < 10 ? ("0" + dateTime.getDayOfMonth()) : dateTime.getDayOfMonth() + ""));
    }


    private void calculateDay(String year, String month, String day) {

        //当前显示的年月
        DateTime dateTime = new DateTime(year + "-" + month + "-01");

        //选中当月的天数
        int maximumValue = dateTime.dayOfMonth().getMaximumValue();

        List<String> daysList = new ArrayList<>();
        for (int i = 1; i <= maximumValue; i++) {
            daysList.add(i < 10 ? "0" + i : String.valueOf(i));
        }

        wheelDay.replaceData(daysList);
        int dayIndex = Integer.parseInt(day);

        if (dayIndex >= daysList.size()) {
            wheelDay.setCurrentPosition(daysList.size() - 1);
        } else {
            wheelDay.setCurrentPosition(dayIndex - 1);
        }
    }


    public DatePicker setSelect(String select) {
        if (select == null || "".equals(select)) {
            return this;
        }

        setDateTime(new DateTime(select));

        return this;
    }


    @Override
    protected void onSure() {
        if (mDatePickListener != null) {
            mDatePickListener.onSelect(wheelYear.getCurrentItem().toString(), wheelMonth.getCurrentItem().toString(), wheelDay.getCurrentItem().toString());
        }
    }

    public interface OnDatePickListener {
        void onSelect(String year, String month, String day);
    }

    public DatePicker setOnDatePickListener(OnDatePickListener onDatePickListener) {
        this.mDatePickListener = onDatePickListener;
        return this;
    }

    public void setYearInterval(int beganYear, int endYear) {
        this.beganYear = beganYear;
        this.endYear = endYear;
        initData();
    }
}
