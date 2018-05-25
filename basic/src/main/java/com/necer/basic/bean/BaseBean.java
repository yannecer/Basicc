package com.necer.basic.bean;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by necer on 2017/12/11.
 */

public class BaseBean implements Serializable {


    @Override
    public String toString() {
        return getVar();
    }


    private String getVar() {
        String value = "";
        // 获取f对象对应类中的所有属性域
        Field[] fields = this.getClass().getDeclaredFields();
        for(int i = 0 , len = fields.length; i < len; i++) {
            // 对于每个属性，获取属性名
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(this);
                value = value + varName + "=" + o+"\n";

                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return value;
    }

    public boolean isEmpty(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        return false;
    }
}
