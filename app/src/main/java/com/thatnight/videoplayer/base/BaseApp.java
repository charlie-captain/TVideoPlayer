package com.thatnight.videoplayer.base;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * date: 2018/5/29
 * author: thatnight
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
