package com.necer.basic.view.srecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.necer.basic.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

/**
 * Created by necer on 2018/2/9.
 */

public class SRefreshLayout extends SmartRefreshLayout {

    TextView emptyTextView;
    RecyclerView recyclerView;
    boolean isEnableLoadMore;//是否需要加载更多,原始状态


    public SRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        MaterialHeader materialHeader = new MaterialHeader(context);
        materialHeader.setShowBezierWave(false);
        materialHeader.setColorSchemeColors(Color.parseColor("#4f79e4"));
        setRefreshHeader(materialHeader);


        BallPulseFooter ballPulseFooter = new BallPulseFooter(context);
        ballPulseFooter.setAnimatingColor(Color.parseColor("#4f79e4"));
        setRefreshFooter(ballPulseFooter);

        isEnableLoadMore = isEnableLoadMore();

        emptyTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.view_empty_recyclerview, null);

    }


    public void setNoDataMessage(String message) {
        emptyTextView.setText(message);
    }

    public View getEmptyView() {
        return emptyTextView;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        @SuppressLint("RestrictedApi")
        View view = mRefreshContent.getView();

        if (view instanceof ViewGroup && !(view instanceof RecyclerView)) {
            ViewGroup viewGroup = (ViewGroup) view;
            viewGroup.addView(emptyTextView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt1 = viewGroup.getChildAt(i);
                if (childAt1 instanceof RecyclerView) {
                    recyclerView = (RecyclerView) childAt1;
                    recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            RecyclerView.Adapter adapter = recyclerView.getAdapter();
                            if (adapter != null) {
                                int itemCount = adapter.getItemCount();
                                recyclerView.setVisibility(itemCount == 0 ? GONE : VISIBLE);
                                emptyTextView.setVisibility(itemCount == 0 ? VISIBLE : GONE);

                                //没有数据的时候，禁止上拉加载更多
                                SRefreshLayout.this.setEnableLoadMore(itemCount != 0 && isEnableLoadMore);
                            }
                        }
                    });
                    break;
                }
            }
        }
    }
}
