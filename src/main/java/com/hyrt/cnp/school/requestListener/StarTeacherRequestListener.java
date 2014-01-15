package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.Teacher;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.ui.StarTeacherActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

import roboguice.RoboGuice;

/**
 * Created by GYH on 14-1-14.
 */
public class StarTeacherRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public StarTeacherRequestListener(Activity context) {
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
        StarTeacherActivity activity = (StarTeacherActivity)context.get();
        Teacher.Model result= (Teacher.Model)data;
        activity.initData(result);
    }

    @Override
    public StarTeacherRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
