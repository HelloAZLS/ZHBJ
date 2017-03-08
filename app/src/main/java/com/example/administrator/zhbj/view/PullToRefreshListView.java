package com.example.administrator.zhbj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.zhbj.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/8.
 */

public class PullToRefreshListView extends ListView {

    private final static int STATE_PULL_TO_REFRESH = 1;
    private final static int STATE_RELEASE_REFRESH = 2;
    private final static int STATE_REFRESHING = 3;
    private int mCurrentState = STATE_PULL_TO_REFRESH;

    private View mHeaderView;
    private int sY = -1;
    private int mHeight;
    private TextView mTvTitle;
    private TextView mTvTime;
    private ImageView mIvArrow;
    private ProgressBar mPbLoad;
    private RotateAnimation aniDowm;
    private RotateAnimation animUp;

    public PullToRefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.pull_to_refresh, null);
        mTvTitle = (TextView) mHeaderView.findViewById(R.id.tv_title);
        mTvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);
        mIvArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arrow);
        mPbLoad = (ProgressBar) mHeaderView.findViewById(R.id.pb_loading);
        this.addHeaderView(mHeaderView);
        mHeaderView.measure(0, 0);
        mHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mHeight, 0, 0);
        initAnim();
        setCurrentTime();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (sY == -1) {
                    sY = (int) ev.getY();
                }
                if (mCurrentState == STATE_REFRESHING) {
                    break;
                }
                int evY = (int) ev.getY();
                int dy = evY - sY;
                int firstVisiblePosition = getFirstVisiblePosition();
                if (dy > 0 && firstVisiblePosition == 0) {
                    int padding = dy - mHeight;
                    mHeaderView.setPadding(0, padding, 0, 0);
                    if (padding > 0 && mCurrentState != STATE_RELEASE_REFRESH) {
                        mCurrentState = STATE_RELEASE_REFRESH;
                        refreshView();
                    } else if (padding < 0 && mCurrentState != STATE_PULL_TO_REFRESH) {
                        mCurrentState = STATE_PULL_TO_REFRESH;
                        refreshView();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                sY = -1;
                if (mCurrentState == STATE_RELEASE_REFRESH) {
                    mCurrentState = STATE_REFRESHING;
                    refreshView();
                    mHeaderView.setPadding(0, 0, 0, 0);
                    if (mListener != null) {
                        mListener.onRefresh();
                    }
                } else if (mCurrentState == STATE_PULL_TO_REFRESH) {
                    mHeaderView.setPadding(0, -mHeight, 0, 0);
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    public void initAnim() {
        aniDowm = new RotateAnimation(-180, 0, Animation.
                RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        aniDowm.setDuration(1000);
        aniDowm.setFillAfter(true);
        animUp = new RotateAnimation(0, -180, Animation.
                RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(1000);
        animUp.setFillAfter(true);
    }

    private void refreshView() {
        switch (mCurrentState) {
            case STATE_PULL_TO_REFRESH:
                mTvTitle.setText("下拉刷新");
                mPbLoad.setVisibility(View.INVISIBLE);
                mIvArrow.setVisibility(View.VISIBLE);
                mIvArrow.startAnimation(aniDowm);
                break;
            case STATE_RELEASE_REFRESH:
                mTvTitle.setText("松开刷新");
                mPbLoad.setVisibility(View.INVISIBLE);
                mIvArrow.setVisibility(View.VISIBLE);
                mIvArrow.startAnimation(animUp);
                break;
            case STATE_REFRESHING:
                mTvTitle.setText("刷新中....");
                mIvArrow.clearAnimation();
                mPbLoad.setVisibility(View.VISIBLE);
                mIvArrow.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void setCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm;ss");
        String time = simpleDateFormat.format(new Date());
        mTvTime.setText(time);

    }

    public void onRefreshComplete(Boolean success) {
        if (success) {
            mHeaderView.setPadding(0, -mHeight, 0, 0);
            mCurrentState = STATE_PULL_TO_REFRESH;
            mPbLoad.setVisibility(View.INVISIBLE);
            mIvArrow.setVisibility(View.VISIBLE);
            setCurrentTime();
        }
    }

    private OnRefreshListener mListener;

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    public interface OnRefreshListener {
        public void onRefresh();
    }
}
