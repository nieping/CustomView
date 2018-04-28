package com.example.youku;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements View.OnClickListener {
    private RelativeLayout level1;
    private RelativeLayout level2;
    private RelativeLayout level3;

    private ImageView iconHome;
    private ImageView iconMenu;

    private boolean isLevelShow1 = true;
    private boolean isLevelShow2 = true;
    private boolean isLevelShow3 = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        iconHome = findViewById(R.id.icon_home);
        iconMenu = findViewById(R.id.icon_menu);


        iconMenu.setOnClickListener(this);
        iconHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_home:
               if (isLevelShow2){
                   Util.dissLevel(level2,200);
                   if (isLevelShow3){
                       Util.dissLevel(level3);
                       isLevelShow3 = false;
                   }
                   isLevelShow2 = false;
               }else {
                   Util.showLevel(level2);
                   isLevelShow2 = true;
               }

                break;
            case R.id.icon_menu:
                if (isLevelShow3){
                    Util.dissLevel(level3);
                    isLevelShow3 = false;
                }else {
                    Util.showLevel(level3);
                    isLevelShow3 = true;
                }

                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode ==KeyEvent.KEYCODE_BACK){
           if (isLevelShow2){
               Util.dissLevel(level2,200);
               if (isLevelShow3){
                   Util.dissLevel(level3);
                   isLevelShow3 = false;
               }
               isLevelShow2 = false;
           }
       }
        return true;
    }
}
