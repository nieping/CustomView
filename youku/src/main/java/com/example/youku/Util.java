package com.example.youku;

import android.animation.ObjectAnimator;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/4/16.
 */

public class Util {

    public static void showLevel(ViewGroup viewGroup) {
        showLevel(viewGroup,0);
    }

    public static void dissLevel(ViewGroup viewGroup) {
       dissLevel(viewGroup,0);
    }
    public static void dissLevel(ViewGroup viewGroup,int time){
        ObjectAnimator animator = ObjectAnimator.ofFloat(viewGroup,"rotation",0,180);
        animator.setDuration(500);
        animator.setStartDelay(time);
        viewGroup.setPivotX(viewGroup.getWidth()/2);
        viewGroup.setPivotY(viewGroup.getHeight());
        animator.start();
    }
    public static void showLevel(ViewGroup viewGroup,int time){
        ObjectAnimator animator = ObjectAnimator.ofFloat(viewGroup,"rotation",180,360);
        animator.setDuration(500);
        animator.setStartDelay(time);
        viewGroup.setPivotX(viewGroup.getWidth()/2);
        viewGroup.setPivotY(viewGroup.getHeight());
        animator.start();
    }
}
