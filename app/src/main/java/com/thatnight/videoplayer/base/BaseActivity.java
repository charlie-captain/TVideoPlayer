package com.thatnight.videoplayer.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.thatnight.videoplayer.R;


public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseContract.IBaseView {

    protected P mPresenter;
    protected Toolbar mToolbar;
    protected boolean isShowBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        initPresenter();
        initData();
        initToolbar();
        initView();
        initListener();
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.tb);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if (isShowBack) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        }
    }

    protected abstract int getLayoutId();


    protected void initPresenter() {
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(attachModel(), this);
        }
    }

    protected abstract BaseContract.IBaseModel attachModel();

    protected abstract P getPresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
