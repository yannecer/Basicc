package com.necer.basic.view.list;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by necer on 2018/1/23.
 */

public abstract class SimpleAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mList;
    protected int layoutId;

    public SimpleAdapter(Context context, int layoutId) {
        this.mContext = context;
        this.mList = new ArrayList<>();
        this.layoutId = layoutId;
    }

    public SimpleAdapter(Context context, List<T> list, int layoutId) {
        this.mContext = context;
        this.mList = list;
        this.layoutId = layoutId;
    }


    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layoutId, null);
        }
        onBindViewHolder(new NViewHolder(convertView), mList.get(position), position);
        return convertView;
    }


    public abstract void onBindViewHolder(NViewHolder holder, T t, int position);


    public void replaceData(List<T> dataList) {
        if (dataList == null) {
            throw new RuntimeException("replaceData不能null");
        }
        mList.clear();
        mList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void addData(List<T> dataList) {
        if (dataList == null) {
            throw new RuntimeException("addData不能null");
        }
        mList.addAll(mList.size(), dataList);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public T getData(int position) {
        return mList.get(position);
    }


    public static class NViewHolder {

        private View convertView;

        public NViewHolder(View convertView) {
            this.convertView = convertView;
        }

        public <T extends View> T getView(int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                convertView.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = convertView.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }

        public void setText(int viewId, String s) {
            TextView textView = getView(viewId);
            textView.setText(s);
        }

        public void setImage(int viewId, int imageDrawableId) {
            ImageView imageView = getView(viewId);
            imageView.setImageResource(imageDrawableId);
        }

    }


}
