package com.thatnight.videoplayer.mvp.presenter;


import com.thatnight.videoplayer.base.BasePresenter;
import com.thatnight.videoplayer.entity.VideoInfo;
import com.thatnight.videoplayer.mvp.contract.VideoListContract;
import com.thatnight.videoplayer.mvp.model.VideoListModel;
import com.thatnight.videoplayer.view.activity.MainActivity;

import java.util.List;

public class VideoListPresenter extends BasePresenter<VideoListModel, MainActivity> implements VideoListContract.IPresenter {


    @Override
    public void getVideoList(final boolean isRefresh, int page, int size) {
        model.getVideoList(page, size, new VideoListContract.IModel.OnVideoListCallBack() {
            @Override
            public void getResult(List<VideoInfo> videoInfoList) {
                if (isRefresh) {
                    view.refreshData(videoInfoList);
                } else {
                    view.loadMoreData(videoInfoList);
                }
            }

            @Override
            public void error(String s) {
                view.isSuccess(false, s);
            }
        });
    }
}
