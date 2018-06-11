# TVideoPlayer

### 特性

- 用MediaPlayer + SurfaceView封装，可自定义MediaPlayer

- 重力感应切换全屏

- 手势滑动调节播放进度、亮度、声音

- 采用Glide绑定生命周期方式（无需在Activity/Fragment回调方法）

- 实时监测网络状态并给予提示

- 适配各大厂商刘海屏

### 使用
- #### Gradle
    ```
    implementation 'com.github.thatnight:tvideoplayer:0.2'
    ```

- #### 基本使用

    AndroidManifest.xml
    ```
     <activity android:name=".view.activity.VideoActivity"
                      android:configChanges="orientation|keyboardHidden|screenSize" >
     </activity>
    ```

    布局中
    ```
        <com.thatnight.tvideoplayer.player.TVideoPlayerView android:id="@+id/tvideo_video"
                                                            android:layout_width="match_parent"
                                                            android:layout_height="180dp"
        >
        </com.thatnight.tvideoplayer.player.TVideoPlayerView>
    ```

    Activity中
    ```
    mTVideoPlayer = findViewById(R.id.tvideo_video);
    //创建配置
    TVideoPlayerConfig config = new TVideoPlayerConfig.Builder()
            .autoRotate()    //开启重力感应
            .looping()       //循环播放
            .build();
    //设置VideoPlayer
    mTVideoPlayer
    .bind(this)         //绑定生命周期
    .setTitle(mVideoInfo.getTitle())    //设置标题
    .setCoverUrl(mVideoInfo.getCover()) //设置封面
    .setDataSource(mUrlVideo)           //设置视频链接
    .setConfig(config);                 //设置上面的配置
    ```
- #### Activity后退监听（退出全屏模式）

    ```
    @Override
    public void onBackPressed() {
        if (!TVideoPlayerManager.getInstance().onBackPressed()) {
            super.onBackPressed();
        }
    }
    ```

### 感谢
- [xiaoyanger0825/NiceVieoPlayer](https://github.com/xiaoyanger0825/NiceVieoPlayer)
- [dueeeke/dkplayer](https://github.com/dueeeke/dkplayer)

