package com.hyrt.cnp.school.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.hyrt.cnp.account.model.ClassRoom;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.ClassRoomImageAdapter;
import com.hyrt.cnp.school.request.ClassRoomListRequest;
import com.hyrt.cnp.school.requestListener.ClassRoomListRequestListener;
import com.hyrt.cnp.school.view.MyGridView;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-10.
 */
public class ClassRoomListActivity extends BaseActivity {
    private MyGridView gridviewsmall,gridviewmun,gridviewmax;
    private int[] imageResIdsmall,imageResIdmun,imageResIdmax;
    private String[] Smalltext={"小三班\n班主任：苏菲菲","小三班\n班主任：苏菲菲"};
    private String[] Muntext={"付晓宁\n班主任：苏菲菲"};
    private String[] Maxtext={"付晓宁\n班主任：苏菲菲"};
    private ClassRoomImageAdapter starTeacherAdaptersmall=null;
    private ClassRoomImageAdapter starTeacherAdaptermun=null;
    private ClassRoomImageAdapter starTeacherAdaptermax=null;
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

    @Override
    protected void onStart() {
        super.onStart();
        if(starTeacherAdaptersmall==null){
            loadSendword();
        }
    }

    private void initView() {
        gridviewsmall = (MyGridView) findViewById(R.id.cnp_gridview_small);
        gridviewmun = (MyGridView) findViewById(R.id.cnp_gridview_mun);
        gridviewmax = (MyGridView) findViewById(R.id.cnp_gridview_max);
        imageResIdsmall = new int[] { R.drawable.classroom1,R.drawable.classroom2};
        imageResIdmun = new int[] { R.drawable.classroom3};
        imageResIdmax = new int[] { R.drawable.classroom2};
    }
    public void initData(ClassRoom.Model model){
        starTeacherAdaptersmall=new ClassRoomImageAdapter(model,imageResIdsmall,this);
        starTeacherAdaptermun=new ClassRoomImageAdapter(model,imageResIdmun,this);
        starTeacherAdaptermax=new ClassRoomImageAdapter(model,imageResIdmax,this);
        gridviewsmall.setAdapter(starTeacherAdaptersmall);
        gridviewmun.setAdapter(starTeacherAdaptermun);
        gridviewmax.setAdapter(starTeacherAdaptermax);
    }

    private void loadSendword(){
        ClassRoomListRequestListener sendwordRequestListener = new ClassRoomListRequestListener(this);
        ClassRoomListRequest classRoomListRequest=new ClassRoomListRequest(ClassRoom.Model.class,this);
        spiceManager.execute(classRoomListRequest, classRoomListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
