package com.example.administrator.zhbj.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.zhbj.MainActivity;
import com.example.administrator.zhbj.R;
import com.example.administrator.zhbj.base.impl.NewsPager;
import com.example.administrator.zhbj.domain.NewsMenu.NewsMenuData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/3.
 */

public class LeftFragment extends BaseFragment {
    @ViewInject(R.id.left_lv)
    private ListView mLv;
    private  int mCurrentPos;
    private LeftMenuAdapter adapter;

    private ArrayList<NewsMenuData> mNewsMenuData;
    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
       View view =  View.inflate(mActivity, R.layout.fragment_left,null);
        ViewUtils.inject(this,view);
        return view;
    }

    public void setMenuDate(ArrayList<NewsMenuData> data) {
        mCurrentPos=0;
        mNewsMenuData = data;

        adapter = new LeftMenuAdapter();
        mLv.setAdapter(adapter);

        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentPos =position;
                adapter.notifyDataSetChanged();
                toggle();
                setCurrentDetailPager(position);
            }
        });
    }

    private void setCurrentDetailPager(int postion) {
        MainActivity mainUI = (MainActivity) mActivity;
        MainFragment fragment = mainUI.getMainFragment();
        NewsPager pager = fragment.getNewsCnterPager();
        pager.setCurrentDetailPager(postion);


    }

    private void toggle() {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();
    }

    class LeftMenuAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mNewsMenuData.size();
        }

        @Override
        public NewsMenuData getItem(int position) {
            return mNewsMenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.list_item_left_menu, null);
            TextView tvMenu = (TextView) view.findViewById(R.id.tv_menu);
            NewsMenuData menuData = getItem(position);
            tvMenu.setText(menuData.title);
            if (position==mCurrentPos){
                tvMenu.setEnabled(true);
            }else {
                tvMenu.setEnabled(false);
            }
            return view;
        }
    }
}
