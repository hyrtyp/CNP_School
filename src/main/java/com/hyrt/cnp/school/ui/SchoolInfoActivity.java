package com.hyrt.cnp.school.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.account.model.School;
import com.hyrt.cnp.account.utils.UITextUtils;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.api.BaseActivity;
import com.hyrt.cnp.school.request.SchoolinfoRequest;
import com.hyrt.cnp.school.requestListener.SchoolinfoRequestListener;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-13.
 */
public class SchoolInfoActivity extends BaseActivity{

    private TextView schoolintro;
    private ImageView schoolmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolinfo);
        initView();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        TextView textView = (TextView) viewTitleBar.findViewById(R.id.action_bar_title_text);
        textView.setText("园所介绍");
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSendword();
    }

    private void initView(){
        schoolintro=(TextView)findViewById(R.id.schoolintro);
        schoolmap=(ImageView)findViewById(R.id.schoolmap);
    }

    public void initData(School.Model2 model2){
        UITextUtils.setTextWithSelection(schoolintro,model2.getData().getIntro());
    }

    private void loadSendword(){
        SchoolinfoRequestListener sendwordRequestListener = new SchoolinfoRequestListener(this);
        SchoolinfoRequest schoolinfoRequest= new SchoolinfoRequest(School.Model2.class,this);
        getSpiceManager().execute(schoolinfoRequest,schoolinfoRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
