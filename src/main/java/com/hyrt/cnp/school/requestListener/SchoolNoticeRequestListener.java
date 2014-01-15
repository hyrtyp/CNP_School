package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.Notice;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.ui.SchoolNoticeActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

import roboguice.RoboGuice;

/**
 * Created by GYH on 14-1-14.
 */
public class SchoolNoticeRequestListener extends BaseRequestListener{
    /**
     * @param context
     */
    public SchoolNoticeRequestListener(Activity context) {
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
        SchoolNoticeActivity activity = (SchoolNoticeActivity)context.get();
        Notice.Model result= (Notice.Model)data;
        activity.initData(result);
    }

    @Override
    public SchoolNoticeRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
