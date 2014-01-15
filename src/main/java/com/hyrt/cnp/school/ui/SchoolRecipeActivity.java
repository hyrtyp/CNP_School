package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hyrt.cnp.account.model.Recipe;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.api.BaseActivity;
import com.hyrt.cnp.school.request.SchoolRecipeRequest;
import com.hyrt.cnp.school.requestListener.SchoolRecipeRequestListener;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GYH on 14-1-9.
 */
public class SchoolRecipeActivity extends BaseActivity{
    private ListView noticelistview;
    private SimpleAdapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolrecipe);
        initView();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        TextView textView = (TextView) viewTitleBar.findViewById(R.id.action_bar_title_text);
        textView.setText("每周食谱");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter==null){
            loadSendword();
        }
    }

    private void initView(){
        noticelistview=(ListView)findViewById(R.id.schoolnotice_listview);
        noticelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent().setClass(SchoolRecipeActivity.this,SchoolRepiceInfoActivity.class));
            }
        });
    }

    public void initData(Recipe.Model model){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (int i=0;i<model.getData().size();i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title",model.getData().get(i).getStarttime()+"-"+model.getData().get(i).getEndtime()+"食谱");
            map.put("info",model.getData().get(i).getPosttime());
            list.add(map);
        }
        adapter = new SimpleAdapter(this,list,
                R.layout.layout_item_text, new String[] { "title", "info" },
                new int[] { R.id.item_title, R.id.item_time });
        noticelistview.setAdapter(adapter);
    }

    private void loadSendword(){
        SchoolRecipeRequestListener sendwordRequestListener = new SchoolRecipeRequestListener(this);
        SchoolRecipeRequest schoolRecipeRequest=new SchoolRecipeRequest(Recipe.Model.class,this);
        getSpiceManager().execute(schoolRecipeRequest,schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
