package com.hyrt.cnp.school.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.account.model.SendWord;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.api.BaseActivity;
import com.hyrt.cnp.school.request.SendwordRequest;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-8.
 */
public class SendwordActivity extends BaseActivity{


    private TextView sendtext1;
    private TextView sendtext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendword);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //loadSendword();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        TextView textView = (TextView) viewTitleBar.findViewById(R.id.action_bar_title_text);
        textView.setText("园长寄语");
    }

    private void initView(){
        sendtext1=(TextView)findViewById(R.id.sendword_text_title);
        sendtext2=(TextView)findViewById(R.id.sendword_text_intro);
    }

    private void loadSendword(){
//        setProgressBarIndeterminateVisibility(true);
        SendwordRequestListener sendwordRequestListener = new SendwordRequestListener(this);
        getSpiceManager().execute(new SendwordRequest(SendWord.Model.class,this), "github", DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    class SendwordRequestListener extends BaseRequestListener {

        protected SendwordRequestListener(Activity context) {
            super(context);
        }

        @Override
        public BaseRequestListener start() {
            showIndeterminate("加载...");
            return this;
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            super.onRequestFailure(e);
        }

        @Override
        public void onRequestSuccess(Object data) {
            super.onRequestSuccess(data);
            SendWord.Model result= (SendWord.Model)data;
            if(result==null){
                Toast.makeText(SendwordActivity.this, "is null", Toast.LENGTH_SHORT).show();
            }else {
                sendtext1.setText(result.getData().getnName()+
                        "\n"+result.getData().getRenname()+
                        "\n"+result.getData().getIntro());
                sendtext2.setText(result.getData().getMessage());
            }
        }
    }
}
