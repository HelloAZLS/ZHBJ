package com.example.administrator.zhbj.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2017/3/4.
 */

public abstract class BaseMenuDetailPager {
    public Activity mActivity;
    public View mRootView;

    public BaseMenuDetailPager(Activity mActivity) {
        this.mActivity = mActivity;

        mRootView = initView();
    }

    public abstract View initView();

    public void initData() {

    }
}
