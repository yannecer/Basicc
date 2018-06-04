package com.necer.basicc;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.necer.basicc.databinding.ActivityMainBinding;
import com.necer.basic.base.BaseActivity;

public class MainActivity extends BaseActivity {


    ActivityMainBinding mainBinding;

    @Override
    protected void getNetData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setViewData(Bundle savedInstanceState, int layoutId) {
        mainBinding = DataBindingUtil.setContentView(this, layoutId);
    }

  /*  @Override
    protected void setViewData(Bundle savedInstanceState) {

        DataBindingUtil.setContentView(this, R.layout.activity_main);

    }*/
}
