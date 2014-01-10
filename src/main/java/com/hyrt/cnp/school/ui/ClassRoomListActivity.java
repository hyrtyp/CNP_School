package com.hyrt.cnp.school.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.StarTeacherAdapter;
import com.hyrt.cnp.school.api.BaseActivity;
import com.hyrt.cnp.school.view.MyGridView;

/**
 * Created by GYH on 14-1-10.
 */
public class ClassRoomListActivity extends BaseActivity{
    private MyGridView gridviewsmall,gridviewmun,gridviewmax;
    private int[] imageResIdsmall,imageResIdmun,imageResIdmax;
    private String[] Smalltext={"小三班\n班主任：苏菲菲","小三班\n班主任：苏菲菲"};
    private String[] Muntext={"付晓宁\n班主任：苏菲菲"};
    private String[] Maxtext={"付晓宁\n班主任：苏菲菲"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
        initView();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        TextView textView = (TextView) viewTitleBar.findViewById(R.id.action_bar_title_text);
        textView.setText("班级设置");
    }

    private void initView() {
        gridviewsmall = (MyGridView) findViewById(R.id.cnp_gridview_small);
        gridviewmun = (MyGridView) findViewById(R.id.cnp_gridview_mun);
        gridviewmax = (MyGridView) findViewById(R.id.cnp_gridview_max);
        imageResIdsmall = new int[] { R.drawable.classroom1,R.drawable.classroom2};
        imageResIdmun = new int[] { R.drawable.classroom3};
        imageResIdmax = new int[] { R.drawable.classroom2};
        StarTeacherAdapter starTeacherAdaptersmall=new StarTeacherAdapter(Smalltext,imageResIdsmall,this);
        StarTeacherAdapter starTeacherAdaptermun=new StarTeacherAdapter(Muntext,imageResIdmun,this);
        StarTeacherAdapter starTeacherAdaptermax=new StarTeacherAdapter(Maxtext,imageResIdmax,this);
        gridviewsmall.setAdapter(starTeacherAdaptersmall);
        gridviewmun.setAdapter(starTeacherAdaptermun);
        gridviewmax.setAdapter(starTeacherAdaptermax);
    }
}
