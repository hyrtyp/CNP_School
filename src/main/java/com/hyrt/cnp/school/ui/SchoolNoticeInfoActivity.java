package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hyrt.cnp.account.model.Notice;
import com.hyrt.cnp.school.R;
import com.jingdong.common.frame.BaseActivity;

/**
 * Created by GYH on 14-1-9.
 */
public class SchoolNoticeInfoActivity extends BaseActivity {

    private TextView Noticetitle;
    private TextView Noticetime;
    private TextView Noticecontext;
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolnoticeinfo);
        initView();
        initData();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        Intent intent = getIntent();
        data=intent.getStringExtra("data");
        if(data.equals("school")){
            titletext.setText("通知公告");
        }else if(data.equals("classroom")){
            titletext.setText("班级公告");
        }
    }

    private void initView(){
        Noticetitle = (TextView)findViewById(R.id.schoolnotice_title);
        Noticetime =(TextView)findViewById(R.id.schoolnotice_time_name);
        Noticecontext =(TextView)findViewById(R.id.notice_context);
    }

    private void initData(){
        Intent intent = getIntent();
        Notice notice = (Notice)intent.getSerializableExtra("notice");
        Noticetitle.setText(notice.getTitle());
        Noticetime.setText("发布人："+notice.getRenname()+" 发布时间："+notice.getPosttime());
        Noticecontext.setText(notice.getContent());
    }
}
