package com.hyrt.cnp.school.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.account.model.Teacher;
import com.hyrt.cnp.account.utils.FaceUtils;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.request.StarTeacherInfoRequest;
import com.hyrt.cnp.school.requestListener.StarTeacherInfoRequestListener;
import com.jingdong.app.pad.product.drawable.HandlerRecycleBitmapDrawable;
import com.jingdong.app.pad.utils.InflateUtil;
import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.utils.cache.GlobalImageCache;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.lang.ref.WeakReference;

/**
 * Created by GYH on 14-1-10.
 */
public class StarTeacherInfoActivity extends BaseActivity {
    private TextView sendtexttitle;
    private TextView sendtextintr;
    private TextView sendtextname;
    private TextView sendtextpol;
    private TextView sendtextmsg;
    private WeakReference<ImageView> weakImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starteacherinfo);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSendword();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        TextView textView = (TextView) viewTitleBar.findViewById(R.id.action_bar_title_text);
        textView.setText("明星教师");
    }

    private void initView(){
        sendtexttitle =(TextView)findViewById(R.id.sendword_text_title);
        sendtextintr=(TextView)findViewById(R.id.sendword_text_intro);
        sendtextname=(TextView)findViewById(R.id.sendword_text_rename);
        sendtextpol=(TextView)findViewById(R.id.sendword_text_pol);
        sendtextmsg=(TextView)findViewById(R.id.sendword_text_msg);

    }

    public void initData(Teacher.Model2 model){
        if(!model.getData().getRenname().equals("")){
            sendtexttitle.setText(model.getData().getRenname());
        }
        if(!model.getData().getSex().equals("")){
            if(model.getData().getSex().equals("M")){
                sendtextname.setText("男");
            }else if (model.getData().getSex().equals("W")){
                sendtextname.setText("女");
            }

        }
        if(!model.getData().getRoomName().equals("")){
            sendtextpol.setText(model.getData().getRoomName());
        }
        if(!model.getData().getNurseryName().equals("")){
            sendtextintr.setText(model.getData().getNurseryName());
        }
        if(!model.getData().getIntro().equals("")){
            sendtextmsg.setText(model.getData().getIntro());
        }
        setImage(model.getData().getUser_id());

    }

    private void loadSendword(){
        StarTeacherInfoRequestListener sendwordRequestListener = new StarTeacherInfoRequestListener(this);
        StarTeacherInfoRequest starTeacherInfoRequest= new StarTeacherInfoRequest(Teacher.Model2.class,this);
        spiceManager.execute(starTeacherInfoRequest, starTeacherInfoRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    private void setImage(int user_id){
        String facePath = FaceUtils.getAvatar(user_id, FaceUtils.FACE_BIG);
        ImageView imageView = (ImageView)findViewById(R.id.starteacherimageview);
        weakImageView = new WeakReference<ImageView>(imageView);
        HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable = new HandlerRecycleBitmapDrawable(null, this);
        imageView.setImageDrawable(localHandlerRecycleBitmapDrawable);
        GlobalImageCache.BitmapDigest localBitmapDigest = new GlobalImageCache.BitmapDigest(facePath);
        localBitmapDigest.setWidth(imageView.getWidth());
        localBitmapDigest.setHeight(imageView.getHeight());
        Bitmap localBitmap = InflateUtil.loadImageWithCache(localBitmapDigest);
        if (localBitmap == null) {
            HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable2 = (HandlerRecycleBitmapDrawable) imageView.getDrawable();
            localHandlerRecycleBitmapDrawable2.setBitmap(null);
            localHandlerRecycleBitmapDrawable.invalidateSelf();
            InflateUtil.loadImageWithUrl(getHttpGroupaAsynPool(), localBitmapDigest,false, new InflateUtil.ImageLoadListener() {
                public void onError(GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest) {
                }

                public void onProgress(GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest, int paramAnonymousInt1, int paramAnonymousInt2) {
                }

                public void onStart(GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest) {
                }

                public void onSuccess(GlobalImageCache.BitmapDigest paramAnonymousBitmapDigest, Bitmap paramAnonymousBitmap) {
                    if (weakImageView != null) {
                        ImageView targetIv = weakImageView.get();
                        if (targetIv != null) {
                            HandlerRecycleBitmapDrawable localHandlerRecycleBitmapDrawable = (HandlerRecycleBitmapDrawable) targetIv.getDrawable();
                            localHandlerRecycleBitmapDrawable.setBitmap(paramAnonymousBitmap);
                            localHandlerRecycleBitmapDrawable.invalidateSelf();
                        }
                    }
                }
            });
        } else {
            localHandlerRecycleBitmapDrawable.setBitmap(localBitmap);
            localHandlerRecycleBitmapDrawable.invalidateSelf();
        }
    }
}
