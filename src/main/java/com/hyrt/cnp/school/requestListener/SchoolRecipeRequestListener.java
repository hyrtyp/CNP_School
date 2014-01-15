package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.Recipe;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.ui.SchoolRecipeActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

import roboguice.RoboGuice;

/**
 * Created by GYH on 14-1-14.
 */
public class SchoolRecipeRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public SchoolRecipeRequestListener(Activity context) {
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
        SchoolRecipeActivity activity = (SchoolRecipeActivity)context.get();
        Recipe.Model result= (Recipe.Model)data;
        activity.initData(result);
    }

    @Override
    public SchoolRecipeRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
