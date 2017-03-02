package com.example.administrator.zhbj;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/1.
 */

public class GuideActivity extends AppCompatActivity {
    private ViewPager vpGuide;
    private Button btnStart;
    private int[] images = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private LinearLayout llPoint;
    private ArrayList<ImageView> mImages;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();
        initDate();
        vpGuide.setAdapter(new GuideAdapter());
    }

    public void initDate() {
        mImages = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(images[i]);
            mImages.add(view);
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_gary);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = 10;
            }
            point.setLayoutParams(params);
            llPoint.addView(point);
        }
        ////TODO 没有设置适配器

    }

    private void initViews() {
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        btnStart = (Button) findViewById(R.id.btn_start);
        llPoint = (LinearLayout) findViewById(R.id.ll_point);
        ////TODO 还没找到小红点
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mImages.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
