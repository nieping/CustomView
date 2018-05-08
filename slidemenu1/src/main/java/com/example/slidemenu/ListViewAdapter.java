package com.example.slidemenu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/4.
 */

class ListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MyBean> list;

    public ListViewAdapter(Context context, ArrayList<MyBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViweHanler hanler;
        if (convertView == null) {
            hanler = new ViweHanler();
            convertView = View.inflate(context, R.layout.item_main, null);
            hanler.content = convertView.findViewById(R.id.item_content);
            hanler.menu = convertView.findViewById(R.id.item_menu);
            convertView.setTag(hanler);
        }
        hanler = (ViweHanler) convertView.getTag();
        hanler.content.setText(list.get(position).getName());
        hanler.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, list.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        hanler.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideLayout slideLayout = (SlideLayout) v.getParent();//获取slideLayout
                slideLayout.closeMenu();//关闭item
                list.remove(list.get(position));
                notifyDataSetChanged();
            }
        });
        SlideLayout slideLayout = (SlideLayout) convertView;
        slideLayout.setOnStateChangeListenter(new MyOnStateChange() {



        });

        return convertView;
    }
    private SlideLayout slideLayout1;
    class MyOnStateChange implements SlideLayout.OnStateChangeListenter {

        @Override
        public void OnClose(SlideLayout layout) {
            if (slideLayout1 == layout) {
                slideLayout1 = null;
            }
        }

        @Override
        public void OnDown(SlideLayout layout) {
            if (slideLayout1 != null && slideLayout1 != layout) {
                slideLayout1.closeMenu();
            }
        }

        @Override
        public void OnOpen(SlideLayout layout) {
            slideLayout1 = layout;
        }
    }
    class ViweHanler {
        TextView content;
        TextView menu;
    }
}
