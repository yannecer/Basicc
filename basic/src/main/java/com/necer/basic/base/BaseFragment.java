package com.necer.basic.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.necer.basic.R;
import com.necer.basic.network.RxManager;
import com.necer.basic.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;


/**
 * Created by 闫彬彬 on 2017/10/12.
 * QQ:619008099
 */

public abstract class BaseFragment<E extends BaseModel> extends Fragment {


    public E mModel;
    protected Context mContext;
    protected String TAG;//当前页面联网标识
    protected boolean isFirstNet = true;//第一次联网
    protected LoadingDialog mLoadDialog;
    ViewGroup content;
    View loadingView;
    ProgressBar pb;
    TextView tvError;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(getLayoutId(), container, false);

        ViewDataBinding viewDataBinding = DataBindingUtil.bind(layoutView);

        TAG = getContext().getPackageName() + "." + getClass().getSimpleName();
        mContext = getContext();
        mModel = TUtil.getT(this, 0);
        if (this instanceof BaseView && mModel != null) mModel.setVM(this);

        setViewData(savedInstanceState, viewDataBinding);
        ButterKnife.bind(this, layoutView);

        if (onNeedEventbus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        mLoadDialog = new LoadingDialog(mContext);
        content = (ViewGroup) layoutView.findViewById(R.id.content);
        if (content != null) {
            loadingView = LayoutInflater.from(mContext).inflate(R.layout.view_loading, null);
            pb = (ProgressBar) loadingView.findViewById(R.id.pb_);
            tvError = (TextView) loadingView.findViewById(R.id.tv_error);
            tvError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击重试
                    isFirstNet = true;
                    tvError.setVisibility(View.GONE);
                    pb.setVisibility(View.VISIBLE);
                    getNetData();
                }
            });
            ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            if (content instanceof LinearLayout) {
                content.addView(loadingView, 0, layoutParams);
            } else {
                content.addView(loadingView, layoutParams);
            }
        }

        getNetData();
        return layoutView;
    }


    protected abstract void getNetData();

    protected abstract int getLayoutId();

    /**
     * 初始化页面的方法
     *
     * @param savedInstanceState
     */
    protected abstract void setViewData(Bundle savedInstanceState, ViewDataBinding viewDataBinding);

    protected boolean onNeedEventbus() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        RxManager.getInstance().clear(TAG);
        if (onNeedEventbus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onStartLoading(int whichRequest) {

        if (content == null) {
            if (!mLoadDialog.isShowing()) {
                mLoadDialog.show();
            }
        } else {
            if (!mLoadDialog.isShowing() && !isFirstNet) mLoadDialog.show();
        }

    }

    public void onEndLoading(int whichRequest) {

        if (content == null) {
            if (mLoadDialog.isShowing()) {
                mLoadDialog.dismiss();
            }
        } else {
            if (mLoadDialog.isShowing() && !isFirstNet) mLoadDialog.dismiss();
        }
    }

    public void onLoadingSuccess() {

        if (isFirstNet && content != null) {
            isFirstNet = false;
            loadingView.setVisibility(View.GONE);
        }
    }

    public void onLoadingError(String error) {
        if (isFirstNet && content != null) {
            isFirstNet = false;
            pb.setVisibility(View.GONE);
            tvError.setText(error + "，点击重试");
            tvError.setVisibility(View.VISIBLE);
        }
    }
}
