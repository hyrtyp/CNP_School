package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.SendWord;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.ui.SendwordActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

import roboguice.RoboGuice;

/**
 * Created by GYH on 14-1-14.
 */
public class SendWordRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public SendWordRequestListener(Activity context) {
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
        SendwordActivity activity = (SendwordActivity)context.get();
        SendWord.Model result= (SendWord.Model)data;
        activity.initData(result);
    }

    @Override
    public SendWordRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}