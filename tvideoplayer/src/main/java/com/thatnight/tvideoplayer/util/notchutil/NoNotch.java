package com.thatnight.tvideoplayer.util.notchutil;

/**
 * date: 2018/6/11
 * author: thatnight
 */
public class NoNotch extends BaseNotch {
    @Override
    public boolean hasNotch() {
        return false;
    }

    @Override
    public int[] getNotchSize() {
        return NOT_NOTCH;
    }

}
