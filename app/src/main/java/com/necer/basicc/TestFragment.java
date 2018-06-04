package com.necer.basicc;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.necer.basicc.databinding.FragmentTestBinding;
import com.necer.basic.base.BaseFragment;

public class TestFragment extends BaseFragment {


    FragmentTestBinding testBinding;

    @Override
    protected void getNetData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void setViewData(Bundle savedInstanceState, View layoutView) {

        testBinding = DataBindingUtil.bind(layoutView);
    }
}
