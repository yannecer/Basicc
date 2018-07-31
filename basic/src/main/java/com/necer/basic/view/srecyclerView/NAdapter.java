package com.necer.basic.view.srecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by necer on 2018/2/9.
 */

public abstract class NAdapter<T> extends RecyclerView.Adapter<NAdapter.NViewHolder> {

    protected Context mContext;
    protected List<T> mList;
    protected int mLayoutId;
    private OnItemClickListener mOnItemClickListener;

    private static final int ITEM_VIEW_TYPE_HEADER_INDEX = 10000000;
    private static final int ITEM_VIEW_TYPE_FOOTER_INDEX = 20000000;
    private static final int ITEM_NORMAL = 30000000;


    protected SparseArray mHeaderSparseArray;
    protected SparseArray mFooterSparseArray;


    public NAdapter(Context context, int layoutId, OnItemClickListener onItemClickListener) {
        mContext = context;
        mList = new ArrayList<>();
        this.mLayoutId = layoutId;
        this.mOnItemClickListener = onItemClickListener;

        mHeaderSparseArray = new SparseArray();
        mFooterSparseArray = new SparseArray();
    }

    public NAdapter(Context context, List<T> list, int layoutId, OnItemClickListener onItemClickListener) {
        mContext = context;
        mList = list;
        this.mLayoutId = layoutId;
        this.mOnItemClickListener = onItemClickListener;
        mHeaderSparseArray = new SparseArray();
        mFooterSparseArray = new SparseArray();

    }

    @Override
    public NViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderSparseArray.get(viewType) != null) {
            View headerView = (View) mHeaderSparseArray.get(viewType);
            headerView.setLayoutParams(getLayoutParams());
            return new NViewHolder(headerView);
        } else if (mFooterSparseArray.get(viewType) != null) {
            View footerView = (View) mFooterSparseArray.get(viewType);
            footerView.setLayoutParams(getLayoutParams());
            return new NViewHolder(footerView);
        } else {
            View inflate = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
            return new NViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(final NViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_NORMAL) {
            onBindViewHolder(holder, getData(position - mHeaderSparseArray.size()), position - mHeaderSparseArray.size());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        int i = holder.getAdapterPosition() - mHeaderSparseArray.size();
                        mOnItemClickListener.onItemClick(holder.itemView, mList.get(i), i);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return mHeaderSparseArray.size() + mList.size() + mFooterSparseArray.size();
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View itemView, T t, int position);
    }

    public abstract void onBindViewHolder(NViewHolder holder, T t, int position);

    public boolean hasHeader() {
        return mHeaderSparseArray.size() != 0;
    }

    public boolean hasFooter() {
        return mFooterSparseArray.size() != 0;
    }

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


    public void addHeaderView(View headerView) {
        mHeaderSparseArray.put(mHeaderSparseArray.size() + ITEM_VIEW_TYPE_HEADER_INDEX, headerView);
        notifyDataSetChanged();
    }


    public void addFooterView(View footerView) {
        mFooterSparseArray.put(mFooterSparseArray.size() + ITEM_VIEW_TYPE_FOOTER_INDEX, footerView);
        notifyDataSetChanged();
    }

    public void removeHeaderView(View view) {
        if (mHeaderSparseArray.size() > 0) {
            for (int i = 0; i < mHeaderSparseArray.size(); i++) {
                int key = mHeaderSparseArray.keyAt(i);
                View headerView = (View) mHeaderSparseArray.get(key);
                if (view == headerView) {
                    mHeaderSparseArray.remove(key);
                    notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    public void removeFooterView(View view) {
        if (mFooterSparseArray.size() > 0) {
            for (int i = 0; i < mFooterSparseArray.size(); i++) {
                int key = mFooterSparseArray.keyAt(i);
                View headerView = (View) mFooterSparseArray.get(key);
                if (view == headerView) {
                    mFooterSparseArray.remove(key);
                    notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        int headerSize = mHeaderSparseArray.size();
        int dataSize = mList.size();
        int footerSize = mFooterSparseArray.size();

        if (position < headerSize) {
            return mHeaderSparseArray.keyAt(position);
        } else if (position >= (headerSize + dataSize)) {
            return mFooterSparseArray.keyAt(position - (headerSize + dataSize));
        } else {
            return ITEM_NORMAL;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int spanCount = gridLayoutManager.getSpanCount();
                    if (position < mHeaderSparseArray.size() || position >= (mHeaderSparseArray.size() + mList.size())) {
                        return spanCount;
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    private ViewGroup.LayoutParams getLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static class NViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViewSparseArray;

        public NViewHolder(View itemView) {
            super(itemView);
            mViewSparseArray = new SparseArray<>();

            /**
             * 设置水波纹背景
             */
            if (itemView.getBackground() == null) {
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = itemView.getContext().getTheme();
                int top = itemView.getPaddingTop();
                int bottom = itemView.getPaddingBottom();
                int left = itemView.getPaddingLeft();
                int right = itemView.getPaddingRight();
                if (theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)) {
                    itemView.setBackgroundResource(typedValue.resourceId);
                }
                itemView.setPadding(left, top, right, bottom);
            }
        }

        public <T extends View> T getView(int viewId) {
            View view = mViewSparseArray.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViewSparseArray.put(viewId, view);
            }
            return (T) view;
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
