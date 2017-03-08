package com.example.administrator.zhbj.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zhbj.MainActivity;
import com.example.administrator.zhbj.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by Administrator on 2017/3/3.
 */

public class BasePager {
    public Activity mActivity;
    public TextView tvTitle;
    public ImageView btnMenu;
    public FrameLayout flContent;
    public View mRootView;
    public ImageButton btnPhoto;

    public BasePager(Activity mActivity) {
        this.mActivity = mActivity;

        mRootView = initView();
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.base_pager, null);
        tvTitle = (TextView) view.findViewById(R.id.tv_base_title);
        btnMenu = (ImageView) view.findViewById(R.id.btn_menu);
        btnPhoto = (ImageButton) view.findViewById(R.id.btn_photo);
        flContent = (FrameLayout) view.findViewById(R.id.fl_content);
btnMenu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        toggle();
    }
});
        return view;
    }
    private void toggle() {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();
    }
    public void initDate() {

    }

}
