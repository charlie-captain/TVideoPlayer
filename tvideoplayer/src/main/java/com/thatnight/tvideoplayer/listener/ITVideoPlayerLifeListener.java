package com.thatnight.tvideoplayer.listener;

public interface ITVideoPlayerLifeListener {

    public void onStart();

    public void onPause();

    public void onResume();

    public void onStop();

    public void onDestroy();

    public void onCreate();

    public void onFragmentHiddenChanged(boolean isHidden);

    public void onBackPressed();

}
