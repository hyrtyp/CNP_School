package com.hyrt.cnp.school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.utils.ScreenAdaptHelper;
import com.hyrt.cnp.school.R;

/**
 * Created by Zoe on 2014-04-02.
 */
public class SchoolSearchSpinnerAdapter extends BaseAdapter{

    private String[] array;
    private Context mContext;
    private View focusView;

    private String foucsString = "";

    private OnItemClickListener mListener;

    public SchoolSearchSpinnerAdapter(String[] array, Context context) {
        this.array = array;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return array.length;
    }

    @Override
    public Object getItem(int i) {
        return array[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.school_search_spinner_item, null);
        }
        final TextView textView = (TextView) view.findViewById(R.id.spinner_title);
        RelativeLayout.LayoutParams mParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        textView.setBackground(null);
        if(array[i].equals(foucsString)){
            textView.setBackgroundResource(android.R.color.white);
        }
        int spinnerItemHeight = mContext.getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height);
        if(i == 0){
            mParams.height = spinnerItemHeight;
            textView.setText(array[i]+"");
        } else {
            mParams.height = spinnerItemHeight;

            textView.setText(array[i]+"");
        }
        textView.setLayoutParams(mParams);
        textView.setTag(i);
        textView.setOnClickListener(mOnClickListener);
        return view;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String text = ((TextView) view).getText()+"";
            if(focusView != null){
                focusView.setBackground(null);
            }
            view.setBackgroundResource(android.R.color.white);
            foucsString = text;
            focusView = view;
            if(mListener != null){
                int position = -1;
                if(view.getTag() != null){
                    position = Integer.parseInt(view.getTag()+"");
                }
                mListener.onClick(text, position);
            }
        }
    };

    public void setListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public static interface OnItemClickListener{
        public void onClick(String text, int position);
    }

}
