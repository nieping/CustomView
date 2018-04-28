package com.example.autoattribute;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static com.example.autoattribute.R.styleable.MyAttributeView;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyAttributeView extends View {
    private int mAge;
    private String mName;
    private Bitmap bitmap;
    public MyAttributeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //调用系统
        TypedArray typedArray = context.obtainStyledAttributes(attrs, MyAttributeView);
        for (int i = 0;i < typedArray.getIndexCount();i ++){
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.MyAttributeView_name :
                    mName = typedArray.getString(index);
                    break;
                case R.styleable.MyAttributeView_age :
                    mAge = typedArray.getInt(index,0);
                    break;
                case R.styleable.MyAttributeView_bg :
                    Drawable drawable = typedArray.getDrawable(index);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    bitmap = bitmapDrawable.getBitmap();
                    break;

            }
        }
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap,50,560,paint);
        canvas.drawText(mName + "====" +mAge,50,50,paint);
    }
}
