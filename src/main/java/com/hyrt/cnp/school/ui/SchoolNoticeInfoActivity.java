package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Notice;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.request.NotNeedLoginNoticeInfoRequest;
import com.hyrt.cnp.school.request.NoticeInfoRequest;
import com.hyrt.cnp.school.requestListener.NoticeInfoRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

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
        LoadData();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        if (data.equals("school")) {
            titletext.setText("通知公告");
        } else if (data.equals("classroom")) {
            titletext.setText("班级公告");
        }
    }

    private void initView() {
        Noticetitle = (TextView) findViewById(R.id.schoolnotice_title);
        Noticetime = (TextView) findViewById(R.id.schoolnotice_time_name);
        Noticecontext = (TextView) findViewById(R.id.notice_context);
    }


    private void LoadData(){
        Notice notice = (Notice) getIntent().getSerializableExtra("notice");
        int sid = getIntent().getIntExtra("sid", -1);
        NoticeInfoRequestListener schoolNoticelistRequestListener = new NoticeInfoRequestListener(this);
        if(sid != -1 && data.equals("school")){
            NotNeedLoginNoticeInfoRequest schoolNoticeListRequest = new NotNeedLoginNoticeInfoRequest(
                    Notice.Model.class, this, data, sid, notice.getAnnource_id()+"");
            spiceManager.execute(schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                    schoolNoticelistRequestListener.start());
        }else{
            NoticeInfoRequest schoolNoticeListRequest = new NoticeInfoRequest(
                    Notice.Model.class, this, data, notice.getAnnource_id()+"");
            spiceManager.execute(schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                    schoolNoticelistRequestListener.start());
        }

    }

    public void UpDataUI(Notice.Model2 model2){
        Noticetitle.setText(model2.getData().getTitle());
        Noticetime.setText("发布人：" + model2.getData().getRenname() + " 发布时间：" + model2.getData().getPosttime2());
        Noticecontext.setText(model2.getData().getContent());
    }
}
