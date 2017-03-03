package com.example.administrator.zhbj.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zhbj.R;
import com.example.administrator.zhbj.base.BasePager;
import com.example.administrator.zhbj.base.impl.GovPager;
import com.example.administrator.zhbj.base.impl.HomePager;
import com.example.administrator.zhbj.base.impl.NewsPager;
import com.example.administrator.zhbj.base.impl.SettingPager;
import com.example.administrator.zhbj.base.impl.SmartPager;
import com.example.administrator.zhbj.view.NoScrollViewPager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/3.
 */

public class MainFragment extends BaseFragment {
    private NoScrollViewPager  vpMain;
    private ArrayList<BasePager> mPagers;

    @Override
    protected void initData() {
        mPagers = new ArrayList<>();
        mPagers.add(new HomePager(mActivity));
        mPagers.add(new NewsPager(mActivity));
        mPagers.add(new SmartPager(mActivity));
        mPagers.add(new GovPager(mActivity));
        mPagers.add(new SettingPager(mActivity));
        vpMain.setAdapter(new MyMainAdapter());
        ////Todo 禁用首页ViewPager的点击事件
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_main, null);
        vpMain = (NoScrollViewPager ) view.findViewById(R.id.vp_main);
        return view;
    }
    class MyMainAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view== (View) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagers.get(position);
            View view = pager.initView();
            pager.initDate();
            container.addView(view);
            return  view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
