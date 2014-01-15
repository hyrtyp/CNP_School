package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.account.model.Base;
import com.hyrt.cnp.account.request.BaseRequest;
import com.hyrt.cnp.account.service.TeacherService;

/**
 * Created by GYH on 14-1-14.
 */
public class StarTeacherRequest extends BaseRequest {

    @Inject
    private TeacherService teacherService;

    public StarTeacherRequest(Class clazz, Context context) {
        super(clazz, context);
    }

    @Override
    public Base run() {
        return teacherService.getStarteacherData(getRestTemplate());
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    public String getcachekey(){
        return "Starteacher";
    }
}
