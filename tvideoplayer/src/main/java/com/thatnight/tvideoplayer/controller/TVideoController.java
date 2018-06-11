package com.thatnight.tvideoplayer.controller;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thatnight.tvideoplayer.R;
import com.thatnight.tvideoplayer.util.TVideoPlayerUtil;

import static com.thatnight.tvideoplayer.player.TVideoConstant.MODE_FULLSCREEN;
import static com.thatnight.tvideoplayer.player.TVideoConstant.MODE_FULLSCREEN_REVERSE;
import static com.thatnight.tvideoplayer.player.TVideoConstant.MODE_NORMAL;
import static com.thatnight.tvideoplayer.player.TVideoConstant.NET_MODE_ERROR;
import static com.thatnight.tvideoplayer.player.TVideoConstant.NET_MODE_MOBILE;
import static com.thatnight.tvideoplayer.player.TVideoConstant.NET_MODE_WIFI;
import static com.thatnight.tvideoplayer.player.TVideoConstant.STATE_BUFFERING_PAUSE;
import static com.thatnight.tvideoplayer.player.TVideoConstant.STATE_BUFFERING_PLAYING;
import static com.thatnight.tvideoplayer.player.TVideoConstant.STATE_COMPLETED;
import static com.thatnight.tvideoplayer.player.TVideoConstant.STATE_ERROR;
import static com.thatnight.tvideoplayer.player.TVideoConstant.STATE_IDLE;
import static com.thatnight.tvideoplayer.player.TVideoConstant.STATE_PAUSE;
import static com.thatnight.tvideoplayer.player.TVideoConstant.STATE_PLAYING;
import static com.thatnight.tvideoplayer.player.TVideoConstant.STATE_PREPARED;
import static com.thatnight.tvideoplayer.player.TVideoConstant.STATE_PREPARING;

public class TVideoController extends BaseTVideoController implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private Context mContext;

    private ImageView mIvCover;
    private ImageButton mIbtnPlay;
    private ImageButton mIbtnPlayCenter;
    private ImageButton mIbtnEnterFullScreen;

    private TextView mTvRestart;
    private TextView mTvTitle;
    private TextView mTvCurPosition;
    private TextView mTvDuration;
    private TextView mTvErrorRetry;
    private TextView mTvNetMobile;
    private TextView mTvNetMobileRetry;

    private TextView mTvPosition;

    private ProgressBar mPbBrightness;
    private ProgressBar mPbPosition;
    private ProgressBar mPbVolume;

    private LinearLayout mLlLoading;
    private LinearLayout mLlCompleted;
    private LinearLayout mLlChangePosition;
    private LinearLayout mLlChangeBrightness;
    private LinearLayout mLlChangeVolume;
    private LinearLayout mLlTop;
    private LinearLayout mLlBottom;
    private LinearLayout mLlError;
    private LinearLayout mLlNet;
    private RelativeLayout mRlContent;

    private AppCompatSeekBar mASeekbar;
    private boolean isControllerVisible;
    private CountDownTimer mControllerTimerTask;
    /**
     * 无网络时保存的位置
     */
    private long currentPosition = 0;

    public TVideoController(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_tvideoplayer_controller, this, true);

        mIvCover = findViewById(R.id.iv_player_cover);
        mIbtnPlay = findViewById(R.id.ibtn_player_play);
        mIbtnPlayCenter = findViewById(R.id.ibtn_player_play_center);
        mIbtnEnterFullScreen = findViewById(R.id.ibtn_player_enter_fullscreen);


        mTvTitle = findViewById(R.id.tv_player_title);
        mTvCurPosition = findViewById(R.id.tv_player_cur_time);
        mTvDuration = findViewById(R.id.tv_player_duration);
        mTvRestart = findViewById(R.id.tv_player_restart);
        mTvPosition = findViewById(R.id.tv_player_position);
        mTvErrorRetry = findViewById(R.id.tv_player_retry);
        mTvNetMobile = findViewById(R.id.tv_player_net_mode);
        mTvNetMobileRetry = findViewById(R.id.tv_player_net_start);

        mASeekbar = findViewById(R.id.asb_player);
        mPbVolume = findViewById(R.id.pb_player_volume);
        mPbBrightness = findViewById(R.id.pb_player_brightness);
        mPbPosition = findViewById(R.id.pb_player_position);

        mLlLoading = findViewById(R.id.ll_player_loading);
        mLlCompleted = findViewById(R.id.ll_player_completed);
        mLlChangePosition = findViewById(R.id.ll_player_position);
        mLlChangeBrightness = findViewById(R.id.ll_player_brightness);
        mLlChangeVolume = findViewById(R.id.ll_player_volume);
        mLlTop = findViewById(R.id.ll_player_controller_top);
        mLlBottom = findViewById(R.id.ll_player_controller_bottom);
        mLlError = findViewById(R.id.ll_player_error);
        mLlNet = findViewById(R.id.ll_player_net_mobile);

        mTvErrorRetry.setOnClickListener(this);
        mTvRestart.setOnClickListener(this);

        mTvNetMobileRetry.setOnClickListener(this);

        mIbtnPlay.setOnClickListener(this);
        mIbtnEnterFullScreen.setOnClickListener(this);
        mIbtnPlayCenter.setOnClickListener(this);
        mASeekbar.setOnSeekBarChangeListener(this);
        setOnClickListener(this);
    }

    @Override
    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    @Override
    public void setCover(String url) {
        RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_foreground);
        Glide.with(mIvCover).load(url).apply(options).into(mIvCover);
    }

    @Override
    public void setCover(int coverDrawable) {
        mIvCover.setImageResource(coverDrawable);
    }

    @Override
    public void setBackground(int resId) {
        mIvCover.setImageResource(resId);
    }

    @Override
    public ImageView imageView() {
        return mIvCover;
    }

    @Override
    public void setLength(long length) {
        mTvDuration.setText(String.valueOf(length));
    }

    @Override
    public void updateProgress() {
        long position = mIVideoPlayer.getCurrentPosition();
        long duration = mIVideoPlayer.getDuration();

        int bufferPercentage = mIVideoPlayer.getBufferPercentage();
        mASeekbar.setSecondaryProgress(bufferPercentage);


        int progress = (int) (100f * position / duration);

        mASeekbar.setProgress(progress);

        mTvCurPosition.setText(TVideoPlayerUtil.formatTime(position));
        mTvDuration.setText(TVideoPlayerUtil.formatTime(duration));
    }

    @Override
    public void onPlayStateChanged(int state) {
        switch (state) {
            case STATE_IDLE:
                break;
            case STATE_PREPARING:
                mLlTop.setVisibility(View.GONE);
                mIvCover.setVisibility(GONE);
                mLlLoading.setVisibility(VISIBLE);
                mLlCompleted.setVisibility(GONE);
                mLlError.setVisibility(View.GONE);
                mIbtnPlayCenter.setVisibility(GONE);
                mLlChangePosition.setVisibility(View.GONE);
                mLlChangeBrightness.setVisibility(View.GONE);
                mLlChangeVolume.setVisibility(View.GONE);
                mLlNet.setVisibility(View.GONE);
                break;
            case STATE_PREPARED:
                startUpdateProgressTask();
                mLlLoading.setVisibility(GONE);
                break;
            case STATE_PLAYING:
                mLlError.setVisibility(View.GONE);
                mLlLoading.setVisibility(GONE);
                setControllerVisible(true);
                mLlNet.setVisibility(View.GONE);
                mIbtnPlay.setImageResource(R.drawable.pause);
                mIbtnPlayCenter.setVisibility(GONE);
                startDismissControllerTimer();  //倒计时隐藏控制器
                break;
            case STATE_PAUSE:
                mLlLoading.setVisibility(GONE);
                mIbtnPlay.setImageResource(R.drawable.play);
                mIbtnPlayCenter.setVisibility(VISIBLE);
                cancelContrllerTimer();     //取消倒计时

                if (mIVideoPlayer.getCurrentNetMode() == NET_MODE_ERROR) {
                    mIbtnPlayCenter.setVisibility(View.GONE);
                    setControllerVisible(false);
                }
                break;
            case STATE_BUFFERING_PLAYING:
                mIbtnPlay.setImageResource(R.drawable.pause);
                mLlLoading.setVisibility(VISIBLE);
                startDismissControllerTimer();
                break;
            case STATE_BUFFERING_PAUSE:
                mIbtnPlay.setImageResource(R.drawable.play);
                mLlLoading.setVisibility(VISIBLE);
                mIbtnPlayCenter.setVisibility(VISIBLE);
                cancelContrllerTimer();
                break;
            case STATE_ERROR:
                cancelUpdateProgressTask();
                setControllerVisible(false);
                mLlTop.setVisibility(View.VISIBLE);
                mLlError.setVisibility(View.VISIBLE);
                mLlCompleted.setVisibility(View.GONE);
                mLlLoading.setVisibility(View.GONE);
                mLlNet.setVisibility(View.GONE);
                break;
            case STATE_COMPLETED:
                cancelUpdateProgressTask();
                mLlCompleted.setVisibility(VISIBLE);
                mIvCover.setVisibility(VISIBLE);
                setControllerVisible(false);
                break;
            default:
                break;
        }

    }

    @Override
    public void onPlayModeChanged(int mode) {
        switch (mode) {
            case MODE_NORMAL:
                mIbtnEnterFullScreen.setImageResource(R.drawable.enter_fullscreen);
                break;
            case MODE_FULLSCREEN:
            case MODE_FULLSCREEN_REVERSE:
                mIbtnEnterFullScreen.setImageResource(R.drawable.exit_fullscreen);
                break;
            default:
                break;
        }
    }


    @Override
    public void onNetModeChanged(int mode) {
        switch (mode) {
            case NET_MODE_MOBILE:
                if (mIVideoPlayer.isUsingMobile()) {
                    mIVideoPlayer.release();
                    mIVideoPlayer.start(currentPosition);
                    return;
                }
                mIvCover.setVisibility(View.GONE);
                mIbtnPlayCenter.setVisibility(View.GONE);
                mIVideoPlayer.pause();
                mTvNetMobile.setText("当前非WIFI网络\n继续播放将产生流量费用");
                mTvNetMobileRetry.setText("继续播放");
                mLlNet.setVisibility(View.VISIBLE);
                break;
            case NET_MODE_ERROR:
                mIvCover.setVisibility(View.GONE);
                mIbtnPlayCenter.setVisibility(View.GONE);
                currentPosition = mIVideoPlayer.getCurrentPosition();
                mIVideoPlayer.pause();
                mTvNetMobile.setText("网络不给力，请检查网络连接状态");
                mTvNetMobileRetry.setText("重试");
                mLlNet.setVisibility(View.VISIBLE);
                break;
            case NET_MODE_WIFI:
                mLlNet.setVisibility(View.GONE);
                if (mIVideoPlayer != null && !mIVideoPlayer.isPlayState()) {
                    mIvCover.setVisibility(View.VISIBLE);
                    mIbtnPlayCenter.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void reset() {
        isControllerVisible = false;
        cancelUpdateProgressTask();
        cancelContrllerTimer();
        mASeekbar.setProgress(0);
        mASeekbar.setSecondaryProgress(0);

        mLlTop.setVisibility(View.VISIBLE);
        mLlBottom.setVisibility(View.GONE);
        mLlError.setVisibility(View.GONE);
        mLlLoading.setVisibility(View.GONE);
        mLlCompleted.setVisibility(View.GONE);

        mIbtnPlayCenter.setVisibility(VISIBLE);
        mIbtnPlay.setImageResource(R.drawable.play);
        mIbtnEnterFullScreen.setImageResource(R.drawable.enter_fullscreen);
    }

    @Override
    protected void showChangePosition(long duration, int newPositionProgress) {
        mLlChangePosition.setVisibility(View.VISIBLE);
        long newPosition = (long) (duration * newPositionProgress / 100f);
        mTvPosition.setText(TVideoPlayerUtil.formatTime(newPosition));
        mTvCurPosition.setText(TVideoPlayerUtil.formatTime(newPosition));
        mASeekbar.setProgress(newPositionProgress);
        mPbPosition.setProgress(newPositionProgress);
    }

    @Override
    protected void showChangeBrightness(int newBrightnessProgress) {
        mLlChangeBrightness.setVisibility(View.VISIBLE);
        mPbBrightness.setProgress(newBrightnessProgress);
    }

    @Override
    protected void showChangeVolume(int newVolumeProgress) {
        mLlChangeVolume.setVisibility(View.VISIBLE);
        mPbVolume.setProgress(newVolumeProgress);
    }

    @Override
    protected void hideChangePosition() {
        mLlChangePosition.setVisibility(View.GONE);

    }

    @Override
    protected void hideChangeVolume() {
        mLlChangeVolume.setVisibility(View.GONE);
    }

    @Override
    protected void hideChangeBrightness() {
        mLlChangeBrightness.setVisibility(View.GONE);
    }

    @Override
    protected void setControllerVisible(boolean visible) {
        mLlTop.setVisibility(visible ? View.VISIBLE : View.GONE);
        mLlBottom.setVisibility(visible ? View.VISIBLE : View.GONE);
        isControllerVisible = visible;
        if (visible) {
            if (!mIVideoPlayer.isPaused() && !mIVideoPlayer.isBufferingPaused()) {
                startDismissControllerTimer();
            }
        } else {
            cancelContrllerTimer();
        }

    }

    @Override
    protected void startDismissControllerTimer() {
        cancelContrllerTimer();
        if (mControllerTimerTask == null) {
            mControllerTimerTask = new CountDownTimer(4000, 4000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    setControllerVisible(false);
                }
            };
        }
        mControllerTimerTask.start();
    }

    @Override
    protected void cancelContrllerTimer() {
        if (mControllerTimerTask != null) {
            mControllerTimerTask.cancel();
        }
    }

    /**
     * 控制器的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (v == this) {
            if (mIVideoPlayer.isPlayState()) {
                setControllerVisible(!isControllerVisible);
            }
        } else if (i == R.id.ibtn_player_play || i == R.id.ibtn_player_play_center) {
            if (mIVideoPlayer.isIdle()) {
                mIVideoPlayer.start();
            } else if (mIVideoPlayer.isPlaying() || mIVideoPlayer.isBufferingPlaying()) {
                mIVideoPlayer.pause();
            } else if (mIVideoPlayer.isPaused() || mIVideoPlayer.isBufferingPaused()) {
                mIVideoPlayer.restart();
            }

        } else if (i == R.id.ibtn_player_enter_fullscreen) {
            if (mIVideoPlayer.isNormal()) {
                mIVideoPlayer.enterFullScreen(false);
            } else {
                mIVideoPlayer.exitFullScreen(true);
            }
        } else if (i == R.id.tv_player_retry) {
            mIVideoPlayer.restart();
        } else if (i == R.id.tv_player_restart) {
            if (mIVideoPlayer.isCompleted()) {
                mIVideoPlayer.restart();
            }
        } else if (i == R.id.tv_player_net_start) {
            mIVideoPlayer.setUsingMobile(true);
            mIVideoPlayer.start();
        }

    }

    /**
     * SeekBar滑动监听
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            showChangePosition(mIVideoPlayer.getDuration(), progress);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        cancelContrllerTimer();
        cancelUpdateProgressTask();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        hideChangePosition();
        startUpdateProgressTask();
        startDismissControllerTimer();
        if (mIVideoPlayer.isBufferingPaused() || mIVideoPlayer.isPaused()) {
            mIVideoPlayer.restart();
        }

        long position = (long) (mIVideoPlayer.getDuration() * seekBar.getProgress() / 100f);

        mIVideoPlayer.seekTo(position);

    }


}
