package com.example.quickindex;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/2.
 */

public class ListViewAdapter extends BaseAdapter{
    private ArrayList<Person> list;
    private Context context;


    public ListViewAdapter(Context context,ArrayList list){
        this.list = list;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null){
            convertView = View.inflate(context,R.layout.mian_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tvNane = convertView.findViewById(R.id.tv_name);
            viewHolder.tvWord = convertView.findViewById(R.id.tv_words);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String name  =list.get(position).getName();
        String word = list.get(position).getPinyin().substring(0,1);
        viewHolder.tvNane.setText(name);
        viewHolder.tvWord.setText(word);
        if (position == 0){
            viewHolder.tvWord.setVisibility(View.VISIBLE);
        }else {
            //得到钱一个位置对应的字母，如果当前的字母和上一个相同，隐藏
            String preWord =  list.get(position -1).getPinyin().substring(0,1);
            if (word.equals(preWord)){
                viewHolder.tvWord.setVisibility(View.GONE);
            }else {
                viewHolder.tvWord.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }
    class ViewHolder{
        TextView tvWord;
        TextView tvNane;


    }
}
