package com.example.viewpeage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tv_text;
    private ViewPager viewPager;
    private LinearLayout ll_point;

    private ArrayList<ImageView> images;
    private ArrayList<TextView> textViews;

    private final int[] imageIds = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
    private final String[] imageDescrip = {"1111111", "222222", "333333", "444444", "555555"};
    private int perPosition = 0;
    private boolean isScroll = false;//是否已经滚动
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = viewPager.getCurrentItem() + 1;
            viewPager.setCurrentItem(item);
            handler.sendEmptyMessageDelayed(0, 4000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_text = findViewById(R.id.tv_text);
        viewPager = findViewById(R.id.viewpager);
        ll_point = findViewById(R.id.ll_point);

        images = new ArrayList<>();
        textViews = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);

            //添加远点
            ImageView point = new ImageView(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            if (i == 0) {
                point.setEnabled(true);//显示红色
            } else {
                point.setEnabled(false);//显示灰色
                params.leftMargin = 8;
            }

            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.point_selector);
            tv_text.setText(imageDescrip[perPosition]);
            ll_point.addView(point);

        }
        viewPager.setAdapter(new MyPageAdapter());
        //设置中间位置
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % images.size();//要保证是imageViews的整数倍
        viewPager.setCurrentItem(item);
        handler.sendEmptyMessageDelayed(0, 3000);
        //viewPager设置监听页面的改变
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 当我们的页面滚动了的时候回调这个方法
             * @param position  当前页面的位置
             * @param positionOffset   华东页面的百分比
             * @param positionOffsetPixels   在屏幕上滑动的像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 当某个页面北选中的时调用
             * @param position 被选中的位置
             */
            @Override
            public void onPageSelected(int position) {
                int realPosition = position % images.size();
                tv_text.setText(imageDescrip[realPosition]);
                ll_point.getChildAt(perPosition).setEnabled(false);
                ll_point.getChildAt(realPosition).setEnabled(true);
                perPosition = realPosition;
            }

            /**
             *  当页面滚动状态的变化
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING){
                    isScroll = true;

                }else if (state == ViewPager.SCROLL_STATE_SETTLING){
                    handler.removeCallbacksAndMessages(null);
                }else if (state == ViewPager.SCROLL_STATE_IDLE && isScroll){
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(0,4000);

                }

            }
        });

    }

    class MyPageAdapter extends PagerAdapter {
        /**
         * @param container viewPager自身
         * @param position  当前实例化页面的位置
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int realPosition = position % images.size();
            final ImageView imageView = images.get(realPosition);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_UP:
                            handler.sendEmptyMessageDelayed(0,4000);
                            break;
                        case MotionEvent.ACTION_DOWN:

                            handler.removeMessages(0);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_CANCEL:
//                            handler.removeCallbacksAndMessages(null);
//                            handler.sendEmptyMessageDelayed(0,4000);
                            break;

                    }
                    return false;
                }
            });
            imageView.setTag(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int postion = (int) v.getTag()%images.size();

                }
            });
            container.addView(imageView);//添加到viewpageg
            return imageView;
        }

        /**
         * 释放资源
         *
         * @param container viewpager
         * @param position  要释放的位置
         * @param object    要释放的页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        /**
         * 得到总数
         *
         * @return
         */
        @Override
        public int getCount() {
            // return imageDescrip.length;
            return Integer.MAX_VALUE;
        }

        /**
         * 比较view和object是否是一个实例
         *
         * @param view   页面
         * @param object instantiateItem返回的结果
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
