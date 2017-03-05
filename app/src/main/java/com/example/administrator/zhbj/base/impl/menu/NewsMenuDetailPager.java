package com.example.administrator.zhbj.base.impl.menu;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zhbj.MainActivity;
import com.example.administrator.zhbj.R;
import com.example.administrator.zhbj.base.BaseMenuDetailPager;
import com.example.administrator.zhbj.domain.NewsMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/4.
 */

public class NewsMenuDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {
    @ViewInject(R.id.indicator)
    private TabPageIndicator indicator;
    @ViewInject(R.id.vp_new_menu_detail)
    private ViewPager mViewPager;
    private ArrayList<NewsMenu.NewsTabData> mTabDate;
    private ArrayList<TabDetailPager> mTabDetails;

    public NewsMenuDetailPager(Activity mActivity, ArrayList<NewsMenu.NewsTabData> children) {
        super(mActivity);
        mTabDate = children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_news_menu_detail, null);
        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    public void initData() {
        mTabDetails = new ArrayList<>();
        for (int i = 0; i < mTabDate.size(); i++) {
            TabDetailPager pager = new TabDetailPager(mActivity, mTabDate.get(i));
            mTabDetails.add(pager);
        }
        mViewPager.setAdapter(new NewsMenuDetailAdapter());
        indicator.setViewPager(mViewPager);
        indicator.setOnPageChangeListener(this);
    }


    class NewsMenuDetailAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabDate.get(position).title;
        }

        @Override
        public int getCount() {
            return mTabDate.size();

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = mTabDetails.get(position);
            View view = pager.mRootView;
            container.addView(view);
            pager.initData();
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            setSlidingEnable(true);
        } else {
            setSlidingEnable(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setSlidingEnable(boolean b) {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (b) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
}
