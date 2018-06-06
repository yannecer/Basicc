package com.necer.basicc;

import android.os.Bundle;
import android.view.View;

import com.necer.basic.base.BaseFragment;

public class TestFragment extends BaseFragment {


   // FragmentTestBinding testBinding;

    @Override
    protected void getNetData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void setViewData(Bundle savedInstanceState, View layoutView) {







        //可以使用DataBinding，也可以不做处理

       // testBinding = DataBindingUtil.bind(layoutView);
    }
}
