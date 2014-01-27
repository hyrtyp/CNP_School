package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
    private SchoolNoticeAdapter schoolNoticeAdapter = null;
    private Notice.Model model;
    private String data;
    private Notice notice;
    private TextView schoolnotice_title, schoolnotice_time_name, schoolnotice_content;
    private LinearLayout noticefirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolnotice);
        initView();
    }

    /**
     * 更新actionbar标题
     * */
    @Override
    protected void initTitleview() {
        super.initTitleview();
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        if (data.equals("school")) {
            loadSchoolNoticeData();
            titletext.setText("通知公告");
        } else if (data.equals("classroom")) {
            loadClassroomNoticeData();
            titletext.setText("班级公告");
        }
    }
    /**
     * 初始化ui界面
     * */

    private void initView() {
        noticefirst=(LinearLayout)findViewById(R.id.notice_first);
        schoolnotice_title = (TextView) findViewById(R.id.schoolnotice_title);
        schoolnotice_time_name = (TextView) findViewById(R.id.schoolnotice_time_name);
        schoolnotice_content = (TextView) findViewById(R.id.schoolnotice_content);

        noticelistview = (ListView) findViewById(R.id.schoolnotice_listview);
        noticelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(SchoolNoticeActivity.this, SchoolNoticeInfoActivity.class);
                intent.putExtra("notice", model.getData().get(i));
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 学习公告请求方法
     * */
    private void loadSchoolNoticeData() {
        SchoolNoticeRequestListener schoolNoticelistRequestListener = new SchoolNoticeRequestListener(this);
        SchoolNoticeListRequest schoolNoticeListRequest = new SchoolNoticeListRequest(Notice.Model.class, this, "school");
        spiceManager.execute(schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                schoolNoticelistRequestListener.start());
    }

    /**
     * 班级公告请求方法
     * */

    private void loadClassroomNoticeData() {
        SchoolNoticeRequestListener schoolNoticelistRequestListener = new SchoolNoticeRequestListener(this);
        SchoolNoticeListRequest schoolNoticeListRequest = new SchoolNoticeListRequest(Notice.Model.class, this, "classroom");
        spiceManager.execute(schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                schoolNoticelistRequestListener.start());
    }

    /**
     * 监听更新ui界面数据
     * */
    public void initData(final Notice.Model model) {
        this.model = model;
        notice=model.getData().get(0);
        schoolnotice_title.setText(model.getData().get(0).getTitle());

        schoolnotice_time_name.setText("发布人:" + model.getData().get(0).getRenname() +
                "    发布时间:" + model.getData().get(0).getPosttime2());

        schoolnotice_content.setText(model.getData().get(0).getContent());
        noticefirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SchoolNoticeActivity.this, SchoolNoticeInfoActivity.class);
                intent.putExtra("notice", notice);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
        model.getData().remove(0);
        schoolNoticeAdapter = new SchoolNoticeAdapter(SchoolNoticeActivity.this, model);
        noticelistview.setAdapter(schoolNoticeAdapter);
    }
}
