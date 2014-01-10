package com.hyrt.cnp.school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyrt.cnp.school.R;

/**
 * Created by GYH on 14-1-8.
 */
public class StarTeacherAdapter extends BaseAdapter{

    private String[] text;
    private int[] imageid;
    private Context context;
    public StarTeacherAdapter(String[] text,int[] imageid,Context context){
        this.text=text;
        this.imageid=imageid;
        this.context=context;
    }

    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int i) {
        return text[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_gridview_image, null);
        }
        RelativeLayout list_item = (RelativeLayout)convertView.findViewById(R.id.gridview_item);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.gridview_image);
        TextView textView = (TextView)convertView.findViewById(R.id.gridview_name);
        imgView.setImageResource(imageid[i]);
        textView.setText(text[i]);
        return convertView;
    }
}
