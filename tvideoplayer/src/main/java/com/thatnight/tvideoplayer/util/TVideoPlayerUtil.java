package com.thatnight.tvideoplayer.util;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.thatnight.tvideoplayer.lifecycle.TVideoPlayerManager;
import com.thatnight.tvideoplayer.listener.ITVideoPlayerNetListener;
import com.thatnight.tvideoplayer.player.BaseTVideoPlayer;

public class TVideoPlayerUtil {


    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        int s = 1000;
        int m = s * 60;
        int h = m * 60;

        long hour = time / h;
        long minute = (time - hour * h) / m;
        long second = (time - hour * h - minute * m) / s;

        String sHour = hour < 10 ? "0" + hour : hour + "";
        String sMinute = minute < 10 ? "0" + minute : minute + "";
        String sSecond = second < 10 ? "0" + second : second + "";

        if (hour > 0) {
            return sHour + ":" + sMinute + ":" + sSecond;
        }
        return sMinute + ":" + sSecond;
    }


    /**
     * 从Context获取 AppCompatActivity
     *
     * @param context
     * @return
     */
    public static AppCompatActivity getActivityFromContext(Context context) {
        if (context == null) {
            return null;
        }

        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        } else if (context instanceof ContextThemeWrapper) {
            return getActivityFromContext(((ContextThemeWrapper) context).getBaseContext());
        }
        return null;
    }

    /**
     * 从Context获取 Activity
     *
     * @param context
     * @return
     */
    public static Activity scanForActivity(Context context) {
        if (context == null) {
            return null;
        }

        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    /**
     * 显示Toolbar/ActionBar
     *
     * @param context
     */
    public static void showToolbar(Context context) {
        ActionBar bar = getActivityFromContext(context).getSupportActionBar();
        if (bar != null) {
            bar.show();
        }
        scanForActivity(context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 28) {
            scanForActivity(context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else if (context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism")) {
            //适配OPPO

        }
    }

    public static void hideToolbar(Context context) {
        //        ActionBar bar = getActivityFromContext(context).getSupportActionBar();
        //        if (bar != null) {
        //            bar.hide();
        //        }
                scanForActivity(context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //        if (Build.VERSION.SDK_INT >= 28) {
        //            WindowManager.LayoutParams lp = scanForActivity(context).getWindow().getAttributes();
        //            scanForActivity(context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        //            scanForActivity(context).getWindow().setAttributes(lp);
        //        } else if (context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism")) {
        //            //适配OPPO
        //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //                scanForActivity(context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        //            } else {
        //                WindowManager.LayoutParams params = scanForActivity(context).getWindow().getAttributes();
        //                params.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        //                scanForActivity(context).getWindow().setAttributes(params);
        //            }
        //        }

    }


    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth( ) {
        DisplayMetrics dm = TVideoPlayerManager.getInstance().getContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = TVideoPlayerManager.getInstance().getContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }


    /**
     * 开启重力感应
     *
     * @param context
     */
    public static void startScreenRotate(Context context, BaseTVideoPlayer tVideoPlayer) {
        TVideoPlayerScreenRotateUtil.getInstance(context).setTVideoPlayer(tVideoPlayer).start();
    }

    public static void stopScreenRotate(Context context) {
        TVideoPlayerScreenRotateUtil.getInstance(context).stop();
    }

    public static void releaseScreenRotate() {
        TVideoPlayerScreenRotateUtil.getInstance().release();
    }

    /**
     * 开启网络监听
     *
     * @param context
     * @param netListener
     */
    public static void startNetListen(Context context, ITVideoPlayerNetListener netListener) {
        TVideoPlayerNetUtil.getInstance().startNetListener(context, netListener);
    }

    public static void stopNetListener(Context context) {
        TVideoPlayerNetUtil.getInstance().stopNetListener(context);
    }


}
