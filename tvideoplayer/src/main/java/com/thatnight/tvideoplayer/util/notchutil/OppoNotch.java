package com.thatnight.tvideoplayer.util.notchutil;

import com.thatnight.tvideoplayer.lifecycle.TVideoPlayerManager;

/**
 * date: 2018/6/11
 * author: thatnight
 */
public class OppoNotch extends BaseNotch {

    @Override
    public boolean hasNotch() {
        if (!isInit && TVideoPlayerManager.getInstance().getContext() != null) {
            hasNotch = TVideoPlayerManager.getInstance()
                    .getContext()
                    .getPackageManager()
                    .hasSystemFeature("com.oppo.feature.screen.heteromorphism");
        }
        return hasNotch;
    }

    @Override
    public int[] getNotchSize() {
        if (hasNotch) {
            int width = 324;
            int height = 80;
            return new int[]{width, height};
        }
        return mNotchSize;
    }


}
