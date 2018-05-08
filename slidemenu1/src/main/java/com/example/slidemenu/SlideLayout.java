package com.example.slidemenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2018/5/3.
 * 侧滑菜单
 */

public class SlideLayout extends FrameLayout {
    private View deleteView;
    private View contentView;
    private Scroller scroller;//滚动者

    public OnStateChangeListenter getOnStateChangeListenter() {
        return onStateChangeListenter;
    }

    public void setOnStateChangeListenter(OnStateChangeListenter onStateChangeListenter) {
        this.onStateChangeListenter = onStateChangeListenter;
    }

    private OnStateChangeListenter onStateChangeListenter;

    private int deleteWidth;
    private int contentWidth;
    private int viewHeigt;

    private float startY;
    private float startX;
    private float downX;//只赋值一次
    private float downY;//只赋值一次



    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    /**
     * 得到各个控件的高和宽
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        contentWidth = contentView.getMeasuredWidth();
        deleteWidth = deleteView.getMeasuredWidth();
        viewHeigt = contentView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        deleteView.layout(contentWidth, 0, contentWidth + deleteWidth, viewHeigt);//为deleteView设置位置
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //1.按下时记录位置
                downX = startX = event.getX();
                downY = startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                //2.记录结束值
                float endX = event.getX();
                float endY = event.getY();
                //3.计算偏移量
                float distanceX = endX - startX;

                int toScrollX = (int) (getScrollX() - distanceX);
                if (toScrollX < 0) {
                    toScrollX = 0;
                } else if (toScrollX > deleteWidth) {
                    toScrollX = deleteWidth;
                }
                scrollTo(toScrollX, getScrollY());
                startX = event.getX();
                startY = event.getY();

                /**
                 * 在x轴和Y轴滑动的距离
                 */
                float DX = Math.abs(endX - downX);
                float DY=  Math.abs(endY - downY);
                if (DX > DY &&  DX> 8 ){
                    getParent().requestDisallowInterceptTouchEvent(true);//反拦截，给子视图
                }

                break;
            case MotionEvent.ACTION_UP:
                int totalScrollX = getScrollX();//偏移量
                if (totalScrollX < deleteWidth / 2) {
                    //关闭菜单
                    closeMenu();
                } else {
                    //打开菜单
                    openMenu();
                }

                break;
        }

        return true;
    }

    /**
     * 打开Menu
     */
    private void openMenu() {
        int distanceX = deleteWidth - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, getScrollY());
        invalidate();
        if (onStateChangeListenter != null){
            onStateChangeListenter.OnOpen(this);
        }
    }

    /**
     * 关闭menu
     */
    public void closeMenu() {
        //--->
        int distanceX = 0 - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, getScrollY());
        invalidate();//强制刷新
        if (onStateChangeListenter != null){
            onStateChangeListenter.OnClose(this);
        }
    }

    /**
     * true:拦截孩子的时间，但会执行当前控件的OnTouchEvent
     * false：不拦截孩子的事件，事件继续传递
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //1.按下时记录位置
                downX = startX = event.getX();
                if (onStateChangeListenter != null){
                    onStateChangeListenter.OnDown(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:

                //2.记录结束值
                float endX = event.getX();
                //3.计算偏移量
                float distanceX = endX - startX;

                int toScrollX = (int) (getScrollX() - distanceX);
                if (toScrollX < 0) {
                    toScrollX = 0;
                } else if (toScrollX > deleteWidth) {
                    toScrollX = deleteWidth;
                }
                scrollTo(toScrollX, getScrollY());
                startX = event.getX();

                /**
                 * 在x轴和Y轴滑动的距离
                 */
                float DX = Math.abs(endX - downX);
                if (  DX> 8 ){
                    intercept = true;//反拦截，给子视图
                }

                break;
        }
        return intercept;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }

    }

    /**
     * 当布局文件加载完成的时候回调这个方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        deleteView = getChildAt(1);
    }

    /**
     * 监听SlideLayout
     */
    public interface OnStateChangeListenter{
        void OnClose(SlideLayout  layout);
        void OnDown(SlideLayout layout);
        void OnOpen(SlideLayout layout);
    }
}
