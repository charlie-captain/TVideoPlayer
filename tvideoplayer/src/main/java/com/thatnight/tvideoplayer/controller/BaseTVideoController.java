package com.thatnight.tvideoplayer.controller;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.thatnight.tvideoplayer.player.BaseTVideoPlayer;
import com.thatnight.tvideoplayer.util.TVideoPlayerUtil;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class BaseTVideoController extends FrameLayout implements View.OnTouchListener {

    private Context mContext;

    protected BaseTVideoPlayer mIVideoPlayer;
    protected ScheduledExecutorService mUpdateProgressScheduled;
    protected TimerTask mUpdateProgressTimerTask;

    protected float mDownX;
    protected float mDownY;

    protected boolean isChangePosition;
    protected boolean isChangeBrightness;
    protected boolean isChangeVolume;

    protected long mNewPosition;

    protected float mDownBrightness;
    protected int mDownVolume;
    protected long mDownPosition;

    public static final int FACTOR = 80;
    public static final int FACTOR_HEIGHT = 3;

    public BaseTVideoController(Context context) {
        super(context);
        mContext = context;
        setOnTouchListener(this);
    }

    public void setIVideoPlayer(BaseTVideoPlayer iVideoPlayer) {
        mIVideoPlayer = iVideoPlayer;
    }

    /**
     * 开始更新进度计划任务
     */
    protected void startUpdateProgressTask() {
        cancelUpdateProgressTask();
        if (mUpdateProgressScheduled == null) {
            mUpdateProgressScheduled = new ScheduledThreadPoolExecutor(1);
        }
        if (mUpdateProgressTimerTask == null) {
            mUpdateProgressTimerTask = new TimerTask() {
                @Override
                public void run() {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            updateProgress();
                        }
                    });
                }
            };
        }
        mUpdateProgressScheduled.scheduleAtFixedRate(mUpdateProgressTimerTask, 0, 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 清空更新进度计划任务
     */
    protected void cancelUpdateProgressTask() {
        if (mUpdateProgressScheduled != null) {
            mUpdateProgressScheduled.shutdownNow();
            mUpdateProgressScheduled = null;
        }
        if (mUpdateProgressTimerTask != null) {
            mUpdateProgressTimerTask.cancel();
            mUpdateProgressTimerTask = null;
        }
    }


    public abstract void setTitle(String title);

    public abstract void setCover(String url);

    public abstract void setCover(int coverDrawable);

    public abstract void setBackground(@DrawableRes int resId);

    public abstract ImageView imageView();

    public abstract void setLength(long length);

    public abstract void updateProgress();

    public abstract void onPlayStateChanged(int state);

    public abstract void onPlayModeChanged(int mode);

    public abstract void onNetModeChanged(int mode);

    public abstract void reset();

    /**
     * 显示改变进度，
     *
     * @param duration 视频总时长ms
     * @param newPositionProgress
     */
    protected abstract void showChangePosition(long duration, int newPositionProgress);

    /**
     * 显示改变亮度
     *
     * @param newBrightnessProgress
     */
    protected abstract void showChangeBrightness(int newBrightnessProgress);

    /**
     * 显示改变音量
     *
     * @param newVolumeProgress
     */
    protected abstract void showChangeVolume(int newVolumeProgress);

    protected abstract void hideChangePosition();

    protected abstract void hideChangeVolume();

    protected abstract void hideChangeBrightness();

    /**
     * 手势监听
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!mIVideoPlayer.isPlayState()) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                isChangeBrightness = false;
                isChangePosition = false;
                isChangeVolume = false;
                return false;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mDownX;
                float deltaY = y - mDownY;
                float absDeltaX = Math.abs(deltaX);
                float absDeltaY = Math.abs(deltaY);
                if (!isChangePosition && !isChangeVolume && !isChangeBrightness) {
                    if (absDeltaX >= FACTOR) {
                        cancelUpdateProgressTask();
                        isChangePosition = true;
                        mDownPosition = mIVideoPlayer.getCurrentPosition();
                    } else if (absDeltaY >= FACTOR) {
                        if (mDownX < getWidth() * 0.5f) {
                            isChangeBrightness = true;
                            mDownBrightness = TVideoPlayerUtil.scanForActivity(mContext).getWindow().getAttributes().screenBrightness;
                        } else {
                            isChangeVolume = true;
                            mDownVolume = mIVideoPlayer.getVolume();
                        }
                    }
                }
                if (isChangeBrightness) {
                    //改变亮度
                    deltaY = -deltaY;
                    float deltaBrightness = deltaY * FACTOR_HEIGHT / getHeight();
                    float newBrightness = deltaBrightness + mDownBrightness;
                    newBrightness = Math.max(0, Math.min(newBrightness, 1));
                    float newBrightnessProgress = newBrightness;
                    WindowManager.LayoutParams params = TVideoPlayerUtil.scanForActivity(mContext).getWindow().getAttributes();
                    params.screenBrightness = newBrightnessProgress;
                    TVideoPlayerUtil.scanForActivity(mContext).getWindow().setAttributes(params);
                    showChangeBrightness((int) (newBrightnessProgress * 100f));
                    Log.d("asd", "onTouch: " + newBrightness + "    " + newBrightnessProgress + "    " + x + "   " + y);

                }
                if (isChangeVolume) {
                    //改变音量
                    deltaY = -deltaY;
                    int maxVolume = mIVideoPlayer.getMaxVolume();
                    int deltaVolume = (int) (maxVolume * deltaY * FACTOR_HEIGHT / getHeight());
                    int newVolume = mDownVolume + deltaVolume;
                    newVolume = Math.max(0, Math.min(maxVolume, newVolume));
                    mIVideoPlayer.setVolume(newVolume);
                    int newVolumeProgress = (int) (100f * newVolume / maxVolume);
                    showChangeVolume(newVolumeProgress);
                }
                if (isChangePosition) {
                    //改变进度
                    long duration = mIVideoPlayer.getDuration();
                    long toPosition = (long) (mDownPosition + duration * deltaX / getWidth());
                    mNewPosition = Math.max(0, Math.min(duration, toPosition));
                    int newPositionProgress = (int) (100f * mNewPosition / duration);
                    showChangePosition(mNewPosition, newPositionProgress);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (isChangePosition) {
                    mIVideoPlayer.seekTo(mNewPosition);
                    hideChangePosition();
                    startUpdateProgressTask();
                    return true;
                }
                if (isChangeVolume) {
                    hideChangeVolume();
                    return true;
                }
                if (isChangeBrightness) {
                    hideChangeBrightness();
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    protected abstract void setControllerVisible(boolean visible);

    protected abstract void startDismissControllerTimer();

    protected abstract void cancelContrllerTimer();


}

