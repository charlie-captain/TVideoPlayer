package com.thatnight.tvideoplayer.player;

import android.app.Fragment;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.thatnight.tvideoplayer.controller.BaseTVideoController;
import com.thatnight.tvideoplayer.lifecycle.TVideoPlayerManager;
import com.thatnight.tvideoplayer.listener.ITVideoPlayerController;
import com.thatnight.tvideoplayer.listener.ITVideoPlayerEventListener;
import com.thatnight.tvideoplayer.listener.ITVideoPlayerNetListener;
import com.thatnight.tvideoplayer.listener.IVideoPlayerListener;
import com.thatnight.tvideoplayer.util.TVideoPlayerUtil;

import java.util.Map;

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

/**
 * date: 2018/5/16
 * author: thatnight
 * base播放器
 */
public abstract class BaseTVideoPlayer extends FrameLayout implements ITVideoPlayerController, ITVideoPlayerEventListener, IVideoPlayerListener {

    /**
     * 抽象播放器，方便解耦
     */
    protected AbstractPlayer mMediaPlayer;
    protected BaseTVideoController mController;
    protected boolean isLocked;

    /**
     * 访问头部
     */
    protected Map<String, String> mHeaders;

    /**
     * 音频管理
     */
    protected AudioManager mAudioManager;

    protected Context mContext;
    /**
     * 内容布局
     */
    protected FrameLayout mContent;
    /**
     * 视频链接
     */
    protected String mVideoUrl;

    /**
     * 本地视频
     */
    protected Uri mVideoUri;

    /**
     * 上次暂停位置
     */
    //    protected static long mStopPosition = 0;

    /**
     * Player的配置
     */
    protected TVideoPlayerConfig mPlayerConfig;
    /**
     * 缓冲百分比
     */
    protected int mBufferPercentage;
    /**
     * 布局大小
     */
    protected int mWidth;
    protected int mHeight;


    /**
     * 当前状态(默认IDLE)
     */
    protected int mCurrentState = STATE_IDLE;
    /**
     * 当前模式(默认)
     */
    protected int mCurrentMode = MODE_NORMAL;

    /**
     * 当前位置
     */
    protected int mCurrentPosition;

    /**
     * 当前网络状态
     */
    protected int mCurrentNetMode;

    protected boolean isUsingMobile = false;

    protected String mTitle;
    protected String mCoverUrl;
    protected int mCoverDrawable;
    protected long mLength;

    public BaseTVideoPlayer(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BaseTVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseTVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected abstract void setPlayerState(int state);

    protected abstract void setPlayerMode(int mode);

    protected void setNetMode(int mode) {
        if (mController != null) {
            mCurrentNetMode = mode;
            mController.onNetModeChanged(mCurrentNetMode);
        }
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context) {
        mContext = context;
        mPlayerConfig = new TVideoPlayerConfig.Builder().build();
    }

    /**
     * 初始化播放器，根据Config.mAbstractPlayer判断是否用户自定义
     */
    protected void initPlayer() {
        if (mMediaPlayer == null) {
            if (mPlayerConfig.mAbstractPlayer != null) {
                mMediaPlayer = mPlayerConfig.mAbstractPlayer;
            } else {
                mMediaPlayer = new TVideoPlayer(mContext);
            }
        }
        mMediaPlayer.setEventListener(this);
        mMediaPlayer.initPlayer();
        if (mPlayerConfig.isLooping) {
            mMediaPlayer.setLooping();
        }
    }

    /**
     * 第一次播放
     */
    protected void playFirst() {
        initPlayer();
        startPrepared();
    }

    /**
     * Player数据
     */
    protected void startPrepared() {
        mMediaPlayer.setDataSource(mVideoUrl);
        mMediaPlayer.prepareAsync();
        setPlayerState(STATE_PREPARING);
    }

    /**
     * 开始播放
     */
    @Override
    public void start() {
        if (mCurrentNetMode == NET_MODE_ERROR) {
            return;
        } else if (!isUsingMobile && mCurrentNetMode == NET_MODE_MOBILE) {
            return;
        }
        if (mCurrentState == STATE_IDLE) {
            TVideoPlayerManager.getInstance().setTVideoPlayer(this);
            initAudioManager();
            playFirst();
        } else {
            playNext();
        }
        if (mPlayerConfig.isAutoRotate) {
            TVideoPlayerUtil.startScreenRotate(mContext, this);
        }
        setKeepScreenOn(true);
    }

    public int getCurrentNetMode() {
        return mCurrentNetMode;
    }

    /**
     * 继续播放
     */
    protected abstract void playNext();

    /**
     * 从一个位置开始播放
     *
     * @param position
     */
    @Override
    public void start(long position) {
        mCurrentPosition = (int) position;
        start();
    }

    @Override
    public void pause() {
        if (mMediaPlayer != null && mCurrentNetMode == NET_MODE_ERROR) {
            mMediaPlayer.pause();
            mCurrentState = STATE_PAUSE;
            return;
        } else if (mMediaPlayer != null && !isUsingMobile && mCurrentNetMode == NET_MODE_MOBILE) {
            mMediaPlayer.pause();
            mCurrentState = STATE_PAUSE;
            return;
        }
        switch (mCurrentState) {
            case STATE_PLAYING:
                mMediaPlayer.pause();
                setPlayerState(STATE_PAUSE);
                break;
            case STATE_BUFFERING_PLAYING:
                mMediaPlayer.pause();
                setPlayerState(STATE_BUFFERING_PAUSE);
                break;
            default:
                break;
        }

    }

    @Override
    public void restart() {
        switch (mCurrentState) {
            case STATE_PAUSE:
                mMediaPlayer.start();
                setPlayerState(STATE_PLAYING);
                break;
            case STATE_BUFFERING_PAUSE:
                mMediaPlayer.start();
                setPlayerState(STATE_BUFFERING_PLAYING);
                break;
            case STATE_COMPLETED:
                release();
                start();
                break;
            case STATE_ERROR:
                release();
                start();
                break;
            default:
                break;
        }
    }


    public boolean isPlayState() {
        return (mMediaPlayer != null && mCurrentState != STATE_ERROR && mCurrentState != STATE_IDLE && mCurrentState != STATE_PREPARING && mCurrentState != STATE_COMPLETED && mCurrentNetMode != NET_MODE_ERROR);
    }

    public abstract void releaseSurfaceView();

    @Override
    public void release() {
        if (mPlayerConfig.isAutoRotate) {
            TVideoPlayerUtil.stopScreenRotate(mContext);
        }
        if (mAudioManager != null) {
            mAudioManager.abandonAudioFocus(null);
            mAudioManager = null;
        }

        if (mPlayerConfig.isSavingProgress) {
            // TODO: 2018/5/15 保存上次播放位置

        }

        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            setPlayerState(STATE_IDLE);
            setKeepScreenOn(false);
        }


        if (isFullScreen()) {
            exitFullScreen(false);
        }

        mCurrentMode = MODE_NORMAL;

        if (mController != null) {
            mController.reset();
        }
        Runtime.getRuntime().gc();

    }

    @Override
    public void reset() {
        mMediaPlayer.reset();
        mCurrentState = STATE_IDLE;
    }

    @Override
    public void seekTo(long pos) {
        if (isPlayState()) {
            mMediaPlayer.seekTo(pos);
        }
    }

    @Override
    public long getDuration() {
        return mMediaPlayer != null ? mMediaPlayer.getDuration() : 0;
    }

    @Override
    public int getVolume() {
        if (mAudioManager != null) {
            return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }
        return 0;
    }

    @Override
    public int getMaxVolume() {
        if (mAudioManager != null) {
            return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        }
        return 0;
    }

    @Override
    public long getCurrentPosition() {
        if (mMediaPlayer != null && mCurrentState != STATE_ERROR && mCurrentState != STATE_IDLE && mCurrentState != STATE_PREPARING && mCurrentState != STATE_COMPLETED) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        return mMediaPlayer != null ? mBufferPercentage : 0;
    }

    /**
     * 初始化音频管理器
     */
    private void initAudioManager() {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);

            // TODO: 2018/5/9   8.0 之后的音频焦点获取
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MOVIE).build();
                AudioFocusRequest request = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).setAudioAttributes(audioAttributes).build();
                mAudioManager.requestAudioFocus(request);
            } else {
                mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            }

        }
    }

    @Override
    public void onError() {
        if (isPlayState()) {
            return;
        }
        setPlayerState(STATE_ERROR);

    }

    @Override
    public void onCompletion() {
        setPlayerState(STATE_COMPLETED);
        setKeepScreenOn(false);
        mCurrentPosition = 0;
    }

    @Override
    public void onInfo(int what, int extra) {
        Log.d("info", "onInfo: " + what + "  " + extra);
        switch (what) {
            case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                setPlayerState(STATE_PLAYING);
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mCurrentState == STATE_PAUSE || mCurrentState == STATE_BUFFERING_PAUSE) {
                    mCurrentState = STATE_BUFFERING_PAUSE;
                } else {
                    mCurrentState = STATE_BUFFERING_PLAYING;
                }
                setPlayerState(mCurrentState);
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                if (mCurrentState == STATE_BUFFERING_PLAYING) {
                    mCurrentState = STATE_PLAYING;
                }
                if (mCurrentState == STATE_BUFFERING_PAUSE) {
                    mCurrentState = STATE_PAUSE;
                }
                setPlayerState(mCurrentState);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBufferingUpdate(int percent) {
        mBufferPercentage = percent;
    }

    @Override
    public void onPrepared() {
        setPlayerState(STATE_PREPARED);
        mMediaPlayer.start();

        if (mCurrentPosition > 0) {
            mMediaPlayer.seekTo(mCurrentPosition);
        }
    }


    @Override
    public int getPlayerState() {
        return mCurrentState;
    }

    @Override
    public int getPlayerMode() {
        return mCurrentMode;
    }

    @Override
    public void setVolume(int volume) {
        if (mAudioManager != null) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        }
    }

    @Override
    public ITVideoPlayerController setDataSource(Uri uri, Map<String, String> headers) {
        mVideoUri = uri;
        mHeaders = headers;
        return this;
    }

    @Override
    public ITVideoPlayerController setDataSource(String url, Map<String, String> headers) {
        mVideoUrl = url;
        mHeaders = headers;
        return this;
    }

    @Override
    public ITVideoPlayerController setDataSource(String url) {
        return setDataSource(url, null);
    }

    @Override
    public ITVideoPlayerController setDataSource(Uri uri) {
        return setDataSource(uri);
    }

    @Override
    public ITVideoPlayerController setTitle(String title) {
        mTitle = title;
        if (mController != null) {
            mController.setTitle(title);
        }
        return this;
    }

    @Override
    public ITVideoPlayerController setLength(long length) {
        mLength = length;
        if (mController != null) {
            mController.setLength(length);
        }
        return this;
    }

    @Override
    public ITVideoPlayerController setHeaders(Map<String, String> headers) {
        mHeaders = headers;
        return this;
    }

    @Override
    public ITVideoPlayerController setConfig(TVideoPlayerConfig config) {
        mPlayerConfig = config;
        return this;
    }

    @Override
    public ITVideoPlayerController setCoverUrl(String coverUrl) {
        mCoverUrl = coverUrl;
        if (mController != null) {
            mController.setCover(coverUrl);
        }
        return this;
    }

    @Override
    public ITVideoPlayerController setCoverUrl(int coverDrawable) {
        mCoverDrawable = coverDrawable;
        if (mController != null) {
            mController.setCover(mCoverDrawable);
        }
        return null;
    }

    @Override
    public boolean isNormal() {
        return mCurrentMode == MODE_NORMAL;
    }

    @Override
    public boolean isFullScreen() {
        return mCurrentMode == MODE_FULLSCREEN || mCurrentMode == MODE_FULLSCREEN_REVERSE;
    }

    /**
     * 绑定生命周期
     *
     * @param context
     */
    public BaseTVideoPlayer bind(Context context) {
        TVideoPlayerManager.getInstance().bind(context, this);
        return startNetListener(context);
    }

    /**
     * 绑定 app.fragment
     *
     * @param context
     * @return
     */
    public BaseTVideoPlayer bind(Fragment context) {
        TVideoPlayerManager.getInstance().bind(context, this);
        return startNetListener(context.getActivity());
    }

    /**
     * 绑定 v4.fragment
     *
     * @param context
     * @return
     */
    public BaseTVideoPlayer bind(android.support.v4.app.Fragment context) {
        TVideoPlayerManager.getInstance().bind(context, this);
        return startNetListener(context.getActivity());
    }

    /**
     * 网络监听
     *
     * @param context
     * @return
     */
    public BaseTVideoPlayer startNetListener(Context context) {

        //网络监听
        TVideoPlayerUtil.startNetListen(context, new ITVideoPlayerNetListener() {
            @Override
            public void onWifi() {
                setNetMode(NET_MODE_WIFI);
            }

            @Override
            public void onMoibile() {
                setNetMode(NET_MODE_MOBILE);
            }

            @Override
            public void onError() {
                setNetMode(NET_MODE_ERROR);
            }
        });
        return this;
    }


    @Override
    public boolean isIdle() {
        return mCurrentState == STATE_IDLE;
    }

    @Override
    public boolean isPreparing() {
        return mCurrentState == STATE_PREPARING;
    }

    @Override
    public boolean isPrepared() {
        return mCurrentState == STATE_PREPARED;
    }

    @Override
    public boolean isPlaying() {
        return mCurrentState == STATE_PLAYING;
    }

    @Override
    public boolean isPaused() {
        return mCurrentState == STATE_PAUSE;
    }

    @Override
    public boolean isError() {
        return mCurrentState == STATE_ERROR;
    }

    @Override
    public boolean isCompleted() {
        return mCurrentState == STATE_COMPLETED;
    }

    @Override
    public boolean isBufferingPlaying() {
        return mCurrentState == STATE_BUFFERING_PLAYING;
    }

    @Override
    public boolean isBufferingPaused() {
        return mCurrentState == STATE_BUFFERING_PAUSE;
    }


    @Override
    public void enterFullScreen(boolean isReverse) {

    }

    @Override
    public void exitFullScreen(boolean fromUser) {

    }


    public void setUsingMobile(boolean usingMobile) {
        isUsingMobile = usingMobile;
    }

    public boolean isUsingMobile() {
        return isUsingMobile;
    }
}
