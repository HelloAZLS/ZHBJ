package com.example.administrator.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zhbj.base.BasePager;

/**
 * Created by Administrator on 2017/3/3.
 */

public class SettingPager extends BasePager {
    public SettingPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initDate() {
        TextView view = new TextView(mActivity);
        view.setText("设置");
        view.setTextSize(20);
        view.setTextColor(Color.BLUE);
        view.setGravity(Gravity.CENTER);
        flContent.addView(view);
        tvTitle.setText("设置");
        btnMenu.setVisibility(View.INVISIBLE);
    }
}
