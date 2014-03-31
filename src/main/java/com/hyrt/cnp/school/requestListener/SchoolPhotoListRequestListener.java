package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Photo;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.ui.SchoolPhotoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-14.
 */
public class SchoolPhotoListRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    private int id;
    public SchoolPhotoListRequestListener(Activity context,int id) {
        super(context);
        this.id=id;
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        showMessage(R.string.nodata_title,R.string.nodata_content);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data != null){
            SchoolPhotoActivity activity = (SchoolPhotoActivity)context.get();
            Photo.Model result= (Photo.Model)data;
            switch (id){
                case 0:
                    activity.pages.get(0).initData(result);
                    break;
                case 1:
                    activity.pages.get(1).initData(result);
                    break;
                case 2:
                    activity.pages.get(2).initData(result);
                    break;
            }
        }else{
            showMessage(R.string.nodata_title,R.string.nodata_content);
        }
    }

    @Override
    public SchoolPhotoListRequestListener start() {
        showIndeterminate(R.string.photo_pg);
        return this;
    }
}
