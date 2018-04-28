package com.example.xialakuang;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private EditText input;
    private ImageView downArrow;
    private PopupWindow popupWindow;
    private ListView listView;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downArrow = findViewById(R.id.down_arrow);
        input = findViewById(R.id.et_input);
        mLayoutInflater = LayoutInflater.from(this);

        arrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            arrayList.add(i + "aaaaaaaa" + i);
        }
        listView = new ListView(this);
        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(MainActivity.this);
                    popupWindow.setWidth(input.getWidth());
                    popupWindow.setHeight(400);
                    popupWindow.setContentView(listView);
                    popupWindow.setFocusable(true);
                }
                popupWindow.showAsDropDown(input, 0, 0);


            }

        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = arrayList.get(position);
                input.setText(msg);
                if (popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });

    }

    class ViewHandler {
        private TextView textView;
        private ImageView imageView;
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHandler viewHandler;
            if (convertView == null) {
                viewHandler = new ViewHandler();
               convertView = View.inflate(MainActivity.this,R.layout.item_list,null);
                viewHandler.textView = convertView.findViewById(R.id.tv_text);
                viewHandler.imageView = convertView.findViewById(R.id.delete);
                convertView.setTag(viewHandler);

            } else {
                viewHandler = (ViewHandler) convertView.getTag();

            }
            final String msg = arrayList.get(position);
            viewHandler.textView.setText(msg);

            viewHandler.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arrayList.remove(msg);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }
}
