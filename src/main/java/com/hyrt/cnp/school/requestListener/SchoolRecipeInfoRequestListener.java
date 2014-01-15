package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.RecipeInfo;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.ui.SchoolRepiceInfoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

import roboguice.RoboGuice;

/**
 * Created by GYH on 14-1-14.
 */
public class SchoolRecipeInfoRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public SchoolRecipeInfoRequestListener(Activity context) {
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
        SchoolRepiceInfoActivity activity = (SchoolRepiceInfoActivity)context.get();
        RecipeInfo.Model2 result= (RecipeInfo.Model2)data;
        activity.initData(result);
    }

    @Override
    public SchoolRecipeInfoRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
