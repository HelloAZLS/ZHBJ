package com.example.administrator.zhbj.base.impl.menu;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhbj.R;
import com.example.administrator.zhbj.Utils.CacheUtils;
import com.example.administrator.zhbj.base.BaseMenuDetailPager;
import com.example.administrator.zhbj.domain.NewsMenu.NewsTabData;
import com.example.administrator.zhbj.domain.NewsTabBean;
import com.example.administrator.zhbj.global.GlobalContants;
import com.example.administrator.zhbj.view.TopNewsViewPager;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

;

/**
 * Created by Administrator on 2017/3/4.
 */

public class TabDetailPager extends BaseMenuDetailPager {
    private TextView view;
    private NewsTabData mTabData;

    @ViewInject(R.id.vp_top_news)
    private TopNewsViewPager mVp;
    @ViewInject(R.id.tv_top_news_title)
    private TextView tvTitle;
    private String mUrl;
    private ArrayList<NewsTabBean.TopNews> mTopnews;

    public TabDetailPager(Activity mActivity, NewsTabData newsTabData) {
        super(mActivity);
        mTabData = newsTabData;

        mUrl = GlobalContants.SERVER_URL + mTabData.url;
    }

    @Override
    public View initView() {
/*
        view = new TextView(mActivity);
        view.setTextSize(20);
        view.setTextColor(Color.BLUE);
        view.setGravity(Gravity.CENTER);*/
        View view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        // view.setText(mTabData.title);
        String cache = CacheUtils.getCache(mUrl, mActivity);
        if (!TextUtils.isEmpty(cache)) {
            ProcessData(cache);
        }
        getDataFromServer();
    }

    public void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                ProcessData(result);
                CacheUtils.setCache(mUrl, mActivity, result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ProcessData(String result) {
        Gson gson = new Gson();
        NewsTabBean newsTabBean = gson.fromJson(result, NewsTabBean.class);
        mTopnews = newsTabBean.data.topnews;
        if (mTopnews != null) {
            //// TODO: 2017/3/5    没有图像出来
            mVp.setAdapter(new TopNewsAdapter());
            mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    tvTitle.setText(mTopnews.get(position).title);
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            tvTitle.setText(mTopnews.get(0).title);
        }

    }

    class TopNewsAdapter extends PagerAdapter {

        private BitmapUtils MbitmapUtils;


        public TopNewsAdapter() {
            MbitmapUtils = new BitmapUtils(mActivity);
            MbitmapUtils.configDefaultLoadingImage(R.mipmap.topnews_item_default);
        }

        @Override
        public int getCount() {
            return mTopnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(mActivity);
//            view.setBackgroundResource(R.mipmap.topnews_item_default);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            String imgUrl = mTopnews.get(position).topimage;
            MbitmapUtils.display(view, imgUrl);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
