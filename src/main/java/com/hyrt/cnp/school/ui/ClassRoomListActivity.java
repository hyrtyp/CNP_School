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

import java.util.ArrayList;

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

        String[] resKeys=new String[]{"getImagepath","getRenname"};
        int[] reses=new int[]{R.id.gridview_image,R.id.gridview_name};

        starTeacherAdaptersmall=new ClassRoomImageAdapter(this,getClassRoommodel(model,"3").getData(),R.layout.layout_item_gridview_image,resKeys,reses);
        starTeacherAdaptermun=new ClassRoomImageAdapter(this,getClassRoommodel(model,"2").getData(),R.layout.layout_item_gridview_image,resKeys,reses);
        starTeacherAdaptermax=new ClassRoomImageAdapter(this,getClassRoommodel(model,"1").getData(),R.layout.layout_item_gridview_image,resKeys,reses);
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

    private ClassRoom.Model getClassRoommodel(ClassRoom.Model model,String grade){
        ClassRoom.Model m=new ClassRoom.Model();
        m.setData(new ArrayList<ClassRoom>());

        for (int i=0;i<model.getData().size();i++){
            if(model.getData().get(i).getGrade().equals(grade)){
                m.getData().add(model.getData().get(i));
            }
        }

        return m;
    }
}
