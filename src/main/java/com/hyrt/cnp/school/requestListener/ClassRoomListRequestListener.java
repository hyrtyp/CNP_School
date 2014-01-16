package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.ClassRoom;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.ui.ClassRoomListActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

import roboguice.RoboGuice;

/**
 * Created by GYH on 14-1-14.
 */
public class ClassRoomListRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public ClassRoomListRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        showMessage(R.string.nodata_title,R.string.nodata_content);
        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        ClassRoomListActivity activity = (ClassRoomListActivity)context.get();
        ClassRoom.Model result= (ClassRoom.Model)data;
        activity.initData(result);
    }

    @Override
    public ClassRoomListRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
