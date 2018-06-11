package com.thatnight.tvideoplayer.util.notchutil;

import java.lang.reflect.Method;

/**
 * date: 2018/6/11
 * author: thatnight
 */
public class HuaweiNotch extends BaseNotch {


    @Override
    public boolean hasNotch() {
        if (!isInit) {
            try {
                Class hwNotchSizeUtil = Class.forName("com.huawei.android.util.HwNotchSizeUtil");
                Method get = hwNotchSizeUtil.getMethod("hasNotchInScreen");
                hasNotch = (boolean) get.invoke(hwNotchSizeUtil);
                isInit = true;
            } catch (Exception e) {

            }
        }
        return hasNotch;
    }

    @Override
    public int[] getNotchSize() {
        if (hasNotch) {
            if (mNotchSize[0] != NOTCH_UNKNOWN && mNotchSize[1] != NOTCH_UNKNOWN) {
                return mNotchSize;
            } else {
                try {
                    Class hwNotchSizeUtil = Class.forName("com.huawei.android.util.HwNotchSizeUtil");
                    Method get = hwNotchSizeUtil.getMethod("getNotchSize");
                    mNotchSize = (int[]) get.invoke(hwNotchSizeUtil);
                } catch (Exception e) {
                }
            }
        }
        return mNotchSize;
    }


}
