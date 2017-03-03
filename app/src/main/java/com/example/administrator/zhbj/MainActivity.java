package com.example.administrator.zhbj;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.zhbj.fragment.LeftFragment;
import com.example.administrator.zhbj.fragment.MainFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by Administrator on 2017/3/1.
 */

public class MainActivity extends SlidingFragmentActivity {

    private static  final String TAG_MAIN_MENU = "TAG_MAIN_MENU";
    private static  final String TAG_LEFT_MENU = "TAG_LEFT_MENU";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBehindContentView(R.layout.left_menu);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(700);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_main,new MainFragment(),TAG_MAIN_MENU);
        transaction.replace(R.id.fl_left_menu,new LeftFragment(),TAG_LEFT_MENU);
        transaction.commit();

    }
}
