package com.example.administrator.zhbj.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.administrator.zhbj.MainActivity;
import com.example.administrator.zhbj.R;
import com.example.administrator.zhbj.base.BasePager;
import com.example.administrator.zhbj.base.impl.GovPager;
import com.example.administrator.zhbj.base.impl.HomePager;
import com.example.administrator.zhbj.base.impl.NewsPager;
import com.example.administrator.zhbj.base.impl.SettingPager;
import com.example.administrator.zhbj.base.impl.SmartPager;
import com.example.administrator.zhbj.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/3.
 */

public class MainFragment extends BaseFragment {
    private NoScrollViewPager vpMain;
    private ArrayList<BasePager> mPagers;
    private RadioGroup rgGroud;

    @Override
    protected void initData() {
        mPagers = new ArrayList<>();
        mPagers.add(new HomePager(mActivity));
        mPagers.add(new NewsPager(mActivity));
        mPagers.add(new SmartPager(mActivity));
        mPagers.add(new GovPager(mActivity));
        mPagers.add(new SettingPager(mActivity));
        vpMain.setAdapter(new MyMainAdapter());
        rgGroud.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        vpMain.setCurrentItem(0, false);
                        break;
                    case R.id.rb_news:
                        vpMain.setCurrentItem(1, false);
                        break;
                    case R.id.rb_smart:
                        vpMain.setCurrentItem(2, false);
                        break;
                    case R.id.rb_gov:
                        vpMain.setCurrentItem(3, false);
                        break;
                    case R.id.rb_setting:
                        vpMain.setCurrentItem(4, false);
                        break;
                }
            }
        });
        vpMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPagers.get(position).initDate();
                if (position==0||position==mPagers.size()-1){
                    setSlidingEnable(false);
                }else{
                    setSlidingEnable(true);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPagers.get(0).initDate();
        setSlidingEnable(false );
    }

    private void setSlidingEnable(boolean b) {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
       if (b){
           slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

       }else {
           slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
       }
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_main, null);
        vpMain = (NoScrollViewPager) view.findViewById(R.id.vp_main);
        rgGroud = (RadioGroup) view.findViewById(R.id.rg_group);
        return view;
    }

    class MyMainAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (View) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagers.get(position);
            View view = pager.initView();
            //pager.initDate();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public NewsPager  getNewsCnterPager(){
        NewsPager pager = (NewsPager) mPagers.get(1);
        return pager;
    }
}
