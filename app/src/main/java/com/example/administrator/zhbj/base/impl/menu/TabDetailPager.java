package com.example.administrator.zhbj.base.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zhbj.base.BaseMenuDetailPager;
import com.example.administrator.zhbj.domain.NewsMenu.NewsTabData;

;

/**
 * Created by Administrator on 2017/3/4.
 */

public class TabDetailPager extends BaseMenuDetailPager {
    private  TextView view;
    private NewsTabData mTabData;
    public TabDetailPager(Activity mActivity, NewsTabData newsTabData) {
        super(mActivity);
        mTabData =newsTabData;
    }

    @Override
    public View initView() {

        view = new TextView(mActivity);
        view.setTextSize(20);
        view.setTextColor(Color.BLUE);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    @Override
    public void initData() {
        view.setText(mTabData.title);
    }
}
