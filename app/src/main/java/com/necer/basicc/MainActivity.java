package com.necer.basicc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.necer.basic.base.BaseActivity;

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
    protected void setViewData(Bundle savedInstanceState, int layoutId) {

        //可以使用DataBinding，也可以setContentView(layoutId) 必须要处理

       // mainBinding = DataBindingUtil.setContentView(this, layoutId);
    }


}
