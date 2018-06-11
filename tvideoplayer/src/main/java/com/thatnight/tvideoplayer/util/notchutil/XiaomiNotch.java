package com.thatnight.tvideoplayer.util.notchutil;

/**
 * date: 2018/6/11
 * author: thatnight
 */
public class XiaomiNotch extends BaseNotch  {
    @Override
    public boolean hasNotch() {
        return false;
    }

    @Override
    public int[] getNotchSize() {
        return new int[0];
    }

}
