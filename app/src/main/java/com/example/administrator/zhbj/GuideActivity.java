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
    }

    public void initDate() {
        mImages = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(images[i]);
            mImages.add(view);

        }
    }

    private void initViews() {
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        btnStart = (Button) findViewById(R.id.btn_start);
        llPoint = (LinearLayout) findViewById(R.id.ll_point);
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
