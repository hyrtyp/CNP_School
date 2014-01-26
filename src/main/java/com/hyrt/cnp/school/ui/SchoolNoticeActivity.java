package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
    private String data;
    private TextView schoolnotice_title,schoolnotice_time_name,schoolnotice_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolnotice);
        initView();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        Intent intent = getIntent();
        data=intent.getStringExtra("data");
        if(data.equals("school")){
            loadSchoolNoticeData();
            titletext.setText("通知公告");
        }else if(data.equals("classroom")){
            loadClassroomNoticeData();
            titletext.setText("班级公告");
        }
    }

    private void initView(){
        schoolnotice_title=(TextView)findViewById(R.id.schoolnotice_title);
        schoolnotice_time_name=(TextView)findViewById(R.id.schoolnotice_time_name);
        schoolnotice_content=(TextView)findViewById(R.id.schoolnotice_content);

        noticelistview=(ListView)findViewById(R.id.schoolnotice_listview);
        noticelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent();
                intent.setClass(SchoolNoticeActivity.this,SchoolNoticeInfoActivity.class);
                intent.putExtra("notice", model.getData().get(i));
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void loadSchoolNoticeData(){
        SchoolNoticeRequestListener schoolNoticelistRequestListener = new SchoolNoticeRequestListener(this);
        SchoolNoticeListRequest schoolNoticeListRequest= new SchoolNoticeListRequest(Notice.Model.class, this,"school");
        spiceManager.execute(schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                schoolNoticelistRequestListener.start());
    }
    private void loadClassroomNoticeData(){
        SchoolNoticeRequestListener schoolNoticelistRequestListener = new SchoolNoticeRequestListener(this);
        SchoolNoticeListRequest schoolNoticeListRequest= new SchoolNoticeListRequest(Notice.Model.class, this,"classroom");
        spiceManager.execute(schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                schoolNoticelistRequestListener.start());
    }

    public void initData(Notice.Model model){
        this.model=model;
        schoolnotice_title.setText(model.getData().get(0).getTitle());
        // TODO modify date
        schoolnotice_time_name.setText("发布人:"+model.getData().get(0).getRenname()+
                    "    发布时间:"+ model.getData().get(0).getPosttime());
//        try {
//            schoolnotice_time_name.setText("发布人:"+model.getData().get(0).getRenname()+
//                    "    发布时间:"+ StringUtils.StimeForday(String.valueOf(model.getData().get(0).getPosttime())));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        schoolnotice_content.setText(model.getData().get(0).getContent());
        model.getData().remove(0);
        schoolNoticeAdapter = new SchoolNoticeAdapter(SchoolNoticeActivity.this,model);
        noticelistview.setAdapter(schoolNoticeAdapter);
    }
}
