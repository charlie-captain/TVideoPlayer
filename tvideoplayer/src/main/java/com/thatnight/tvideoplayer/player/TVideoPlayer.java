package com.thatnight.tvideoplayer.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.Map;

/**
 * date: 2018/5/16
 * author: thatnight
 * 默认的播放器类
 */
public class TVideoPlayer extends AbstractPlayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {
    private MediaPlayer mMediaPlayer;
    public Context mContext;

    /**
     * 循环播放
     */
    public boolean isLooping;

    /**
     * 硬解码
     */
    public boolean isEnableMediaCodec;

    public TVideoPlayer(Context context) {
        mContext = context.getApplicationContext();
    }


    @Override
    public void initPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setLooping(isLooping);

        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnVideoSizeChangedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnInfoListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);

    }

    @Override
    public AbstractPlayer setDataSource(Uri uri, Map<String, String> headers) {
        return null;
    }

    @Override
    public AbstractPlayer setDataSource(String url, Map<String, String> headers) {
        return null;
    }

    @Override
    public AbstractPlayer setDataSource(String url) {
        try {
            mMediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public AbstractPlayer setDataSource(Uri uri) {
        return null;
    }


    @Override
    public void start() {
        mMediaPlayer.start();
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
    }

    @Override
    public void stop() {
        mMediaPlayer.stop();
    }

    @Override
    public void prepareAsync() {
        try {
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            mEventListener.onError();
        }
    }

    @Override
    public void reset() {
        mMediaPlayer.reset();

    }

    @Override
    public void setLooping() {
        isLooping = true;
        mMediaPlayer.setLooping(isLooping);
    }

    @Override
    public void restart() {
        mMediaPlayer.start();
    }

    @Override
    public void seekTo(long position) {
        mMediaPlayer.seekTo((int) position);
    }

    @Override
    public void setSurface(Surface surface) {
        mMediaPlayer.setSurface(surface);
    }

    @Override
    public void setDisPlay(SurfaceHolder holder) {
        mMediaPlayer.setDisplay(holder);
    }

    @Override
    public long getSpeed() {
        return 0;
    }


    @Override
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }


    @Override
    public long getDuration() {
        return mMediaPlayer != null ? mMediaPlayer.getDuration() : 0;
    }

    @Override
    public long getCurrentPosition() {
        return mMediaPlayer != null ? mMediaPlayer.getCurrentPosition() : 0;
    }

    @Override
    public float getSpeed(float speed) {
        return 0;
    }

    @Override
    public long getNetSpeed() {
        return 0;
    }

    @Override
    public int getVideoWidth() {
        return mMediaPlayer != null ? mMediaPlayer.getVideoWidth() : 0;
    }

    @Override
    public int getVideoHeight() {
        return mMediaPlayer != null ? mMediaPlayer.getVideoHeight() : 0;
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mEventListener.onPrepared();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mEventListener.onCompletion();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mEventListener.onError();
        return true;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        mEventListener.onInfo(what, extra);
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        mEventListener.onBufferingUpdate(percent);
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        mEventListener.onVideoSizedChanged(width, height);
    }
}
