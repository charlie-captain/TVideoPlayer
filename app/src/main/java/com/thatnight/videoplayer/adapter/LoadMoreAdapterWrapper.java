package com.thatnight.videoplayer.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thatnight.videoplayer.R;

public class LoadMoreAdapterWrapper extends RecyclerView.Adapter {

    public static final int ADAPTER_TYPE_GRID = 0x02;
    public static final int ADAPTER_TYPE_LINEAR = 0x01;
    public static final int ITEM_TYPE_LOAD = 2;
    public static final int ITEM_TYPE = 1;

    public static final int LOADING = 111;
    public static final int LOADING_FINISH = 222;
    public static final int LOADING_END = 333;
    private int mState = LOADING_FINISH;

    private RecyclerView.Adapter mAdapter;
    private WrapperHolder mHolder;


    public LoadMoreAdapterWrapper(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD) {
            if (mHolder == null) {
                mHolder = new WrapperHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, null));
            }
            return mHolder;
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WrapperHolder) {
            WrapperHolder wrapperHolder = (WrapperHolder) holder;
            if (position == 0) {
                wrapperHolder.mTextView.setText("列表是空的，下拉刷新");
                wrapperHolder.mProgressBar.setVisibility(View.GONE);
                wrapperHolder.setLoadVisibility(true);
                return;
            }
            switch (mState) {
                case LOADING:
                case LOADING_FINISH:
                    wrapperHolder.setProgressBarVisibility(true);
                    wrapperHolder.setText("正在加载...");
                    wrapperHolder.setLoadVisibility(true);
                    break;
                case LOADING_END:
                    wrapperHolder.setProgressBarVisibility(false);
                    wrapperHolder.setText("我是有底线的");
                    break;
                default:
                    break;
            }
        } else {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == ITEM_TYPE_LOAD ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return ITEM_TYPE_LOAD;
        }
        return mAdapter.getItemViewType(position);
    }

    public void setState(int state) {
        if (mState != state) {
            mState = state;
            notifyDataSetChanged();
        }
    }

    class WrapperHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ProgressBar mProgressBar;

        public WrapperHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_item_load_more);
            mProgressBar = itemView.findViewById(R.id.pb_item_load_more);
        }

        void setText(CharSequence text) {
            mTextView.setText(text);
        }

        void setLoadVisibility(boolean isShow) {
            itemView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }

        void setProgressBarVisibility(boolean isShow) {
            mProgressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    public boolean isLoading() {
        return mState == LOADING;
    }

    public boolean isNormal() {
        return mState == LOADING_FINISH;
    }


}
