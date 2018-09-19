package com.necer.basicc;

import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.necer.basic.base.BaseActivity;
import com.necer.basic.base.MyLog;
import com.necer.basic.bean.Area;
import com.necer.basic.bean.City;
import com.necer.basic.bean.Districts;
import com.necer.basic.bean.Province;
import com.necer.basic.utils.Toast;
import com.necer.basic.view.picker.AddressPicker;
import com.necer.basic.view.picker.DatePicker;
import com.necer.basic.view.picker.OptionPicker;

public class MainActivity extends BaseActivity {


  //  ActivityMainBinding mainBinding;

    @Override
    protected void getNetData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setViewData(Bundle savedInstanceState, ViewDataBinding viewDataBinding) {


        String areaJson = ReadAssetsUtils.getAreaJson(this);


        final Area aaaa = new Gson().fromJson(areaJson, Area.class);

       // MyLog.d("bean::" + aaaa.data.size());


        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* new AddressPicker(mContext)
                        .setArea(aaaa)
                        .setDistVisibility(false)
                        .setOnAddressSelectListener(new AddressPicker.OnAddressSelectListener() {
                            @Override
                            public void onAddressSelect(Province province, City city, Districts districts) {
                                MyLog.d("province::" + province.name);
                                MyLog.d("city::" + city.name);
                                MyLog.d("districts::" + districts.name);
                            }
                        })
                        .show();*/


              /*  new DatePicker(mContext)
                        .setSelect("2010-10-10")
                        .setOnDatePickListener(new DatePicker.OnDatePickListener() {
                            @Override
                            public void onSelect(String year, String month, String day) {

                                MyLog.d("year::" + year);
                                MyLog.d("month::" + month);
                                MyLog.d("day::" + day);
                            }
                        }).show();*/


                new OptionPicker(mContext)
                        .setItem(new String[]{"aa", "abb"})
                        .show();


            }
        });

    }


}
