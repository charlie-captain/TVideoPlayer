package com.thatnight.tvideoplayer.util;

import android.content.Context;
import android.os.Build;
import android.view.View;

import com.thatnight.tvideoplayer.controller.BaseTVideoController;
import com.thatnight.tvideoplayer.util.notchutil.BaseNotch;
import com.thatnight.tvideoplayer.util.notchutil.HuaweiNotch;
import com.thatnight.tvideoplayer.util.notchutil.NoNotch;
import com.thatnight.tvideoplayer.util.notchutil.OppoNotch;
import com.thatnight.tvideoplayer.util.notchutil.VivoNotch;

/**
 * date: 2018/6/11
 * author: thatnight
 */
public class NotchUtil {

    private static BaseNotch sNotch = null;

    /**
     * 初始化
     */
    public static void init() {
        if (sNotch == null) {
            sNotch = getNotchType();
        }
    }

    /**
     * 初始化刘海类型
     *
     * @return
     */
    private static BaseNotch getNotchType() {
        if (isHuawei()) {
            return new HuaweiNotch();
        } else if (isOPPO()) {
            return new OppoNotch();
        } else if (isVIVO()) {
            return new VivoNotch();
        }

        return new NoNotch();
    }

    public static boolean hasNotch() {
        return sNotch.hasNotch();
    }

    public static int getNotchHeight() {
        return sNotch.getNotchSize()[1];
    }

    public static int getNotchWidth() {
        return sNotch.getNotchSize()[0];
    }


    /**
     * 判断是否华为手机
     *
     * @return
     */
    public static boolean isHuawei() {
        try {
            return Build.BRAND.toLowerCase().contains("huawei");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否OPPO手机
     *
     * @return
     */
    public static boolean isOPPO() {
        try {
            return Build.BRAND.toLowerCase().contains("oppo");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否VIVO手机
     *
     * @return
     */
    public static boolean isVIVO() {
        try {
            return Build.BRAND.toLowerCase().contains("vivo");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 进入全屏,刘海区域不显示控件
     *
     * @param controller
     */
    public static void enterFullScreen(View controller) {
        sNotch.enterFullScreen(controller);
    }

    /**
     * 退出全屏
     *
     * @param controller
     */
    public static void exitFullScreen(View controller) {
        sNotch.exitFullScreen(controller);
    }
}
