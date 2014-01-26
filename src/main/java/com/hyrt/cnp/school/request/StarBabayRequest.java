package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.account.model.Base;
import com.hyrt.cnp.account.request.BaseRequest;
import com.hyrt.cnp.account.service.StarBabayService;

/**
 * Created by GYH on 14-1-14.
 * 明星宝宝
 */
public class StarBabayRequest extends BaseRequest {

    @Inject
    private StarBabayService teacherService;

    public StarBabayRequest(Class clazz, Context context) {
        super(clazz, context);
    }

    @Override
    public Base run() {
        return teacherService.getStarbabayData(getRestTemplate());
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    public String getcachekey(){
        return "Starbabay";
    }
}
