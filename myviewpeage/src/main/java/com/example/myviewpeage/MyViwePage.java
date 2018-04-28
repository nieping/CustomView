package com.example.myviewpeage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/4/25.
 */

public class MyViwePage extends ViewGroup {
    private GestureDetector detector;//定义手势识别器
    private Context context;
    private int currenIndex;//当前页面的下标位置
    private float startX;
    private MyScroller scroller;
    private OnPagerChangListenter mOnPagerChangListenter;
    private float downX;
    private float downY;

    public MyViwePage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        scroller = new MyScroller();
        /**
         * 实例化手势识别器
         */
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return super.onDown(e);
            }

            /**
             * 长安
             * @param e
             */
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

            /**
             *滑动
             * @param e1
             * @param e2
             * @param distanceX 在X轴移动的距离
             * @param distanceY 在y轴移动的距离
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                /**
                 * 滑动方法
                 * x:要在X轴移动的距离
                 *  y:要在y轴移动的距离
                 */
                scrollBy((int) distanceX, getScrollY());
                return true;
            }

            /**
             * 双击
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }


        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float endX;
                endX = event.getX();
                int tempIndex = currenIndex;
                if ((startX - endX) > getWidth() / 2) {
                    tempIndex++;
                } else if ((endX - startX) > getWidth() / 2) {
                    tempIndex--;
                }
                //根据下标位置移动到指定页面
                scrollTopager(tempIndex);
                break;

        }
        /**
         * 2.在 detector.onTouchEvent(event); 将事件传递给时候识别器
         */

        return true;
    }

    /**
     * 屏蔽非法值,根据位置移动到制定位置
     *
     * @param tempIndex
     */
    public void scrollTopager(int tempIndex) {
        if (tempIndex < 0) {
            tempIndex = 0;
        }
        if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }
        //当前页面的下标位置
        currenIndex = tempIndex;

        /**
         * 页面改变的时候调用
         */
        if (mOnPagerChangListenter != null) {
            mOnPagerChangListenter.onScrollToPager(currenIndex);
        }

        //总距离
        float distanceX = currenIndex * getWidth() - getScaleX();
        //移动到指定位置
        // scrollTo(currenIndex * getWidth(), 0);

        scroller.startScroll((int) getScaleX(), (int) getScaleY(), (int) distanceX, 0);
        invalidate();//
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            float currX = scroller.getCurrX();//得到移动这一小段的对应的坐标
            Log.e("computeScroll", "computeScroll: " + currX);
            scrollTo((int) currX, 0);
            invalidate();
        }
    }

    /**
     * 测量view
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec MeasureSpec.AT_MOST;
     *                          MeasureSpec.EXACTLY;
     *                          MeasureSpec.UNSPECIFIED;
     *                          <p>
     *                          measure 是view调用
     *                          measureChilt  是viewGroup调用
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        Log.e("onMeasure", "  mode======" + mode + "size ===" + size);
        int make = MeasureSpec.makeMeasureSpec(mode, size);
        ViewConfiguration.get(context).getScaledTouchSlop();


    }

    /**
     * 监听页面改变
     */

    public interface OnPagerChangListenter {
        /**
         * 当页面改变的时候回调这个方法
         *
         * @param position 当前页面的下标
         */
        void onScrollToPager(int position);
    }

    /**
     * 设置页面改变的监听
     *
     * @param onPagerChangListenter
     */
    public void setOnPagerChangListenter(OnPagerChangListenter onPagerChangListenter) {
        mOnPagerChangListenter = onPagerChangListenter;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 如果横向滑动大于竖直
     * @param ev
     * @return 如果当前方法，返回ture，拦截事件，将会触发当前空间的onTouchEvent（）方法
     *          如果当前方法，返回false，不拦截事件，事件继续传递给子view
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev); //将拦截事件 传递给手势识别器
         boolean result = false;

         switch (ev.getAction()){
             case MotionEvent.ACTION_DOWN :
                 //1.记录坐标
                 downX = ev.getX();
                 downY = ev.getY();
                 break;
             case MotionEvent.ACTION_UP:

                 break;
             case MotionEvent.ACTION_MOVE :
                 //2.记录结束坐标
                 float endX= ev.getX();
                 float endY = ev.getY();

                 //3.计算绝对值
                 float distanceX = Math.abs(endX - downX);
                 float distanceY = Math.abs(endY - downY);

                 if (distanceX > distanceY && distanceX > 5){
                     result=  true;
                 }
                 break;
         }
        return result;
    }
}
