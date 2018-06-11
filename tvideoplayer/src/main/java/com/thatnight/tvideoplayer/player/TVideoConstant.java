package com.thatnight.tvideoplayer.player;

public class TVideoConstant {

    /**
     * 播放器的状态参数
     */
    public static final int STATE_ERROR = -1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_PREPARING = 1;
    public static final int STATE_PREPARED = 2;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_PAUSE = 4;
    public static final int STATE_BUFFERING_PLAYING = 5;
    public static final int STATE_BUFFERING_PAUSE = 6;
    public static final int STATE_COMPLETED = 7;

    public static final int MODE_NORMAL = 10;
    public static final int MODE_FULLSCREEN = 11;
    public static final int MODE_FULLSCREEN_REVERSE = 12;

    public static final int LANDSCAPE = 121;
    public static final int LANDSCAPE_REVERSE = 122;
    public static final int PORTRAIT = 120;

    /**
     * 网络状态
     */
    public static final int NET_MODE_WIFI = 99;
    public static final int NET_MODE_MOBILE = 100;
    public static final int NET_MODE_ERROR = -99;

}
