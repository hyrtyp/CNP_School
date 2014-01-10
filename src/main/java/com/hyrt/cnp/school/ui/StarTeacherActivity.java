package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.StarTeacherAdapter;
import com.hyrt.cnp.school.api.BaseActivity;

/**
 * Created by GYH on 14-1-10.
 */
public class StarTeacherActivity extends BaseActivity {

    private GridView gridview;
    private int[] imageResId;
    private String[] text={"付晓宁","李妍熙","任静","闫薇薇"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starteacher);
        initView();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        TextView textView = (TextView) viewTitleBar.findViewById(R.id.action_bar_title_text);
        textView.setText("明星教师");
    }

    private void initView() {
        gridview = (GridView) findViewById(R.id.cnp_gridview);
        imageResId = new int[] { R.drawable.imge_test,R.drawable.image_test2
                ,R.drawable.image_test3,R.drawable.image_test4};
        StarTeacherAdapter starTeacherAdapter=new StarTeacherAdapter(text,imageResId,this);
        gridview.setAdapter(starTeacherAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent().setClass(StarTeacherActivity.this,StarTeacherInfoActivity.class));
            }
        });
    }
}
