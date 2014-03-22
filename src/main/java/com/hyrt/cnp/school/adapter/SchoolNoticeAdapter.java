package com.hyrt.cnp.school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Notice;
import com.hyrt.cnp.school.R;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-9.
 */
public class SchoolNoticeAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Notice> notices;
    public SchoolNoticeAdapter(Context context,ArrayList<Notice> notices){
        this.context=context;
        this.notices=notices;

    }

    @Override
    public int getCount() {
        return notices.size();
    }

    @Override
    public Object getItem(int i) {
        return notices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_text, null);
            holder.title = (TextView) convertView.findViewById(R.id.item_title);
            holder.text = (TextView) convertView.findViewById(R.id.item_time);
            convertView.setTag(holder);//绑定ViewHolder对象
        }else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }

        holder.title.setText(notices.get(i).getTitle());
        holder.text.setText(notices.get(i).getPosttime2());
        return convertView;
    }

    /**存放控件*/
    public final class ViewHolder{
        public TextView title;
        public TextView text;
    }

}
