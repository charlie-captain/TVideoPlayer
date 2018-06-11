package com.thatnight.tvideoplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

import com.thatnight.tvideoplayer.player.TVideoConstant;
import com.thatnight.tvideoplayer.util.TVideoPlayerUtil;

public class TVideoSurfaceView extends SurfaceView {

    private int mVideoHeight;
    private int mVideoWidth;

    private int mode;

    public TVideoSurfaceView(Context context) {
        super(context);
        init();
    }

    public TVideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TVideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mode = TVideoConstant.PORTRAIT;

    }

    public void attachVideoSize(int videoWidth, int videoHeight) {
        if (mVideoWidth != videoWidth && mVideoHeight != videoHeight) {
            mVideoWidth = videoWidth;
            mVideoHeight = videoHeight;
            requestLayout();
        }
    }


    public void setMode(int mode) {
        if (this.mode != mode) {
            this.mode = mode;
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);

        if (mVideoWidth > 0 && mVideoHeight > 0) {
            if (mode == TVideoConstant.PORTRAIT) {
                if (mVideoWidth / mVideoHeight < widthSize / heightSize) {
                    height = heightSize;
                    width = mVideoWidth * height / mVideoHeight;
                } else {
                    width = widthSize;
                    height = mVideoHeight * width / mVideoWidth;
                }
            } else {
                if (mVideoWidth < mVideoHeight) {
                    //如果视频是竖向的
                    height = heightSize;
                    width = mVideoWidth * height / mVideoHeight;
                } else {
                    width = widthSize;
                    height = heightSize;
                }
            }
        }

        Log.d("surfaceview", "onMeasure: " + width + "   " + height + "   " + mVideoWidth + "    " + mVideoHeight);
        Log.d("surfaceview", "onMeasure: " + TVideoPlayerUtil.getScreenWidth() + "   " + TVideoPlayerUtil.getScreenHeight());

        setMeasuredDimension(width, height);
    }


}
