package com.hyrt.cnp.school.requestListener;
import android.app.Activity;
import com.hyrt.cnp.account.model.Teacher;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.ui.StarTeacherActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-14.
 */
public class StarTeacherRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public StarTeacherRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        showMessage(R.string.nodata_title, R.string.nodata_content);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data != null){
            StarTeacherActivity activity = (StarTeacherActivity)context.get();
            Teacher.Model result= (Teacher.Model)data;
            activity.initData(result);
        }else{
            showMessage(R.string.nodata_title,R.string.nodata_content);
        }
    }

    @Override
    public StarTeacherRequestListener start() {
        showIndeterminate(R.string.starteacher_pg);
        return this;
    }
}
