package com.thatnight.videoplayer.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thatnight.tvideoplayer.player.TVideoPlayerConfig;
import com.thatnight.tvideoplayer.player.TVideoPlayerView;
import com.thatnight.videoplayer.R;
import com.thatnight.videoplayer.entity.VideoInfo;
import com.thatnight.videoplayer.view.activity.MainActivity;


/**
 * date: 2018/5/25
 * author: thatnight
 */
public class VideoFragment extends Fragment {

    private TVideoPlayerView mTVideoPlayerView;
    private String mUrlCover;
    private String mUrlVideo;
    private VideoInfo mVideoInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_fragment, container, false);
        initData(getArguments());
        initView(view);
        return view;
    }

    private void initData(Bundle arguments) {
        mVideoInfo = arguments.getParcelable(MainActivity.INTENT_STRING);
        if (mVideoInfo != null) {
            mUrlCover = mVideoInfo.getCover();
            mUrlVideo = mVideoInfo.getFlv().replaceAll("flv", "mp4");
        }
    }


    private void initView(View view) {
        mTVideoPlayerView = view.findViewById(R.id.tvideo_video);
        TVideoPlayerConfig config = new TVideoPlayerConfig.Builder().
                autoRotate()    //重力感应
                .build();

        mTVideoPlayerView.bind(this).setTitle(mVideoInfo.getTitle()).setCoverUrl(mVideoInfo.getCover()).setDataSource(mUrlVideo).setConfig(config);
    }


}
