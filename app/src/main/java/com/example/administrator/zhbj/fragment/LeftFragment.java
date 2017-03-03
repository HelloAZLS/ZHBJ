package com.example.administrator.zhbj.fragment;

import android.view.View;

import com.example.administrator.zhbj.R;

/**
 * Created by Administrator on 2017/3/3.
 */

public class LeftFragment extends BaseFragment {
    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
       View view =  View.inflate(mActivity, R.layout.fragment_left,null);
        return view;
    }
}
