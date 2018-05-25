package com.necer.basic.view.picker;

import android.content.Context;
import android.view.View;

import com.necer.basic.R;
import com.necer.basic.base.MyLog;

import java.util.ArrayList;
import java.util.Calendar;

public class DatePicker extends BasePicker {


    private ArrayList<String> yearsList;
    private ArrayList<String> monthsList;
    private ArrayList<String> daysList;

    private int beganYear;
    private int endYear;


    private WheelPicker wheelYear;
    private WheelPicker wheelMonth;
    private WheelPicker wheelDay;

    private String selectYear;
    private String selectMonth;
    private String selectDay;

    private Calendar calendar;
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

        calendar = Calendar.getInstance();
        yearsList = new ArrayList<>();
        monthsList = new ArrayList<>();
        daysList = new ArrayList<>();

        wheelYear.setCyclic(false);
        wheelMonth.setCyclic(true);
        wheelDay.setCyclic(true);

        wheelYear.setDataList(yearsList);
        wheelMonth.setDataList(monthsList);
        wheelDay.setDataList(daysList);

        beganYear = 1900;
        endYear = 2050;
        initData();
    }

    private void initData() {
        yearsList.clear();
        monthsList.clear();
        daysList.clear();

        for (int i = beganYear; i <= endYear; i++) {
            yearsList.add(String.valueOf(i));
        }
        for (int i = 1; i <= 12; i++) {
            monthsList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        for (int i = 1; i <= 31; i++) {
            daysList.add(i < 10 ? "0" + i : String.valueOf(i));
        }


        selectYear = String.valueOf(calendar.get(Calendar.YEAR));
        selectMonth = calendar.get(Calendar.MONTH) + 1 < 10 ? "0" + (calendar.get(Calendar.MONTH) + 1) : String.valueOf(calendar.get(Calendar.MONTH) + 1);
        selectDay = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        wheelYear.setCurrentPosition(yearsList.indexOf(selectYear));
        wheelMonth.setCurrentPosition(monthsList.indexOf(selectMonth));

        calculateDate();

        wheelYear.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener<String>() {
            @Override
            public void onWheelSelected(String item, int position) {
                selectYear = item;
                calculateDate();
            }
        });

        wheelMonth.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener<String>() {
            @Override
            public void onWheelSelected(String item, int position) {
                selectMonth = item;
                calculateDate();
            }
        });

        wheelDay.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener<String>() {
            @Override
            public void onWheelSelected(String item, int position) {
                selectDay = item;
            }
        });
    }

    public DatePicker setSelect(String select) {
        if (select==null || "".equals(select) ) {
            return this;
        }

        String[] split = select.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];


        int yearIndex = yearsList.indexOf(year);
        int monthIndex = monthsList.indexOf(month);
        int dayIndex = daysList.indexOf(day);

        wheelYear.setCurrentPosition(yearIndex == -1 ? 0 : yearIndex);
        wheelMonth.setCurrentPosition(monthIndex == -1 ? 0 : monthIndex);
        wheelDay.setCurrentPosition(dayIndex == -1 ? 0 : dayIndex);

        return this;
    }


    @Override
    protected void onSure() {
        if (mDatePickListener != null) {
            selectYear = yearsList.get(wheelYear.getCurrentPosition());
            selectMonth = monthsList.get(wheelMonth.getCurrentPosition());
            selectDay = daysList.get(wheelDay.getCurrentPosition());
            mDatePickListener.onSelect(selectYear, selectMonth, selectDay);
        }
    }

    public interface OnDatePickListener {
        void onSelect(String year, String month, String day);
    }

    public DatePicker setOnDatePickListener(OnDatePickListener onDatePickListener) {
        this.mDatePickListener = onDatePickListener;
        return this;
    }

    private void calculateDate() {
        daysList.clear();
        int month = Integer.parseInt(selectMonth);
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            for (int i = 1; i <= 31; i++) {
                daysList.add(i < 10 ? "0" + i : String.valueOf(i));
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            for (int i = 1; i <= 30; i++) {
                daysList.add(i < 10 ? "0" + i : String.valueOf(i));
            }
            if (selectDay.equals("31")) {
                selectDay = "30";
            }
        } else {
            // 是否PING年
            if ((Integer.parseInt(selectYear) % 4 == 0 && (Integer.parseInt(selectYear) % 100 != 0) || (Integer.parseInt(selectYear) % 400 == 0))) {
                for (int i = 1; i <= 29; i++) {
                    daysList.add(i < 10 ? "0" + i : String.valueOf(i));
                }
                //上月是结尾
                if (selectDay.equals("30") || selectDay.equals("31")) {
                    selectDay = "29";
                }

            } else {
                for (int i = 1; i <= 28; i++) {
                    daysList.add(i < 10 ? "0" + i : String.valueOf(i));
                }
                if (selectDay.equals("30") || selectDay.equals("31") || selectDay.equals("29")) {
                    selectDay = "28";
                }
            }
        }
        wheelDay.setCurrentPosition(daysList.indexOf(selectDay));
    }

    public void setYearInterval(int beganYear, int endYear) {
        this.beganYear = beganYear;
        this.endYear = endYear;
        initData();
    }
}
