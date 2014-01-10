package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.SchoolIndexAdapter;
import com.hyrt.cnp.school.api.BaseActivity;

/**
 * Created by GYH on 14-1-8.
 */
public class SchoolIndexActivity extends BaseActivity{

    private GridView gridView;
    private int[] imageResId;
    private String[] text={"通知公告","园长寄语","活动剪辑","明星教师","明星宝宝","园所介绍","班级设置","每周食谱"};
    private int[] bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolindex);
        initView();
    }



    private void initView(){
        imageResId = new int[] { R.drawable.schoolindex_notice, R.drawable.schoolindex_sendwend,
                R.drawable.schoolindex_photo,
                R.drawable.schoolindex_starteacher, R.drawable.schoolindex_starbabay,
                R.drawable.schoolindex_schoolinfo,
                R.drawable.schoolindex_classroom,R.drawable.schoolindex_recipe};
        bg = new int[]{R.color.schoolindex_notice,R.color.schoolindex_sendword,
                R.color.schoolindex_photo,R.color.schoolindex_starteacher,
                R.color.schoolindex_starstudent,R.color.schoolindex_schoolinfo,
                R.color.schoolindex_classroom,R.color.schoolindex_recipe};
        gridView = (GridView) findViewById(R.id.schoolindexlist);
        SchoolIndexAdapter schoolIndexAdapter=new SchoolIndexAdapter(text,imageResId,bg,this);
        gridView.setAdapter(schoolIndexAdapter);
        gridView.setOnItemClickListener(new ItemClickListener());
    }

    class  ItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3) {
            int i = arg2;
            switch (i){
                case 0:
                    startActivity(new Intent().setClass(SchoolIndexActivity.this,SchoolNoticeActivity.class));
                    break;
                case 1:
                    startActivity(new Intent().setClass(SchoolIndexActivity.this,SendwordActivity.class));
                    break;
                case 2:
                    break;
                case 3:
                    startActivity(new Intent().setClass(SchoolIndexActivity.this,StarTeacherActivity.class));
                    break;
                case 4:
                    startActivity(new Intent().setClass(SchoolIndexActivity.this,StarBabayActivity.class));
                    break;
                case 6:
                    startActivity(new Intent().setClass(SchoolIndexActivity.this,ClassRoomListActivity.class));
                    break;
                case 7:
                    startActivity(new Intent().setClass(SchoolIndexActivity.this,SchoolRecipeActivity.class));
                    break;

            }
        }
    }
}
