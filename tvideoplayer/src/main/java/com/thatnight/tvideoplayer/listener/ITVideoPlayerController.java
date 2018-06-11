package com.thatnight.tvideoplayer.listener;

import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.thatnight.tvideoplayer.controller.BaseTVideoController;
import com.thatnight.tvideoplayer.player.TVideoPlayerConfig;

import java.util.Map;

/**
 * date: 2018/5/16
 * author: thatnight
 */
public interface ITVideoPlayerController {


    /* 一些常规操作  */

    /**
     * 播放
     */
    void start();

    void start(long position);

    /**
     * 暂停、出错，继续播放
     */
    void restart();

    /**
     * 暂停
     */
    void pause();

    /**
     * 跳转进度
     *
     * @param pos
     */
    void seekTo(long pos);

    /**
     * 释放播放器
     */
    void release();

    /**
     * 重置
     */
    void reset();

    /**
     * 获取时长
     *
     * @return
     */
    long getDuration();

    /**
     * 是否在播放
     *
     * @return
     */
    boolean isPlaying();

    /**
     * 获取音量
     *
     * @return
     */
    int getVolume();

    /**
     * 获取系统最大音量
     *
     * @return
     */
    int getMaxVolume();

    /**
     * 获取当前播放位置
     *
     * @return
     */
    long getCurrentPosition();

    /**
     * 获取缓冲百分比
     *
     * @return
     */
    int getBufferPercentage();

    /**
     * 进入全屏
     *
     * @param isReverse true(顺时针),false(逆时针)
     */
    void enterFullScreen(boolean isReverse);

    /**
     * 退出全屏
     *
     * @param fromUser 是否用户点击切换
     */
    void exitFullScreen(boolean fromUser);

    /**
     * 获取播放状态
     *
     * @return
     */
    int getPlayerState();

    /**
     * 获取播放器模式（全屏，正常）
     *
     * @return
     */
    int getPlayerMode();

    /**
     * 设置音量
     *
     * @param volume
     */
    void setVolume(int volume);


    /**
     * 是否全屏
     *
     * @return
     */
    public abstract boolean isFullScreen();

    /**
     * 是否正常
     *
     * @return
     */
    public abstract boolean isNormal();

    /**
     * 设置视频地址
     *
     * @param uri
     * @param headers
     * @return
     */
    ITVideoPlayerController setDataSource(Uri uri, Map<String, String> headers);

    /**
     * 设置视频地址
     *
     * @param url 网络地址
     * @param headers 访问头部
     * @return
     */
    ITVideoPlayerController setDataSource(String url, Map<String, String> headers);

    ITVideoPlayerController setDataSource(String url);

    ITVideoPlayerController setDataSource(Uri uri);

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    ITVideoPlayerController setTitle(String title);

    /**
     * 设置视频长度
     *
     * @param length
     * @return
     */
    ITVideoPlayerController setLength(long length);

    /**
     * 设置封面链接
     *
     * @param coverUrl
     * @return
     */
    ITVideoPlayerController setCoverUrl(String coverUrl);

    /**
     * 设置封面链接
     *
     * @param coverDrawable
     * @return
     */
    ITVideoPlayerController setCoverUrl(@DrawableRes int coverDrawable);

    /**
     * 设置头部
     *
     * @param headers
     * @return
     */
    ITVideoPlayerController setHeaders(Map<String, String> headers);

    /**
     * 设置视频配置信息
     *
     * @param config
     * @return
     */
    ITVideoPlayerController setConfig(TVideoPlayerConfig config);

    /**
     * 设置控制器
     *
     * @param controller
     * @return
     */
    ITVideoPlayerController setController(BaseTVideoController controller);

    void autoFullScreen(int orientation);
}
