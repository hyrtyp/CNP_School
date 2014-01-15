package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.School;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.ui.SchoolInfoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

import roboguice.RoboGuice;

/**
 * Created by GYH on 14-1-14.
 */
public class SchoolinfoRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public SchoolinfoRequestListener(Activity context) {
        super(context);
        RoboGuice.getInjector(context).injectMembers(this);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        SchoolInfoActivity activity = (SchoolInfoActivity)context.get();
        School.Model2 result= (School.Model2)data;
        activity.initData(result);
    }

    @Override
    public SchoolinfoRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
