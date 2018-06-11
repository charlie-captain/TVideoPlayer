package com.thatnight.videoplayer.view.activity;


import android.content.res.Configuration;
import android.view.WindowManager;

import com.thatnight.tvideoplayer.lifecycle.TVideoPlayerManager;
import com.thatnight.tvideoplayer.player.TVideoPlayerConfig;
import com.thatnight.tvideoplayer.player.TVideoPlayerView;
import com.thatnight.videoplayer.R;
import com.thatnight.videoplayer.base.BaseActivity;
import com.thatnight.videoplayer.base.BaseContract;
import com.thatnight.videoplayer.base.BasePresenter;
import com.thatnight.videoplayer.entity.VideoInfo;

public class VideoActivityFullScreen extends BaseActivity {

    private TVideoPlayerView mTVideoPlayer;
    private String mUrlCover;
    private String mUrlVideo;
    private VideoInfo mVideoInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_full;
    }

    @Override
    protected BaseContract.IBaseModel attachModel() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initData() {

        isShowBack = true;
        mVideoInfo = getIntent().getParcelableExtra(MainActivity.INTENT_STRING);
        if (mVideoInfo != null) {
            mUrlCover = mVideoInfo.getCover();
            mUrlVideo = mVideoInfo.getFlv().replaceAll("flv", "mp4");
        }
    }

    @Override
    protected void initView() {
        mTVideoPlayer = findViewById(R.id.tvideo_video);
        TVideoPlayerConfig config = new TVideoPlayerConfig.Builder().
                autoRotate()    //重力感应
                .looping()      //循环播放
                .fullScreen().build();
        mTVideoPlayer.bind(this)
                .setTitle(mVideoInfo.getTitle())
                .setCoverUrl(mVideoInfo.getCover())
                .setDataSource(mUrlVideo)
                .setConfig(config);

    }

    @Override
    protected void initListener() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }

    @Override
    public void onBackPressed() {
        if (!TVideoPlayerManager.getInstance().onBackPressed()) {
            super.onBackPressed();
        }
    }

}
