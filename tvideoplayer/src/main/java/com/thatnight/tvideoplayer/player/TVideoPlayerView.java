package com.thatnight.tvideoplayer.player;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thatnight.tvideoplayer.controller.BaseTVideoController;
import com.thatnight.tvideoplayer.controller.TVideoController;
import com.thatnight.tvideoplayer.listener.ITVideoPlayerController;
import com.thatnight.tvideoplayer.util.TVideoPlayerScreenRotateUtil;
import com.thatnight.tvideoplayer.util.TVideoPlayerUtil;
import com.thatnight.tvideoplayer.view.TVideoSurfaceView;
import com.thatnight.tvideoplayer.view.TVideoTextureView;

import static com.thatnight.tvideoplayer.player.TVideoConstant.LANDSCAPE;
import static com.thatnight.tvideoplayer.player.TVideoConstant.LANDSCAPE_REVERSE;
import static com.thatnight.tvideoplayer.player.TVideoConstant.MODE_FULLSCREEN;
import static com.thatnight.tvideoplayer.player.TVideoConstant.MODE_FULLSCREEN_REVERSE;
import static com.thatnight.tvideoplayer.player.TVideoConstant.MODE_NORMAL;
import static com.thatnight.tvideoplayer.player.TVideoConstant.PORTRAIT;
import static com.thatnight.tvideoplayer.player.TVideoConstant.STATE_COMPLETED;
import static com.thatnight.tvideoplayer.player.TVideoConstant.STATE_PLAYING;

/**
 * date: 2018/5/16
 * author: thatnight
 * 最终实现播放器布局类
 */
public class TVideoPlayerView extends BaseTVideoPlayer {
    /**
     * SurfaceView相关
     */
    private TVideoSurfaceView mVideoSurfaceView;

    private TVideoTextureView mTextureView;
    private SurfaceTexture mSurfaceTexture;
    private Surface mSurface;


    public TVideoPlayerView(@NonNull Context context) {
        super(context);
        initView(context);

    }

    public TVideoPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public TVideoPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        //添加内容布局
        mContext = context;
        mContent = new FrameLayout(mContext);
        mContent.setBackgroundColor(Color.BLACK);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mContent, params);

        //初始化配置
        mPlayerConfig = new TVideoPlayerConfig.Builder().build();

        //初始化控制器
        mController = new TVideoController(mContext);

        setController(mController);
        // TODO: 2018/5/31 测试
        //        createLeftView();
    }

    private void createLeftView() {
        FrameLayout.LayoutParams otherParams = new FrameLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()));
        otherParams.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        TextView textView = new TextView(getContext());
        textView.setBackgroundColor(Color.WHITE);
        mContent.addView(textView, otherParams);
    }

    /**
     * 设置控制器
     *
     * @param controller
     */
    @Override
    public ITVideoPlayerController setController(BaseTVideoController controller) {
        mContent.removeView(mController);
        mController = controller;
        mController.reset();
        mController.setIVideoPlayer(this);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContent.addView(mController, params);
        return this;
    }


    @Override
    protected void initPlayer() {
        super.initPlayer();
        addDisplay();
    }

    /**
     * 根据配置文件,isUsingTextureView判断是否使用TextureView实现
     */
    private void addDisplay() {
        if (mPlayerConfig.isUsingTextureView) {
            initTextureView();
        } else {
            initSurfaceView();
        }
    }

    @Override
    public void start() {
        super.start();

    }

    @Override
    protected void playNext() {
        if (mCurrentState == STATE_COMPLETED) {
            return;
        }
        //初始化SurfaceView/TextureView
        if (mVideoSurfaceView == null || mTextureView == null) {
            addDisplay();
        }
        mMediaPlayer.start();
        setPlayerState(STATE_PLAYING);
    }

    /**
     * 初始化TextureView
     */
    private void initTextureView() {
        if (mTextureView == null) {
            mTextureView = new TVideoTextureView(mContext);
            mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    if (mSurfaceTexture == null) {
                        mSurfaceTexture = surface;
                        mMediaPlayer.setSurface(new Surface(surface));
                    } else {
                        mTextureView.setSurfaceTexture(mSurfaceTexture);
                    }
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    return mTextureView == null;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                }
            });
            mContent.removeView(mTextureView);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            mContent.addView(mTextureView, 0, params);
        }
    }


    /**
     * 默认使用SurfaceView
     */
    private void initSurfaceView() {
        if (mVideoSurfaceView == null) {
            mVideoSurfaceView = new TVideoSurfaceView(mContext);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            mContent.addView(mVideoSurfaceView, 0, params);
            mVideoSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

                /**
                 * SurfaceView渲染
                 *
                 * @param holder
                 */
                @Override
                public void surfaceCreated(SurfaceHolder holder) {

                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    Log.d("video", "surfaceChanged: ");
                    if (mMediaPlayer != null) {
                        mMediaPlayer.setDisPlay(holder);
                    }
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    Log.d("video", "surfaceDestroyed: ");
                }
            });
        }
    }

    @Override
    protected void setPlayerState(int state) {
        mCurrentState = state;
        mController.onPlayStateChanged(mCurrentState);
    }

    @Override
    protected void setPlayerMode(int mode) {
        mCurrentMode = mode;
        mController.onPlayModeChanged(mode);
    }

    private int mCurrentOrientation = PORTRAIT;

    @Override
    public void autoFullScreen(final int orientation) {
        //        Log.d("handler", "autoFullScreen: " + orientation + "    " + mCurrentOrientation);
        if (mCurrentOrientation == orientation) {
            mHandler.removeCallbacksAndMessages(null);
            return;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("handler", "run: " + orientation);
                if (orientation == LANDSCAPE) {
                    if (mCurrentMode == MODE_FULLSCREEN) {
                        return;
                    }
                    Log.d("handler", "run: a");
                    mCurrentOrientation = orientation;
                    enterFullScreen(false);
                } else if (orientation == LANDSCAPE_REVERSE) {
                    if (mCurrentMode == MODE_FULLSCREEN_REVERSE) {
                        return;
                    }
                    Log.d("handler", "run: b");
                    mCurrentOrientation = orientation;
                    enterFullScreen(true);
                } else if (orientation == PORTRAIT) {
                    if (mCurrentMode == MODE_NORMAL) {
                        return;
                    }
                    Log.d("handler", "run: c");
                    mCurrentOrientation = orientation;
                    exitFullScreen(false);
                }
            }
        }, 800);
    }

    Handler mHandler = new Handler();

    @Override
    public void enterFullScreen(boolean isReverse) {

        TVideoPlayerUtil.hideToolbar(mContext);

        if (isReverse) {
            TVideoPlayerUtil.scanForActivity(mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            setPlayerMode(MODE_FULLSCREEN_REVERSE);
        } else {
            TVideoPlayerUtil.scanForActivity(mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setPlayerMode(MODE_FULLSCREEN);
        }

        this.removeView(mContent);
        ViewGroup viewGroup = TVideoPlayerUtil.scanForActivity(mContext).findViewById(android.R.id.content);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(mContent, params);

    }


    @Override
    public void exitFullScreen(boolean fromUser) {

        if (!isFullScreen()) {
            return;
        }
        if (fromUser && mPlayerConfig.isAutoRotate) {
            TVideoPlayerUtil.stopScreenRotate(mContext);
        }

        TVideoPlayerUtil.scanForActivity(mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setPlayerMode(MODE_NORMAL);

        TVideoPlayerUtil.showToolbar(mContext);

        ViewGroup viewGroup = TVideoPlayerUtil.scanForActivity(mContext).findViewById(android.R.id.content);
        viewGroup.removeView(mContent);
        this.addView(mContent, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (fromUser && mPlayerConfig.isAutoRotate) {
            TVideoPlayerScreenRotateUtil.getInstance(mContext).start();
        }
    }


    /**
     * 当一些设置发生改变
     *
     * @param newConfig
     */
    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("what", "onConfigurationChanged: " + newConfig.orientation);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (mPlayerConfig.isUsingTextureView) {
                if (mTextureView != null) {
                    mTextureView.setMode(TVideoConstant.PORTRAIT);
                }
            } else {
                if (mVideoSurfaceView != null) {
                    mVideoSurfaceView.setMode(TVideoConstant.PORTRAIT);
                }
            }
        } else {
            if (mPlayerConfig.isUsingTextureView) {
                if (mTextureView != null) {
                    mTextureView.setMode(TVideoConstant.LANDSCAPE);
                }
            } else {
                if (mVideoSurfaceView != null) {
                    mVideoSurfaceView.setMode(TVideoConstant.LANDSCAPE);
                }
            }
        }
    }


    @Override
    public void onPrepared() {
        super.onPrepared();
    }

    @Override
    public void onVideoSizedChanged(int width, int height) {
        Log.d("onVideoSizedChanged", width + "  " + height);
        if (mPlayerConfig.isUsingTextureView) {
            mTextureView.attachVideoSize(width, height);
        } else {
            mVideoSurfaceView.attachVideoSize(width, height);
        }
    }

    @Override
    public void release() {
        super.release();
        releaseSurfaceView();
    }

    @Override
    public void releaseSurfaceView() {
        //销毁view
        if (mVideoSurfaceView != null) {
            mContent.removeView(mVideoSurfaceView);
            mVideoSurfaceView = null;
        }
        if (mTextureView != null) {
            mContent.removeView(mTextureView);
            mTextureView = null;
        }
    }
}
