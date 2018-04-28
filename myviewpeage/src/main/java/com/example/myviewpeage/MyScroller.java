package com.example.myviewpeage;

import android.os.SystemClock;

/**
 * Created by Administrator on 2018/4/26.
 */

public class MyScroller {
    private float startX;
    private float startY;
    private int distanceX;
    private int distanceY;

    private long startTime;//开始的时间
    private long totalTime = 50;
    private boolean isFinish;//是否移动完成，false 没有完成， ture完成移动
    private float currX;//得到坐标

    /**
     * @param startX    x轴的起始坐标
     * @param startY    y轴的起始坐标
     * @param distanceX x轴的移动距离
     * @param distanceY y轴的移动距离
     */
    public void startScroll(float startX, float startY, int distanceX, int distanceY) {
        this.startX = startX;
        this.startY = startY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.startTime = SystemClock.uptimeMillis();//系统开机时间
        this.isFinish = false;
    }

    /**
     * 速度，
     * 求移动一小段的距离
     * 求移动一小段对应的坐标
     * 求移动一小段对应的时间
     *
     * @return ture zheng
     */
    public boolean computeScrollOffset() {
        if (isFinish) {
            return false;
        }
        long endTime = SystemClock.uptimeMillis();//小段的结束时间
        long passTime = endTime - startTime;//一小段使用的时间

        if (passTime < totalTime) {
            //还没有移动结束
            //计算平均速度
            float distanceSamllX = passTime * distanceX / totalTime;//小段距离；

            currX = startX + distanceSamllX;//移动这一小段后的x轴坐标
        } else {
            //移动结束
            isFinish = true;
            currX = startX + distanceX;

        }
        return true;
    }

    public float getCurrX() {
        return currX;
    }
}
