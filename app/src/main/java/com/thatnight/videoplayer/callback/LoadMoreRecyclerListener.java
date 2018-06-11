package com.thatnight.videoplayer.callback;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class LoadMoreRecyclerListener extends RecyclerView.OnScrollListener {
    private boolean isSlidingToUp = false;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isFinish = false;

    public LoadMoreRecyclerListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (isFinish) {
            return;
        }
        isSlidingToUp = dy >= 0;
        //停止滑动状态
        if (mLinearLayoutManager == null) {
            mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        }
        if (isSlidingToUp) {
            int lastItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
            //最后一个item 和 向上滑动
            if (lastItemPosition == mLinearLayoutManager.getItemCount() - 1) {
                if (mOnLoadMoreListener != null) {
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mOnLoadMoreListener.onLoad();
                        }
                    });
                }
            }
        }

    }


    public interface OnLoadMoreListener {
        void onLoad();
    }

    public void finishListening() {
        isFinish = true;
    }

    public void startListening() {
        isFinish = false;
    }
}
