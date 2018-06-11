package com.thatnight.tvideoplayer.player;

/**
 * @author thatnigt
 * @date 2018/5/15
 * 播放器配置类
 */
public class TVideoPlayerConfig {
    /**
     * 循环播放
     */
    public boolean isLooping;
    /**
     * 自动旋转屏幕
     */
    public boolean isAutoRotate;
    /**
     * 使用TextureView实现
     */
    public boolean isUsingTextureView;
    /**
     * 硬解码
     */
    public boolean isEnableMediaCodec;
    /**
     * 保存进度
     */
    public boolean isSavingProgress;
    /**
     * 自定义播放器
     */
    public AbstractPlayer mAbstractPlayer;

    /**
     * 是否全屏
     */
    public boolean isFullScreen;


    public TVideoPlayerConfig(boolean isLooping, boolean isAutoRotate, boolean isUsingTextureView, boolean isEnableMediaCodec, boolean isSavingProgress) {
        this.isLooping = isLooping;
        this.isAutoRotate = isAutoRotate;
        this.isUsingTextureView = isUsingTextureView;
        this.isEnableMediaCodec = isEnableMediaCodec;
        this.isSavingProgress = isSavingProgress;

    }

    public TVideoPlayerConfig(TVideoPlayerConfig config) {
        this.isLooping = config.isLooping;
        this.isAutoRotate = config.isAutoRotate;
        this.isUsingTextureView = config.isUsingTextureView;
        this.isEnableMediaCodec = config.isEnableMediaCodec;
        this.isSavingProgress = config.isSavingProgress;
    }

    public TVideoPlayerConfig() {
    }

    public static class Builder {
        private TVideoPlayerConfig mConfig;

        public Builder() {
            mConfig = new TVideoPlayerConfig();
        }

        public Builder looping() {
            mConfig.isLooping = true;
            return this;
        }

        public Builder autoRotate() {
            mConfig.isAutoRotate = true;
            return this;
        }


        public Builder useTextureView() {
            mConfig.isUsingTextureView = true;
            return this;
        }

        public Builder enableMediaCodec() {
            mConfig.isEnableMediaCodec = true;
            return this;
        }

        public Builder saveProgress() {
            mConfig.isSavingProgress = true;
            return this;
        }

        public Builder setMediaPlayer(AbstractPlayer abstractPlayer) {
            mConfig.mAbstractPlayer = abstractPlayer;
            return this;
        }

        public TVideoPlayerConfig build() {
            return new TVideoPlayerConfig(mConfig);
        }

        public Builder fullScreen() {
            mConfig.isFullScreen = true;
            return this;
        }
    }
}
