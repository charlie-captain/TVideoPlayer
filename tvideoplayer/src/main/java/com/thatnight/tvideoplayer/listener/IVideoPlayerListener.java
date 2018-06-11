package com.thatnight.tvideoplayer.listener;

public interface IVideoPlayerListener {


    /* 9个MediaPlayer状态 */
    public abstract boolean isIdle();

    public abstract boolean isPreparing();

    public abstract boolean isPrepared();

    public abstract boolean isPlaying();

    public abstract boolean isPaused();

    public abstract boolean isError();

    public abstract boolean isCompleted();

    /* 缓冲 */
    public abstract boolean isBufferingPlaying();

    public abstract boolean isBufferingPaused();


}
