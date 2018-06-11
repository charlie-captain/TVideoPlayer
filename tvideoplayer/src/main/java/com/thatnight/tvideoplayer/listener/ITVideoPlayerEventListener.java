package com.thatnight.tvideoplayer.listener;

/**
 * date: 2018/5/16
 * author: thatnight
 */
public interface ITVideoPlayerEventListener {

    void onError();

    void onCompletion();

    void onInfo(int what, int extra);

    void onBufferingUpdate(int percent);

    void onPrepared();

    void onVideoSizedChanged(int width, int height);


}
