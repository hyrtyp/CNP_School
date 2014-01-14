package com.hyrt.cnp.school.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.GridviewImageAdapter;
import com.hyrt.cnp.school.api.BaseActivity;

/**
 * Created by GYH on 14-1-10.
 */
public class StarBabayActivity extends BaseActivity {
    private GridView gridview;
    private int[] imageResId;
    private String[] text={"andy丽丽","许安安","甄炎","燕燕"};
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
        textView.setText("明星宝宝");
    }

    private void initView() {
        gridview = (GridView) findViewById(R.id.cnp_gridview);
        imageResId = new int[] { R.drawable.babay1,R.drawable.babay2
                ,R.drawable.babay3,R.drawable.babay4};
        GridviewImageAdapter starTeacherAdapter=new GridviewImageAdapter(text,imageResId,this);
        gridview.setAdapter(starTeacherAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowPop(gridview);
            }
        });
    }

    private PopupWindow popWin;
    public  void ShowPop(View view) {
        View popView = this.getLayoutInflater().inflate(
                R.layout.layout_popwindwos, null);
        popWin = new PopupWindow(popView, ViewPager.LayoutParams.FILL_PARENT,
                ViewPager.LayoutParams.FILL_PARENT);
        popView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWin.dismiss();
            }
        });
        // 需要设置一下此参数，点击外边可消失
        popWin.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        popWin.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popWin.setFocusable(true);
        popWin.showAtLocation(view,
                Gravity.CENTER, 0, 0);
    }
}
