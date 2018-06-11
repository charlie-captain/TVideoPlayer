package com.thatnight.videoplayer.view.activity;


import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thatnight.tvideoplayer.lifecycle.TVideoPlayerManager;
import com.thatnight.tvideoplayer.player.TVideoPlayerConfig;
import com.thatnight.tvideoplayer.player.TVideoPlayerView;
import com.thatnight.videoplayer.R;
import com.thatnight.videoplayer.base.BaseActivity;
import com.thatnight.videoplayer.base.BaseContract;
import com.thatnight.videoplayer.base.BasePresenter;
import com.thatnight.videoplayer.entity.VideoInfo;
import com.thatnight.videoplayer.view.fragment.VideoFragment;
import com.thatnight.videoplayer.view.fragment.VideoFragmentV4;

public class VideoActivity extends BaseActivity {

    private TVideoPlayerView mTVideoPlayer;
    private String mUrlCover;
    private String mUrlVideo;
    private VideoInfo mVideoInfo;
    private Button mBtnFragment, mBtnFragmentV4;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_video;
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
                .build();
        mTVideoPlayer.bind(this).setTitle(mVideoInfo.getTitle()).setCoverUrl(mVideoInfo.getCover()).setDataSource(mUrlVideo).setConfig(config);

        mBtnFragment = findViewById(R.id.btn_video_fragment);
        mBtnFragmentV4 = findViewById(R.id.btn_video_fragment_v4);
        mBtnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVideoPlayerManager.getInstance().pauseVideoPlayer();
                FragmentManager fm = getFragmentManager();
                VideoFragment videoFragment = new VideoFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(MainActivity.INTENT_STRING, mVideoInfo);
                videoFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.fl_video, videoFragment).addToBackStack("fragment").commitAllowingStateLoss();
            }
        });
        mBtnFragmentV4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVideoPlayerManager.getInstance().pauseVideoPlayer();
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                VideoFragmentV4 videoFragment = new VideoFragmentV4();
                Bundle bundle = new Bundle();
                bundle.putParcelable(MainActivity.INTENT_STRING, mVideoInfo);
                videoFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.fl_video, videoFragment).addToBackStack("fragmentV4").commitAllowingStateLoss();
            }
        });

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
            return;
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
            return;
        }
        if (!TVideoPlayerManager.getInstance().onBackPressed()) {
            super.onBackPressed();
        }
    }

}
