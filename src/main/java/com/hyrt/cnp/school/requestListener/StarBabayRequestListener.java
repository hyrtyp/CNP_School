package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.StarBabay;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.ui.StarBabayActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

import roboguice.RoboGuice;

/**
 * Created by GYH on 14-1-14.
 */
public class StarBabayRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public StarBabayRequestListener(Activity context) {
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
        StarBabayActivity activity = (StarBabayActivity)context.get();
        StarBabay.Model result= (StarBabay.Model)data;
        activity.initData(result);
    }

    @Override
    public StarBabayRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
