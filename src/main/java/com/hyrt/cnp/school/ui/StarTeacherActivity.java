package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hyrt.cnp.account.model.Teacher;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.StarTeacherImageAdapter;
import com.hyrt.cnp.school.request.StarTeacherRequest;
import com.hyrt.cnp.school.requestListener.StarTeacherRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-10.
 */
public class StarTeacherActivity extends BaseActivity {

    private GridView gridview;

    private StarTeacherImageAdapter starTeacherImageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starteacher);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(starTeacherImageAdapter==null){
            loadStarteacehr();
        }
    }

    private void initView() {
        gridview = (GridView) findViewById(R.id.cnp_gridview);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(StarTeacherActivity.this,StarTeacherInfoActivity.class);
                intent.putExtra("vo",(Teacher)(starTeacherImageAdapter.getItem(i)));
                startActivity(intent);
            }
        });
    }

    public void initData(Teacher.Model model){
        String[] resKeys=new String[]{"getImagepath","getRenname"};
        int[] reses=new int[]{R.id.gridview_image,R.id.gridview_name};
        starTeacherImageAdapter = new StarTeacherImageAdapter
                (this,model.getData(),R.layout.layout_item_gridview_image,resKeys,reses);
        gridview.setAdapter(starTeacherImageAdapter);
    }

    private void loadStarteacehr(){
        StarTeacherRequestListener sendwordRequestListener = new StarTeacherRequestListener(this);
        StarTeacherRequest starTeacherRequest=new StarTeacherRequest(Teacher.Model.class, this);
        spiceManager.execute(starTeacherRequest, starTeacherRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
