package com.thatnight.tvideoplayer.lifecycle;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.thatnight.tvideoplayer.listener.ITVideoPlayerLifeListener;
import com.thatnight.tvideoplayer.player.BaseTVideoPlayer;
import com.thatnight.tvideoplayer.util.TVideoPlayerUtil;

public class TVideoPlayerManager {
    private BaseTVideoPlayer mTVideoPlayer;
    private Context mContext;

    private static class InnerManager {
        private static final TVideoPlayerManager PLAYER_MANAGER = new TVideoPlayerManager();
    }

    public static final TVideoPlayerManager getInstance() {
        return InnerManager.PLAYER_MANAGER;
    }


    public void setTVideoPlayer(BaseTVideoPlayer tVideoPlayer) {
        mTVideoPlayer = tVideoPlayer;
    }

    public Context getContext() {
        if (mContext == null) {
            return null;
        }
        return mContext;
    }

    /**
     * 退出正在全屏
     *
     * @return
     */
    public boolean onBackPressed() {
        if (mTVideoPlayer != null) {
            if (mTVideoPlayer.isFullScreen()) {
                mTVideoPlayer.exitFullScreen(false);
                return true;
            }
            // TODO: 2018/5/14 悬浮窗口
        }
        return false;
    }

    /**
     * 绑定生命周期
     *
     * @param context
     * @return
     */
    public boolean bind(Context context, BaseTVideoPlayer tVideoPlayer) {
        if (context != null) {
            if (context instanceof Activity) {
                TVideoPlayerFragment fragment = new TVideoPlayerFragment();
                TVideoLifecycleListener tVideoLifecycleListener = new TVideoLifecycleListener(tVideoPlayer);
                fragment.setITVideoPlayerLifeListener(tVideoLifecycleListener);
                FragmentManager manager = ((Activity) context).getFragmentManager();
                if (manager != null) {
                    manager.beginTransaction().add(fragment, TVideoPlayerFragment.TAG_FRAGMENT).commitAllowingStateLoss();
                }
                mContext = context;
            }
        }
        return true;
    }

    /**
     * 绑定周期(v4.Fragment)
     *
     * @param context
     * @return
     */
    public boolean bind(Fragment context, BaseTVideoPlayer tVideoPlayer) {
        if (context != null) {
            TVideoPlayerFragmentV4 fragment = new TVideoPlayerFragmentV4();
            TVideoLifecycleListener tVideoLifecycleListener = new TVideoLifecycleListener(tVideoPlayer);
            fragment.setITVideoPlayerLifeListener(tVideoLifecycleListener);
            android.support.v4.app.FragmentManager manager = context.getFragmentManager();
            if (manager != null) {
                manager.beginTransaction().add(fragment, TVideoPlayerFragment.TAG_FRAGMENT).commitAllowingStateLoss();
                return true;
            }
            mContext = context.getActivity().getApplicationContext();
        }
        return false;
    }

    /**
     * 绑定周期(app.Fragment)
     *
     * @param context
     * @return
     */
    public boolean bind(android.app.Fragment context, BaseTVideoPlayer tVideoPlayer) {
        if (context != null) {
            {
                TVideoPlayerFragment fragment = new TVideoPlayerFragment();
                TVideoLifecycleListener tVideoLifecycleListener = new TVideoLifecycleListener(tVideoPlayer);
                fragment.setITVideoPlayerLifeListener(tVideoLifecycleListener);
                FragmentManager manager = context.getFragmentManager();
                if (manager != null) {
                    manager.beginTransaction().add(fragment, TVideoPlayerFragment.TAG_FRAGMENT).commitAllowingStateLoss();
                    return true;
                }
                mContext = context.getActivity().getApplicationContext();
            }
        }
        return false;
    }


    /**
     * 释放播放器
     */
    public void releaseAllVideoPlayer() {
        if (mTVideoPlayer != null) {
            mTVideoPlayer.release();
            mTVideoPlayer = null;
        }
    }

    public void releaseVideoPlayer() {
        if (mTVideoPlayer != null) {
            mTVideoPlayer.release();
            mTVideoPlayer = null;
        }
    }

    /**
     * 暂停
     */
    public void pauseVideoPlayer() {
        if (mTVideoPlayer != null) {
            if (mTVideoPlayer.isPlayState()) {
                mTVideoPlayer.pause();
            }
        }
    }

    /**
     * 继续播放
     */
    public void resumeVideoPlayer() {
        if (mTVideoPlayer != null) {
            mTVideoPlayer.start();
        }
    }

    /**
     * 生命周期监听
     */
    public class TVideoLifecycleListener implements ITVideoPlayerLifeListener {

        private BaseTVideoPlayer mTVideoPlayer;

        public TVideoLifecycleListener(BaseTVideoPlayer TVideoPlayer) {
            mTVideoPlayer = TVideoPlayer;
        }

        /**
         * 释放播放器
         */
        public void releaseAllVideoPlayer() {
            if (mTVideoPlayer != null) {
                mTVideoPlayer.release();
                mTVideoPlayer = null;
            }
        }

        public void releaseVideoPlayer() {
            if (mTVideoPlayer != null) {
                mTVideoPlayer.release();
                mTVideoPlayer = null;
            }
        }

        /**
         * 暂停
         */
        public void pauseVideoPlayer() {
            if (mTVideoPlayer != null) {
                if (mTVideoPlayer.isPlayState()) {
                    mTVideoPlayer.pause();
                }
            }
        }

        /**
         * 继续播放
         */
        public void resumeVideoPlayer() {
            if (mTVideoPlayer != null && mTVideoPlayer.isPlayState()) {
                mTVideoPlayer.start();
            }
        }

        @Override
        public void onStart() {
            Log.d("life", "onStart: ");
            resumeVideoPlayer();
        }

        @Override
        public void onPause() {
            Log.d("life", "onPause: ");
            pauseVideoPlayer();

        }

        @Override
        public void onResume() {
            Log.d("life", "onResume: ");

        }

        @Override
        public void onStop() {
            Log.d("life", "onStop: ");
            if (mTVideoPlayer != null) {
                mTVideoPlayer.releaseSurfaceView();
            }
            TVideoPlayerUtil.stopScreenRotate(mContext);
        }

        @Override
        public void onDestroy() {
            Log.d("life", "onDestroy: ");
            releaseAllVideoPlayer();
            TVideoPlayerUtil.stopNetListener(mContext);
            TVideoPlayerUtil.releaseScreenRotate();
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onFragmentHiddenChanged(boolean isHidden) {

        }

        @Override
        public void onBackPressed() {

        }

    }
}
