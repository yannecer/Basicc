package com.necer.basic.view.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by necer on 2018/1/23.
 */

public abstract class ListAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mList;

    public ListAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
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
            convertView = LayoutInflater.from(mContext).inflate(getLayout(), null);
        }

        bindData(convertView, mList.get(position), position);

        return convertView;
    }

    public abstract int getLayout();

    public abstract void bindData(View convertView, T t, int position);
}
