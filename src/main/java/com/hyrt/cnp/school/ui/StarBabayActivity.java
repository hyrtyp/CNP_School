package com.hyrt.cnp.school.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hyrt.cnp.account.model.StarBabay;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.StarBabayImageAdapter;
import com.hyrt.cnp.school.request.StarBabayRequest;
import com.hyrt.cnp.school.requestListener.StarBabayRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-10.
 */
public class StarBabayActivity extends BaseActivity {

    private GridView gridview;
    private StarBabayImageAdapter starTeacherImageAdapter;

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
                showPop(gridview,((StarBabay)starTeacherImageAdapter.getItem(i)).getImagepath());
            }
        });
    }

    public void initData(StarBabay.Model model){
        String[] resKeys=new String[]{"getImagepath","getRenname"};
        int[] reses=new int[]{R.id.gridview_image,R.id.gridview_name};
       starTeacherImageAdapter = new StarBabayImageAdapter
                (this,model.getData(),R.layout.layout_item_gridview_image,resKeys,reses);
        gridview.setAdapter(starTeacherImageAdapter);
    }

    private void loadStarteacehr(){
        StarBabayRequestListener sendwordRequestListener = new StarBabayRequestListener(this);
        StarBabayRequest starBabayRequest=new StarBabayRequest(StarBabay.Model.class, this);
        spiceManager.execute(starBabayRequest,starBabayRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }


}
