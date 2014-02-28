package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.account.model.Notice;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.SchoolNoticeAdapter;
import com.hyrt.cnp.school.request.SchoolNoticeListRequest;
import com.hyrt.cnp.school.requestListener.SchoolNoticeRequestListener;
import com.hyrt.cnp.view.XListView;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-9.
 */
public class SchoolNoticeActivity extends BaseActivity {

    private XListView noticelistview;
    private SchoolNoticeAdapter schoolNoticeAdapter = null;
    //    private Notice.Model models;
    private String data;
    private Notice notice;
    private TextView schoolnotice_title, schoolnotice_time_name, schoolnotice_content;
    private LinearLayout noticefirst;

    private ArrayList<Notice> notices = new ArrayList<Notice>();
    private ArrayList<Notice> notices2 = new ArrayList<Notice>();
    private String STATE;
    final private String REFRESH = "refresh";
    final private String ONLOADMORE = "onLoadMore";
    final private String HASDATA = "hasdata";
    private String more = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolnotice);
        initView();
        STATE = HASDATA;
        loadData();
    }

    /**
     * 更新actionbar标题
     */
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

    private void loadData() {
        if (data.equals("school")) {
            loadSchoolNoticeData();
        } else if (data.equals("classroom")) {
            loadClassroomNoticeData();
        }
    }

    /**
     * 初始化ui界面
     */

    private void initView() {
        noticefirst = (LinearLayout) findViewById(R.id.notice_first);
        schoolnotice_title = (TextView) findViewById(R.id.schoolnotice_title);
        schoolnotice_time_name = (TextView) findViewById(R.id.schoolnotice_time_name);
        schoolnotice_content = (TextView) findViewById(R.id.schoolnotice_content);

        noticelistview = (XListView) findViewById(R.id.schoolnotice_listview);
        noticelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(SchoolNoticeActivity.this, SchoolNoticeInfoActivity.class);
                intent.putExtra("notice", notices.get(i));
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
        noticelistview.setPullLoadEnable(true);
        noticelistview.setPullRefreshEnable(true);
        noticelistview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if (STATE.equals(HASDATA) || STATE.equals(ONLOADMORE)) {
                    Toast.makeText(SchoolNoticeActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    STATE = REFRESH;
                    more = "1";
                    loadData();
                }
                noticelistview.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                if (STATE.equals(HASDATA) || STATE.equals(REFRESH)) {
                    Toast.makeText(SchoolNoticeActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    loadData();
                }
                noticelistview.stopLoadMore();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 学习公告请求方法
     */
    private void loadSchoolNoticeData() {
        SchoolNoticeRequestListener schoolNoticelistRequestListener = new SchoolNoticeRequestListener(this);
        SchoolNoticeListRequest schoolNoticeListRequest = new SchoolNoticeListRequest(Notice.Model.class, this, "school", more);
        spiceManager.execute(schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                schoolNoticelistRequestListener.start());
    }

    /**
     * 班级公告请求方法
     */

    private void loadClassroomNoticeData() {
        SchoolNoticeRequestListener schoolNoticelistRequestListener = new SchoolNoticeRequestListener(this);
        SchoolNoticeListRequest schoolNoticeListRequest = new SchoolNoticeListRequest(Notice.Model.class, this, "classroom", more);
        spiceManager.execute(schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                schoolNoticelistRequestListener.start());
    }

    /**
     * 监听更新ui界面数据
     */
    public void initData(Notice.Model model) {
        if (model == null && notices.size() == 0) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView) findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        } else if (model == null) {
            Toast.makeText(SchoolNoticeActivity.this, "已经全部加载", Toast.LENGTH_SHORT).show();
        } else {
            more = model.getMore();
            if (STATE.equals(REFRESH)) {//如果正在刷新就清空
                notices.clear();
            }
            notices.addAll(model.getData());
            notice = model.getData().get(0);
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
            notices2.clear();
            notices2.addAll(notices);
            notices2.remove(0);//移除第一个
            if (schoolNoticeAdapter == null) {
                schoolNoticeAdapter = new SchoolNoticeAdapter(SchoolNoticeActivity.this, notices2);
                noticelistview.setAdapter(schoolNoticeAdapter);
            } else {
                schoolNoticeAdapter.notifyDataSetChanged();
            }
        }
        STATE = "";//清空状态
    }
}
