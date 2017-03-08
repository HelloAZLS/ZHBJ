package com.example.administrator.zhbj.base.impl;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.zhbj.MainActivity;
import com.example.administrator.zhbj.Utils.CacheUtils;
import com.example.administrator.zhbj.base.BaseMenuDetailPager;
import com.example.administrator.zhbj.base.BasePager;
import com.example.administrator.zhbj.base.impl.menu.InteractMenuDetailPager;
import com.example.administrator.zhbj.base.impl.menu.NewsMenuDetailPager;
import com.example.administrator.zhbj.base.impl.menu.PhotosMenuDetailPager;
import com.example.administrator.zhbj.base.impl.menu.TopicMenuDetailPager;
import com.example.administrator.zhbj.domain.NewsMenu;
import com.example.administrator.zhbj.fragment.LeftFragment;
import com.example.administrator.zhbj.global.GlobalContants;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/3.
 */

public class NewsPager extends BasePager {
    public NewsPager(Activity mActivity) {
        super(mActivity);
    }

    private ArrayList<BaseMenuDetailPager> mMenuDetailPagers;
    private   NewsMenu mNewsData;;

    @Override
    public void initDate() {
        /*TextView view = new TextView(mActivity);
        view.setText("新闻中心");
        view.setTextSize(20);
        view.setTextColor(Color.BLUE);
        view.setGravity(Gravity.CENTER);
        flContent.addView(view);*/
        tvTitle.setText("新闻中心");
        btnMenu.setVisibility(View.VISIBLE);
        String cache = CacheUtils.getCache(GlobalContants.CATEGORY_URL, mActivity);
        Log.i("缓存是否为空", cache);
        if (!TextUtils.isEmpty(cache)) {
            Log.i("加载缓存", cache);
            processData(cache);
        }
        getDataFromServer();
    }
////// TODO: 2017/3/4 侧边栏组图不会自动加载 
    public void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContants.CATEGORY_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.i("服务器返回结果", result);
                processData(result);
                CacheUtils.setCache(GlobalContants.CATEGORY_URL, mActivity, result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processData(String result) {

        Gson gson = new Gson();

        mNewsData = gson.fromJson(result, NewsMenu.class);
        Log.i("格式化之后的数据", "" + mNewsData.toString());
        MainActivity mainUI = (MainActivity) mActivity;
        LeftFragment leftFragment = mainUI.getLeftFragment();
        leftFragment.setMenuDate(mNewsData.data);
        mMenuDetailPagers = new ArrayList<>();
        mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity,mNewsData.data.get(0).children));
        mMenuDetailPagers.add(new TopicMenuDetailPager(mActivity));
        mMenuDetailPagers.add(new PhotosMenuDetailPager(mActivity,btnPhoto));
        mMenuDetailPagers.add(new InteractMenuDetailPager(mActivity));
        setCurrentDetailPager(0);

    }

    public void setCurrentDetailPager(int postion) {
        BaseMenuDetailPager pager = mMenuDetailPagers.get(postion);
        View view = pager.mRootView;
        flContent.removeAllViews();;
        flContent.addView(view);
        pager.initData();
        tvTitle.setText(mNewsData.data.get(postion).title);
        if (pager instanceof  PhotosMenuDetailPager){
            btnPhoto.setVisibility(View.VISIBLE);
        }else {
            btnPhoto.setVisibility(View.GONE );
        }

    }
}
