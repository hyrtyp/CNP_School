package com.hyrt.cnp.school.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.api.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GYH on 14-1-9.
 */
public class SchoolRecipeActivity extends BaseActivity{
    private ListView noticelistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolrecipe);
        initView();
    }

    private void initView(){
        noticelistview=(ListView)findViewById(R.id.schoolnotice_listview);
        noticelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                startActivity(new Intent().setClass(SchoolRecipeActivity.this,SchoolNoticeInfoActivity.class));
            }
        });
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "2013年12月23日-29日食谱");
        map.put("info", "2014-10-12");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "2013年12月23日-29日食谱");
        map.put("info", "2014-10-12");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "2013年12月23日-29日食谱");
        map.put("info", "2014-10-12");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "2013年12月23日-29日食谱");
        map.put("info", "2014-10-12");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "2013年12月23日-29日食谱");
        map.put("info", "2014-10-12");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "2013年12月23日-29日食谱");
        map.put("info", "2014-10-12");
        list.add(map);
        SimpleAdapter adapter = new SimpleAdapter(this,list,
                R.layout.layout_item_text, new String[] { "title", "info" },
                new int[] { R.id.item_title, R.id.item_time });
        noticelistview.setAdapter(adapter);
    }
}
