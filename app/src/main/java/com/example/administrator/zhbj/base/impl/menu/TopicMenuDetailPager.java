package com.example.administrator.zhbj.base.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zhbj.base.BaseMenuDetailPager;

/**
 * Created by Administrator on 2017/3/4.
 */

public class TopicMenuDetailPager extends BaseMenuDetailPager {
    public TopicMenuDetailPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {
        TextView view = new TextView(mActivity);
        view.setText("菜单详情页-专题");
        view.setTextSize(30);
        view.setTextColor(Color.BLUE);
        view.setGravity(Gravity.CENTER);

        return view;
    }
}
