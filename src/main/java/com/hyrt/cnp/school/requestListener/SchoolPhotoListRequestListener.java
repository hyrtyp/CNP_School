package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.Photo;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.ui.SchoolPhotoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

import roboguice.RoboGuice;

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
        RoboGuice.getInjector(context).injectMembers(this);
        this.id=id;
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
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
    }

    @Override
    public SchoolPhotoListRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
