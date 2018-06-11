package com.thatnight.tvideoplayer.util.notchutil;

import android.view.View;

import static com.thatnight.tvideoplayer.util.NotchUtil.getNotchHeight;

/**
 * date: 2018/6/11
 * author: thatnight
 */
public abstract class BaseNotch implements NotchInterface {

    final int NOTCH_UNKNOWN = -1;

    final int[] NOT_NOTCH = new int[]{NOTCH_UNKNOWN, NOTCH_UNKNOWN};


    public BaseNotch() {
        isInit = false;
        hasNotch = hasNotch();
        mNotchSize = new int[]{NOTCH_UNKNOWN, NOTCH_UNKNOWN};
    }

    /**
     * 是否刘海屏
     */
    protected boolean hasNotch;
    /**
     * 刘海尺寸
     */
    protected int[] mNotchSize;
    /**
     * 初始化
     */
    protected boolean isInit;

    public int getNotchHeight() {
        return getNotchSize()[1];
    }


    public void enterFullScreen(View controller) {
        controller.setPadding(getNotchHeight(), 0, getNotchHeight(), 0);
    }

    public  void exitFullScreen(View controller) {
        controller.setPadding(0, 0, 0, 0);
    }
}
