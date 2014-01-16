package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hyrt.cnp.account.model.Notice;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.SchoolNoticeAdapter;
import com.hyrt.cnp.school.request.SchoolNoticeListRequest;
import com.hyrt.cnp.school.requestListener.SchoolNoticeRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-9.
 */
public class SchoolNoticeActivity extends BaseActivity {

    private ListView noticelistview;
    private SchoolNoticeAdapter schoolNoticeAdapter=null;
    private Notice.Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolnotice);
        initView();
    }

    private void initView(){
        noticelistview=(ListView)findViewById(R.id.schoolnotice_listview);
        noticelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent();
                intent.setClass(SchoolNoticeActivity.this,SchoolNoticeInfoActivity.class);
                intent.putExtra("notice", model.getData().get(i));
                startActivity(intent);
            }
        });
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("title", "记一次班级志愿者活动的体验和感受");
//        map.put("info", "2014-10-12");
//        list.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("title", "记一次班级志愿者活动的体验和感受");
//        map.put("info", "2014-10-12");
//        list.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("title", "记一次班级志愿者活动的体验和感受");
//        map.put("info", "2014-10-12");
//        list.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("title", "记一次班级志愿者活动的体验和感受");
//        map.put("info", "2014-10-12");
//        list.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("title", "记一次班级志愿者活动的体验和感受");
//        map.put("info", "2014-10-12");
//        list.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("title", "记一次班级志愿者活动的体验和感受");
//        map.put("info", "2014-10-12");
//        list.add(map);
//        SimpleAdapter adapter = new SimpleAdapter(this,list,
//                R.layout.layout_item_text, new String[] { "title", "info" },
//                new int[] { R.id.item_title, R.id.item_time });
//        noticelistview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(schoolNoticeAdapter==null){
            loadData();
        }
    }

    private void loadData(){
        SchoolNoticeRequestListener schoolNoticelistRequestListener = new SchoolNoticeRequestListener(this);
        SchoolNoticeListRequest schoolNoticeListRequest= new SchoolNoticeListRequest(Notice.Model.class, this);
        spiceManager.execute(schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                schoolNoticelistRequestListener.start());
    }

    public void initData(Notice.Model model){
        this.model=model;
        schoolNoticeAdapter = new SchoolNoticeAdapter(SchoolNoticeActivity.this,model);
        noticelistview.setAdapter(schoolNoticeAdapter);
    }
}
