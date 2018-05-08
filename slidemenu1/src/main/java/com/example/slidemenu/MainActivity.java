package com.example.slidemenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ListView listView;
    private ArrayList<MyBean> list;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        listView = findViewById(R.id.lv_list);
        list = new ArrayList<>();
        adapter = new ListViewAdapter(this,list);
        for(int i  = 0; i < 100;i ++){
            list.add(new MyBean("Content" + i));
        }
        listView.setAdapter(adapter);
    }
}
