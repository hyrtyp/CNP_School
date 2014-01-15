package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.Teacher;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.ui.StarTeacherInfoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

import roboguice.RoboGuice;

/**
 * Created by GYH on 14-1-14.
 */
public class StarTeacherInfoRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public StarTeacherInfoRequestListener(Activity context) {
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
        StarTeacherInfoActivity activity = (StarTeacherInfoActivity)context.get();
        Teacher.Model2 result= (Teacher.Model2)data;
        activity.initData(result);
    }

    @Override
    public StarTeacherInfoRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
