package com.necer.basic.bean;

import java.util.List;

/**
 * Created by necer on 2018/9/18.
 */
public class Province extends BaseBean {


    public String name;
    public String value;

    public List<City> children;

    @Override
    public String toString() {
        return name;
    }
}
