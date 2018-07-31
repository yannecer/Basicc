package com.necer.basic.utils;


import com.necer.basic.base.BaseApplication;
import java.util.ArrayList;
import java.util.List;

/**
 * T实例需要重写 equals 方法
 */
public class HistoryUtil {

    public static <T> void saveHistory(String spName, T t) {
        List<T> historyList = (List<T>) SharePrefUtil.getObj(BaseApplication.application, spName);
        if (historyList == null) {
            historyList = new ArrayList<>();
        }
        if (historyList.contains(t)) {
            historyList.remove(t);
        }
        historyList.add(0, t);
        SharePrefUtil.saveObj(BaseApplication.application, spName, historyList);
    }


    public static <T> List<T> getHistory(String spName) {
        List<T> searchList = (List<T>) SharePrefUtil.getObj(BaseApplication.application, spName);
        if (searchList == null) {
            searchList = new ArrayList<>();
        }
        return searchList;
    }
}
