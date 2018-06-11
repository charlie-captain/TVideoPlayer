package com.thatnight.videoplayer.mvp.contract;


import com.thatnight.videoplayer.base.BaseContract;
import com.thatnight.videoplayer.entity.VideoInfo;

import java.util.List;

public class VideoListContract {

    public interface IView extends BaseContract.IBaseView {

        void refreshData(List<VideoInfo> videoInfoList);

        void loadMoreData(List<VideoInfo> videoInfoList);

        void isSuccess(boolean isSuccess, String s);

    }

    public interface IModel extends BaseContract.IBaseModel {

        void getVideoList(int page, int size, OnVideoListCallBack callBack);

        interface OnVideoListCallBack {
            void getResult(List<VideoInfo> videoInfoList);

            void error(String s);
        }


    }

    public interface IPresenter extends BaseContract.IBasePresenter {
        void getVideoList(boolean isRefresh, int page, int size);
    }

}
