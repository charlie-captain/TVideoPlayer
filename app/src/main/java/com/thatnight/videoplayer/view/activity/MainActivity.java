package com.thatnight.videoplayer.view.activity;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.thatnight.videoplayer.R;
import com.thatnight.videoplayer.adapter.LoadMoreAdapterWrapper;
import com.thatnight.videoplayer.adapter.VideoListAdapter;
import com.thatnight.videoplayer.base.BaseActivity;
import com.thatnight.videoplayer.base.BaseContract;
import com.thatnight.videoplayer.callback.LoadMoreRecyclerListener;
import com.thatnight.videoplayer.entity.VideoInfo;
import com.thatnight.videoplayer.mvp.contract.VideoListContract;
import com.thatnight.videoplayer.mvp.model.VideoListModel;
import com.thatnight.videoplayer.mvp.presenter.VideoListPresenter;
import com.thatnight.videoplayer.view.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<VideoListPresenter> implements VideoListContract.IView, SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerListener.OnLoadMoreListener, VideoListAdapter.OnClickRecyclerViewListener {
    private RecyclerView mRv;
    private SwipeRefreshLayout mSrlayout;
    private List<VideoInfo> mVideoInfoList;
    private VideoListAdapter mVideoListAdapter;

    private int page = 1;
    private int size = 16;
    LoadMoreAdapterWrapper mLoadMoreAdapter;
    private LoadMoreRecyclerListener mRecyclerListener;

    public static final String INTENT_STRING = "video_info";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected BaseContract.IBaseModel attachModel() {
        return new VideoListModel();
    }

    @Override
    protected VideoListPresenter getPresenter() {
        return new VideoListPresenter();
    }

    @Override
    protected void initData() {
        mVideoInfoList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        mSrlayout = findViewById(R.id.srl_main);
        mRv = findViewById(R.id.rv_main);
        mRv.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        mRv.setItemAnimator(new DefaultItemAnimator());
        mVideoListAdapter = new VideoListAdapter();
        mRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(10, 10, 10, 10);
            }
        });
        mLoadMoreAdapter = new LoadMoreAdapterWrapper(mVideoListAdapter);
        mRv.setAdapter(mLoadMoreAdapter);
        mRecyclerListener = new LoadMoreRecyclerListener(this);
        mRv.addOnScrollListener(mRecyclerListener);
    }

    @Override
    protected void initListener() {
        mSrlayout.setOnRefreshListener(this);
        mVideoListAdapter.setOnClickRecyclerViewListener(this);
        onRefresh();

    }

    @Override
    public void refreshData(List<VideoInfo> videoInfoList) {
        mVideoInfoList.clear();
        if (videoInfoList != null) {
            mVideoInfoList.addAll(videoInfoList);
        }
        mVideoListAdapter.refreshData(videoInfoList);
        mSrlayout.setRefreshing(false);
        mRecyclerListener.startListening();
        mLoadMoreAdapter.setState(LoadMoreAdapterWrapper.LOADING_FINISH);
    }

    @Override
    public void loadMoreData(List<VideoInfo> videoInfoList) {
        if (videoInfoList == null || videoInfoList.isEmpty()) {
            mRecyclerListener.finishListening();
            mLoadMoreAdapter.setState(LoadMoreAdapterWrapper.LOADING_END);
            return;
        }
        mVideoInfoList.addAll(videoInfoList);
        mVideoListAdapter.loadMoreData(videoInfoList);
        mLoadMoreAdapter.setState(LoadMoreAdapterWrapper.LOADING_FINISH);
    }

    @Override
    public void isSuccess(boolean isSuccess, String s) {
        if (isSuccess) {

        } else {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
        mSrlayout.setRefreshing(false);
        mLoadMoreAdapter.setState(LoadMoreAdapterWrapper.LOADING_END);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getVideoList(true, page, size);
    }

    @Override
    public void onLoad() {
        if (mLoadMoreAdapter.isNormal()) {
            mPresenter.getVideoList(false, ++page, size);
            mLoadMoreAdapter.setState(LoadMoreAdapterWrapper.LOADING);
        }
    }

    /**
     * recyclerview点击事件
     *
     * @param pos
     */
    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(this, VideoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("video_info", mVideoInfoList.get(pos));
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onItemLongClick(int pos) {

    }
}
