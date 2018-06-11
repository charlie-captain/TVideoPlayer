package com.thatnight.tvideoplayer.player;

import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.thatnight.tvideoplayer.listener.ITVideoPlayerEventListener;

import java.util.Map;

/**
 * @date 2018/5/16
 * @author thatnigt
 * 抽象 播放器
 */
public abstract class AbstractPlayer {

    /**
     * Player的事件监听
     */
    protected ITVideoPlayerEventListener mEventListener;

    public abstract AbstractPlayer setDataSource(Uri uri, Map<String, String> headers);

    public abstract AbstractPlayer setDataSource(String url, Map<String, String> headers);

    public abstract AbstractPlayer setDataSource(String url);

    public abstract AbstractPlayer setDataSource(Uri uri);

    /**
     * 初始化播放器
     */
    public abstract void initPlayer();


    /**
     * 开始
     */
    public abstract void start();

    /**
     * 暂停
     */
    public abstract void pause();

    /**
     * 停止
     */
    public abstract void stop();

    /**
     * 异步加载
     */
    public abstract void prepareAsync();

    /**
     * 重置
     */
    public abstract void reset();

    /**
     * 重新播放、继续播放
     */
    public abstract void restart();

    /**
     * 跳转
     * @param position
     */
    public abstract void seekTo(long position);

    /**
     * 使用TextureView
     * @param surface
     */
    public abstract void setSurface(Surface surface);

    /**
     * 使用SurfaceView
     * @param holder
     */
    public abstract void setDisPlay(SurfaceHolder holder);

    /**
     * 获取速度
     * @return
     */
    public abstract long getSpeed();

    /**
     * 释放播放器
     */
    public abstract void release();

    /**
     * 是否播放
     * @return
     */
    public abstract boolean isPlaying();


    /*获取设置*/
    public abstract long getDuration();

    /**
     * 获取当前位置
     * @return
     */
    public abstract long getCurrentPosition();


    public abstract float getSpeed(float speed);

    public abstract long getNetSpeed();

    /**
     * 获取视频宽度
     * @return
     */
    public abstract int getVideoWidth();

    /**
     * 获取视频高度
     * @return
     */
    public abstract int getVideoHeight();


    public abstract void setLooping();


    protected void setEventListener(ITVideoPlayerEventListener listener) {
        mEventListener = listener;
    }

}
