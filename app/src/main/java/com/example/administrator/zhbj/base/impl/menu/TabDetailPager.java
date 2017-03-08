package com.example.administrator.zhbj.base.impl.menu;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhbj.R;
import com.example.administrator.zhbj.Utils.CacheUtils;
import com.example.administrator.zhbj.base.BaseMenuDetailPager;
import com.example.administrator.zhbj.domain.NewsMenu.NewsTabData;
import com.example.administrator.zhbj.domain.NewsTabBean;
import com.example.administrator.zhbj.global.GlobalContants;
import com.example.administrator.zhbj.view.PullToRefreshListView;
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
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

;

/**
 * Created by Administrator on 2017/3/4.
 */

public class TabDetailPager extends BaseMenuDetailPager {
    //private TextView view;
    private NewsTabData mTabData;

    @ViewInject(R.id.vp_top_news)
    private TopNewsViewPager mVp;
    @ViewInject(R.id.tv_top_news_title)
    private TextView tvTitle;
    @ViewInject(R.id.indicator)
    private CirclePageIndicator indicator;
    @ViewInject(R.id.lv_tab)
    private PullToRefreshListView mLv;
    private String mUrl;
    private ArrayList<NewsTabBean.TopNews> mTopnews;
    private ArrayList<NewsTabBean.NewsData> mNewsList;
    private NewsAdapter newsAdapter;
    private String mMoreUrl;

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

        View mHeadlerView = View.inflate(mActivity, R.layout.list_item_header, null);
        ViewUtils.inject(this, mHeadlerView);
        mLv.addHeaderView(mHeadlerView);
        mLv.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {
                //加载更多数据
                if (mMoreUrl != null) {
                    getMoreDateFromServer();
                } else {
                    Toast.makeText(mActivity, "没有更多数据了", Toast.LENGTH_SHORT).show();
                    mLv.onRefreshComplete(true);
                }
            }
        });
        return view;
    }

    private void getMoreDateFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                ProcessData(result, true);
                CacheUtils.setCache(mUrl, mActivity, result);

                mLv.onRefreshComplete(true);

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                mLv.onRefreshComplete(false);
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {
        // view.setText(mTabData.title);
        String cache = CacheUtils.getCache(mUrl, mActivity);
        if (!TextUtils.isEmpty(cache)) {
            ProcessData(cache, false);
        }
        getDataFromServer();
    }

    public void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                ProcessData(result, false);
                CacheUtils.setCache(mUrl, mActivity, result);

                mLv.onRefreshComplete(true);

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                mLv.onRefreshComplete(false);
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ProcessData(String result, boolean isMore) {
        Gson gson = new Gson();
        NewsTabBean newsTabBean = gson.fromJson(result, NewsTabBean.class);
        String moreUrl = newsTabBean.data.more;
        if (!TextUtils.isEmpty(moreUrl)) {
            mMoreUrl = GlobalContants.SERVER_URL + moreUrl;
        } else {
            mMoreUrl = null;
        }
        if (!isMore) {
            mTopnews = newsTabBean.data.topnews;

            if (mTopnews != null) {
                mVp.setAdapter(new TopNewsAdapter());
                indicator.setViewPager(mVp);
                indicator.setSnap(true);
                indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                indicator.onPageSelected(0);
            }

            mNewsList = newsTabBean.data.news;
            if (mNewsList != null) {
                newsAdapter = new NewsAdapter();
                mLv.setAdapter(newsAdapter);
            }
        } else {
            ArrayList<NewsTabBean.NewsData> mMoewNews = newsTabBean.data.news;
            mNewsList.addAll(mMoewNews);
            newsAdapter.notifyDataSetChanged();
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

    class NewsAdapter extends BaseAdapter {

        private BitmapUtils mBitmapUtils;

        public NewsAdapter() {
            mBitmapUtils = new BitmapUtils(mActivity);
            mBitmapUtils.configDefaultLoadingImage(R.mipmap.topnews_item_default);
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public NewsTabBean.NewsData getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_item_news, null);
                holder = new ViewHolder();
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NewsTabBean.NewsData newsData = getItem(position);

            holder.tvTitle.setText(newsData.title);
            holder.tvDate.setText(newsData.pubdate);
            mBitmapUtils.display(holder.ivIcon, newsData.listimage);
            return convertView;
        }
    }

    static class ViewHolder {
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView ivIcon;
    }
}
