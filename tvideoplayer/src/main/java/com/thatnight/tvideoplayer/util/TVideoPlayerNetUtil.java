package com.thatnight.tvideoplayer.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.thatnight.tvideoplayer.listener.ITVideoPlayerNetListener;

/**
 * date: 2018/5/17
 * author: thatnight
 */
public class TVideoPlayerNetUtil {

    private static TVideoPlayerNetUtil INSTANCE;
    private boolean isRegister;

    public static TVideoPlayerNetUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (TVideoPlayerNetUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TVideoPlayerNetUtil();
                    NotchUtil.init();
                }
            }
        }
        return INSTANCE;
    }

    private ITVideoPlayerNetListener mITVideoPlayerNetListener;

    private NetChangeReceiver mNetChangeReceiver;

    public void setITVideoPlayerNetListener(ITVideoPlayerNetListener itvideoplayernetlistener) {
        mITVideoPlayerNetListener = itvideoplayernetlistener;
    }

    public void startNetListener(Context context, ITVideoPlayerNetListener netListener) {
        if (context == null) {
            return;
        }
        mITVideoPlayerNetListener = netListener;
        if (mNetChangeReceiver == null) {
            mNetChangeReceiver = new NetChangeReceiver();
        }
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(mNetChangeReceiver, filter);
        isRegister = true;
    }

    public void stopNetListener(Context context) {
        if (context == null || mNetChangeReceiver == null || !isRegister) {
            return;
        }
        context.unregisterReceiver(mNetChangeReceiver);
    }


    /**
     * 广播接收器
     */
    public class NetChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mITVideoPlayerNetListener == null) {
                return;
            }
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                switch (info.getType()) {
                    case ConnectivityManager.TYPE_WIFI:
                        mITVideoPlayerNetListener.onWifi();
                        break;
                    case ConnectivityManager.TYPE_MOBILE:
                        mITVideoPlayerNetListener.onMoibile();
                        break;
                    default:
                        break;
                }
            } else {
                mITVideoPlayerNetListener.onError();
            }
        }
    }


}
