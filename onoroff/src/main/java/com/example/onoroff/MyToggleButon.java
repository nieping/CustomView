package com.example.onoroff;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/4/19.
 * 自定义开关
 * 1.构造方法势力化类
 * 2.测量 measure（int，int) ---》onMeasure
 * 如果当前view是一个viewGroup，还有义务测量子view
 * 子view是建议权
 * 3.指定位置--layout----->onLayout
 * 制定控件的的位置，一般view不用谢这个方法，viewGroup的时候才需要，一般view不需要重新改方法
 * 4。绘制视图--->draw--->onDrow(canvas)
 * 更具上面两个方法参数，进行绘制
 */

public class MyToggleButon extends View implements View.OnClickListener {
    private Bitmap backgroundBitmap;
    private Bitmap slidingBitmap;
    private int slidLeftMax;
    private Paint paint;
    private int slidLeft;
    private boolean isOpen = false;
    private float startX;
    private boolean isEnableCilck = true;//ture点击事件生效，滑动事件生效
    private float lastX;//原始值

    //如果要在布局文件中使用该类，必须使用该构造方法，不然会崩溃
    public MyToggleButon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        slidLeftMax = backgroundBitmap.getWidth() - slidingBitmap.getWidth();
        setOnClickListener(this);
    }

    /**
     * 视图的测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        canvas.drawBitmap(slidingBitmap, slidLeft, 0, paint);
    }

    @Override
    public void onClick(View v) {
        if (isEnableCilck) {
            isOpen = !isOpen;
            fushView();


        }
    }

    private void fushView() {

        if (isOpen) {
            slidLeft = slidLeftMax;
        } else {
            slidLeft = 0;
        }
        invalidate();//强制绘制
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);//执行父类的方法
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (slidLeft > slidLeftMax / 2) {
                    //显示开
                    isOpen = true;
                    fushView();
                } else {
                    isOpen = false;
                    fushView();
                }
                break;
            case MotionEvent.ACTION_DOWN:

                //1.记录按下的坐标
                lastX = startX = event.getX();
                isEnableCilck = true;
                break;
            case MotionEvent.ACTION_MOVE:

                isEnableCilck = false;
                //2.计算结束值
                float endX = event.getX();
                //3.计算偏移量
                float distanceX = endX - startX;
                slidLeft += distanceX;
                //4.屏蔽非法值
                if (slidLeft < 0) {
                    slidLeft = 0;
                } else if (slidLeft > slidLeftMax) {
                    slidLeft = slidLeftMax;
                }
                //5.刷新
                invalidate();
                //6.数据还原
                startX = event.getX();
                if (Math.abs(endX - lastX) > 5) {
                    isEnableCilck = false;

                }
                break;
        }
        return true;
    }
}
