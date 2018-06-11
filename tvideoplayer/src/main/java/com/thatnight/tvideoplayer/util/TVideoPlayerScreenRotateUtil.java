package com.thatnight.tvideoplayer.util;

import android.content.Context;
import android.view.OrientationEventListener;

import com.thatnight.tvideoplayer.player.BaseTVideoPlayer;

import static com.thatnight.tvideoplayer.player.TVideoConstant.LANDSCAPE;
import static com.thatnight.tvideoplayer.player.TVideoConstant.LANDSCAPE_REVERSE;
import static com.thatnight.tvideoplayer.player.TVideoConstant.PORTRAIT;


public class TVideoPlayerScreenRotateUtil {

    private static TVideoPlayerScreenRotateUtil sScreenRotateUtil;

    private BaseTVideoPlayer mIVideoPlayer;

    private OrientationEventListener mOrientationEventListener;

    private boolean isStarted = false;

    private static Context mContext;

    public TVideoPlayerScreenRotateUtil(Context context) {
        if (context == null) {
            return;
        }
        mContext = context.getApplicationContext();
        mOrientationEventListener = new OrientationEventListener(context.getApplicationContext()) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (mIVideoPlayer == null) {
                    return;
                }
                //                Log.d("screen", "onOrientationChanged: " + orientation);
                if (orientation >= 310 || orientation <= 50) {
                    //屏幕正向，竖向
                    mIVideoPlayer.autoFullScreen(PORTRAIT);
                } else if (orientation >= 230 && orientation <= 300) {
                    //屏幕左边在上边，逆时针旋转
                    mIVideoPlayer.autoFullScreen(LANDSCAPE);
                } else if (orientation >= 55 && orientation <= 120) {
                    //屏幕右边在上边，顺时针旋转
                    mIVideoPlayer.autoFullScreen(LANDSCAPE_REVERSE);
                }
            }
        };
    }

    public static TVideoPlayerScreenRotateUtil getInstance(Context context) {
        if (sScreenRotateUtil == null) {
            synchronized (TVideoPlayerScreenRotateUtil.class) {
                if (sScreenRotateUtil == null) {
                    sScreenRotateUtil = new TVideoPlayerScreenRotateUtil(context);
                }
            }
        }
        return sScreenRotateUtil;
    }

    public static TVideoPlayerScreenRotateUtil getInstance() {
        if (sScreenRotateUtil != null) {
            return sScreenRotateUtil;
        }
        return new TVideoPlayerScreenRotateUtil(mContext);
    }


    public TVideoPlayerScreenRotateUtil setTVideoPlayer(BaseTVideoPlayer videoPlayer) {
        mIVideoPlayer = videoPlayer;
        return this;
    }

    public void stop() {
        mOrientationEventListener.disable();
        isStarted = false;
    }

    public void start() {
        if (!isStarted) {
            mOrientationEventListener.enable();
            isStarted = true;
        }
    }


    /**
     * 释放
     */
    public void release() {
        mOrientationEventListener.disable();
        mIVideoPlayer = null;
        sScreenRotateUtil = null;
    }
}
