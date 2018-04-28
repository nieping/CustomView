package com.example.myviewpeage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity {
    private MyViwePage myViwePage;
    private RadioGroup radioGroup;
    private int ids[] = {R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d,R.mipmap.e};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String str = "13515519130,134536274346,42354264324";
        String sour =  "134536274346";
        String[] strings = str.split("\\,");

        myViwePage = findViewById(R.id.myviewpage);
        for (int i = 0 ; i < ids.length;i++){
            radioGroup = findViewById(R.id.rg);
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);
            myViwePage.addView(imageView);
        }
        View view = View.inflate(this,R.layout.testview_layout,null);
        myViwePage.addView(view,2);
        for (int i = 0;i < myViwePage.getChildCount();i ++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);//0--5的id
            if (i == 0){
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);//radioGroup添加radioButton

        }



        /**
         * 设置RadioGroup选中的状态
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             *
             * @param group
             * @param checkedId
             */

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                myViwePage.scrollTopager(checkedId);//根据下标位置定位到具体的某个页面
            }
        });
        /*
        *
         * 接口调用
         */
        myViwePage.setOnPagerChangListenter(new MyViwePage.OnPagerChangListenter() {
            @Override
            public void onScrollToPager(int position) {
                radioGroup.check(position);
            }
        });
    }


}
