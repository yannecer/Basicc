package com.necer.basic.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.necer.basic.R;
import com.necer.basic.network.RxManager;
import com.necer.basic.view.LoadingDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by 闫彬彬 on 2017/10/11.
 * QQ:619008099
 */

public abstract class BaseActivity<E extends BaseModel> extends AppCompatActivity {

    public E mModel;
    public Context mContext;
    protected String TAG;
    protected LoadingDialog mLoadDialog;
    protected boolean isFirstNet = true;//第一次联网
    ViewGroup content;
    View loadingView;
    ProgressBar pb;
    TextView tvError;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, getLayout());
        setContentView(getLayout());

        TAG = getPackageName() + "." + getClass().getSimpleName();
        StatusbarUI.setStatusBarUIMode(this, Color.parseColor("#ffffff"), true);
        mContext = this;
        mModel = TUtil.getT(this, 0);
        if (this instanceof BaseView && mModel != null) {
            mModel.setVM(this);
        }

        ButterKnife.bind(this);

        if (onNeedEventbus()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }

        mLoadDialog = new LoadingDialog(this);
        content = (FrameLayout) findViewById(R.id.content);
        if (content != null) {
            loadingView = LayoutInflater.from(this).inflate(R.layout.view_loading, null);
            pb = (ProgressBar) loadingView.findViewById(R.id.pb_);
            tvError = (TextView) loadingView.findViewById(R.id.tv_error);
            tvError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFirstNet = true;
                    tvError.setVisibility(View.GONE);
                    pb.setVisibility(View.VISIBLE);
                    getNetData();
                }
            });
            ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            if (content instanceof LinearLayout) {
                content.addView(loadingView, 0,layoutParams);
            } else {
                content.addView(loadingView,layoutParams);
            }
        }

        setData(savedInstanceState);
        getNetData();
    }


    protected abstract int getLayout();

    protected abstract void getNetData();

    protected abstract void setData(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadDialog.isShowing()) {
            mLoadDialog.dismiss();
        }
        ButterKnife.unbind(this);
        RxManager.getInstance().clear(TAG);
        if (onNeedEventbus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected boolean onNeedEventbus() {
        return false;
    }

    public void onStartLoading(int whichRequest) {
        if (content == null) {
            if(!mLoadDialog.isShowing()){ mLoadDialog.show();}
        } else {
            if(!mLoadDialog.isShowing()&&!isFirstNet) mLoadDialog.show();
        }
    }

    public void onEndLoading(int whichRequest) {
        if (content == null) {
            if(mLoadDialog.isShowing()){ mLoadDialog.dismiss();}
        } else {
            if(mLoadDialog.isShowing()&&!isFirstNet) mLoadDialog.dismiss();
        }
    }


    public String getTag() {
        return TAG;
    }

    public void onLoadingSuccess() {
        if (isFirstNet && content!=null) {
            isFirstNet = false;
            loadingView.setVisibility(View.GONE);
        }
    }

    public void onLoadingError(String error) {
        if (isFirstNet && content != null) {
            isFirstNet = false;
            pb.setVisibility(View.GONE);
            tvError.setText(error+"，点击重试");
            tvError.setVisibility(View.VISIBLE);
        }
    }


  /*  @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBus(Object o){

    }*/
}
