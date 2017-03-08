package com.example.administrator.zhbj.base.impl.menu;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhbj.R;
import com.example.administrator.zhbj.Utils.CacheUtils;
import com.example.administrator.zhbj.base.BaseMenuDetailPager;
import com.example.administrator.zhbj.domain.PhotosBean;
import com.example.administrator.zhbj.global.GlobalContants;
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

/**
 * Created by Administrator on 2017/3/4.
 */

public class PhotosMenuDetailPager extends BaseMenuDetailPager implements View.OnClickListener {
    @ViewInject(R.id.lv_photo)
    private ListView lvPhoto;
    @ViewInject(R.id.gv_photo)
    private GridView gvPhoto;
    private ArrayList<PhotosBean.PhotoNews> mNewsList;
    private ImageButton btnPhoto;

    public PhotosMenuDetailPager(Activity mActivity, ImageButton btnPhoto) {
        super(mActivity);
        this.btnPhoto = btnPhoto;
        btnPhoto.setOnClickListener(this);
    }

    @Override
    public View initView() {
      /*  TextView view = new TextView(mActivity);
        view.setText("菜单详情页-组图");
        view.setTextSize(30);
        view.setTextColor(Color.BLUE);
        view.setGravity(Gravity.CENTER);*/
        View view = View.inflate(mActivity, R.layout.pager_photos_menu_detail, null);
        ViewUtils.inject(this, view);

        return view;
    }

    public void initData() {
        String cache = CacheUtils.getCache(GlobalContants.PHOTO_URL, mActivity);
        if (cache != null) {
            processData(cache);
        }
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContants.PHOTO_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                CacheUtils.setCache(GlobalContants.PHOTO_URL, mActivity, result);
                if (result!=null){
                processData(result);}else {
                    Log.i("你好！","fafa");
                }
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
        PhotosBean photosBean = gson.fromJson(result, PhotosBean.class);
        mNewsList = photosBean.data.news;
        lvPhoto.setAdapter(new PhotoAdapter());
        gvPhoto.setAdapter(new PhotoAdapter());
    }


    class PhotoAdapter extends BaseAdapter {

        private final BitmapUtils mBitmapUtils;

        public PhotoAdapter() {
            mBitmapUtils = new BitmapUtils(mActivity);
            mBitmapUtils.configDefaultLoadingImage(R.mipmap.pic_item_list_default);
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public PhotosBean.PhotoNews getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_item_photos, null);
                viewHolder = new ViewHolder();
                viewHolder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            PhotosBean.PhotoNews item = getItem(position);
            viewHolder.tvTitle.setText(item.title);
            mBitmapUtils.display(viewHolder.ivPic, item.listimage);
            return convertView;
        }
    }

    static class ViewHolder {
        public ImageView ivPic;
        public TextView tvTitle;
    }

    private boolean isListView;


    @Override
    public void onClick(View v) {
        if (isListView) {
            lvPhoto.setVisibility(View.GONE);
            gvPhoto.setVisibility(View.VISIBLE);
            btnPhoto.setImageResource(R.mipmap.icon_pic_list_type);
            isListView = false;
        } else {
            lvPhoto.setVisibility(View.VISIBLE);
            gvPhoto.setVisibility(View.GONE);
            btnPhoto.setImageResource(R.mipmap.icon_pic_grid_type);
            isListView =true;
        }
    }
}
