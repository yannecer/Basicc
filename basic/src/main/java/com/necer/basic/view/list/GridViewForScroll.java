package com.necer.basic.view.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by necer on 2016/3/4.
 */
public class GridViewForScroll extends GridView {
    public GridViewForScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewForScroll(Context context) {
        super(context);
    }

    public GridViewForScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	   if (ev.getAction() == MotionEvent.ACTION_MOVE) {
               return true;  //禁止GridView滑动
          }
    	return super.dispatchTouchEvent(ev);
    }

}