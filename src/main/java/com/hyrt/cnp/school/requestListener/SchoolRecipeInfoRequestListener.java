package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.RecipeInfo;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.ui.SchoolRepiceInfoActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-14.
 */
public class SchoolRecipeInfoRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public SchoolRecipeInfoRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        SchoolRepiceInfoActivity activity = (SchoolRepiceInfoActivity)context.get();
        activity.initData(null);
//        showMessage(R.string.nodata_title,R.string.nodata_content);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data != null){
            SchoolRepiceInfoActivity activity = (SchoolRepiceInfoActivity)context.get();
            RecipeInfo.Model2 result= (RecipeInfo.Model2)data;
            activity.initData(result);
        }else{
            SchoolRepiceInfoActivity activity = (SchoolRepiceInfoActivity)context.get();
            activity.initData(null);
//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }
    }

    @Override
    public SchoolRecipeInfoRequestListener start() {
        showIndeterminate(R.string.schoolrecipe_pg);
        return this;
    }
}
