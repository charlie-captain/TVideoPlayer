package com.thatnight.videoplayer.base;

public class BasePresenter<M extends BaseContract.IBaseModel, V extends BaseContract.IBaseView> {

    protected M model;
    protected V view;

    public void attachView(M m, V v) {
        model = m;
        view = v;
    }

    public void detachView() {
        model = null;
        view = null;
    }
}
