package com.thatnight.tvideoplayer.lifecycle;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thatnight.tvideoplayer.listener.ITVideoPlayerLifeListener;

/**
 * @author gzs11441
 */
public class TVideoPlayerFragment extends Fragment {
    private ITVideoPlayerLifeListener mITVideoPlayerLifeListener;
    public static final String TAG_FRAGMENT = "ListenerFragment";

    public TVideoPlayerFragment() {
    }

    public void setITVideoPlayerLifeListener(ITVideoPlayerLifeListener ITVideoPlayerLifeListener) {
        mITVideoPlayerLifeListener = ITVideoPlayerLifeListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mITVideoPlayerLifeListener != null) {
            mITVideoPlayerLifeListener.onCreate();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mITVideoPlayerLifeListener != null) {
            mITVideoPlayerLifeListener.onResume();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mITVideoPlayerLifeListener != null) {
            mITVideoPlayerLifeListener.onStart();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mITVideoPlayerLifeListener != null) {
            mITVideoPlayerLifeListener.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mITVideoPlayerLifeListener != null) {
            mITVideoPlayerLifeListener.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mITVideoPlayerLifeListener != null) {
            mITVideoPlayerLifeListener.onDestroy();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mITVideoPlayerLifeListener != null) {
            mITVideoPlayerLifeListener.onFragmentHiddenChanged(hidden);
        }
    }
}
