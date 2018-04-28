package com.example.autoattribute;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;

public class MainActivity extends AppCompatActivity {
    private GestureDetector declaration ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        declaration = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            o

        });
    }
}
