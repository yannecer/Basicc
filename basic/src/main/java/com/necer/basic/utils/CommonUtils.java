package com.necer.basic.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 闫彬彬 on 2017/10/12.
 * QQ:619008099
 */

public class CommonUtils {


    /**
     * dp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 获得屏幕宽/高
     */
    public static int getWindowHeight(Activity acitvity) {
        return acitvity.getWindowManager().getDefaultDisplay().getHeight();
    }

    public static int getWindowWidth(Activity acitvity) {
        return acitvity.getWindowManager().getDefaultDisplay().getWidth();
    }

    /**
     * 时间戳转化成年月日时分
     *
     * @param unixTimestamp
     * @return
     */
    public static String getNYRHFTime(long unixTimestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(unixTimestamp);
        String times = formatter.format(date);
        return times;
    }


    public static String getNYRHFTimeLong(String unixTimestamp) {


        if (unixTimestamp == null) {
            return "";
        }
        long u = Long.parseLong(unixTimestamp);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(u);
        String times = formatter.format(date);
        return times;
    }


    /**
     * 时间戳转化成年月日
     *
     * @param unixTimestamp
     * @return
     */
    public static String getNYRTime(long unixTimestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(unixTimestamp);
        String times = formatter.format(date);
        return times;
    }
/**
     * 时间戳转化成年月日
     *
     * @param unixTimestamp
     * @return
     */
    public static String getNYTime(long unixTimestamp) {
        if (unixTimestamp == 0) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(unixTimestamp);
        String times = formatter.format(date);
        return times;
    }

    public static String getNYRTime2(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        if (s.contains("-")) {
            return s;
        }

        long unixTimestamp = Long.parseLong(s);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(unixTimestamp);
        String times = formatter.format(date);
        return times;
    }

    public static String getNYRTHFime2(String s) {

        if (s == null || s.isEmpty()) {
            return "";
        }

        if (s.contains("-")) {
            return s;
        }

        long unixTimestamp = Long.parseLong(s);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(unixTimestamp);
        String times = formatter.format(date);
        return times;
    }


    public static String isNULL(String string) {

        if (string == null) {
            return "";
        }
        return string;
    }



    /**
     * 字符串日期专秒.
     *
     * @param date
     * @return
     */
    public static long getDateSecond(String date) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date d = format.parse(date);
            return d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    //价格车源详情显示
    public static String formatPrice2(String priceString) {
        if (priceString == null || priceString.isEmpty()) {
            return "**";
        }

        return format2(Double.parseDouble(priceString)/10000);
    }

    /**
     * 格式化两位小数,并三位加，
     *
     * @param d
     * @return
     */
    public static String format2(double d) {
        DecimalFormat decimalFormat = new DecimalFormat("####.00");
        String format = decimalFormat.format(d);
        if (format.equals(".00")) {
            format = "0.00";
        }
        return format;
    }


    //整数显示整数，小数显示两位
    public static String doubleTrans(double d){
        if(Math.round(d)-d==0){
            return String.valueOf((long)d);
        }
        return format2(d);
    }

    /**
     * 打开软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mEditText != null) {
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        } else if (mContext instanceof Activity) {
            imm.hideSoftInputFromWindow(((Activity) mContext).getWindow().getDecorView().getWindowToken(),
                    0);
        }
    }

    public static void closeKeybord(Context mContext) {
        closeKeybord(null, mContext);
    }


    /**
     * 判断当前软键盘是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputShow(Activity activity) {

        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
//       inputmanger.hideSoftInputFromWindow(view.getWindowToken(),0);

            return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }

    public static int getAppVersionCode(Context context) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            if (pm != null) {
                PackageInfo pi;
                try {
                    pi = pm.getPackageInfo(context.getPackageName(), 0);
                    if (pi != null) {
                        return pi.versionCode;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
}
