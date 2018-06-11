package com.thatnight.tvideoplayer.util.notchutil;

import com.thatnight.tvideoplayer.lifecycle.TVideoPlayerManager;
import com.thatnight.tvideoplayer.util.NotchUtil;

import java.lang.reflect.Method;

/**
 * date: 2018/6/11
 * author: thatnight
 */
public class VivoNotch extends BaseNotch {
    @Override
    public boolean hasNotch() {
        if (!isInit) {
            try {
                Class ftFeature = Class.forName("android.util.FtFeature");
                Method get = ftFeature.getMethod("isFeatureSupport");
                hasNotch = (boolean) get.invoke(ftFeature, 0x00000020);
                isInit = true;
            } catch (Exception e) {
            }
        }
        return hasNotch;
    }

    @Override
    public int[] getNotchSize() {
        if (hasNotch&& TVideoPlayerManager.getInstance().getContext()!=null) {
            int width = NotchUtil.dip2px(TVideoPlayerManager.getInstance().getContext(), 100);
            int height = NotchUtil.dip2px(TVideoPlayerManager.getInstance().getContext(), 27);
            mNotchSize = new int[]{width, height};
        }
        return mNotchSize;
    }

}
