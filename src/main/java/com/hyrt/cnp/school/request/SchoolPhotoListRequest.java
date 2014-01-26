package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.account.model.Base;
import com.hyrt.cnp.account.request.BaseRequest;
import com.hyrt.cnp.account.service.PhotoService;

/**
 * Created by GYH on 14-1-3.
 */
public class SchoolPhotoListRequest extends BaseRequest{

    @Inject
    private PhotoService schoolListService;

    private String pkind;

    public SchoolPhotoListRequest(Class clazz, Context context,String pkind) {
        super(clazz, context);
        this.pkind=pkind;
    }
    @Override
    public Base run() {
        return schoolListService.getphotolistData(getRestTemplate(),pkind);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "Schoolphotolist"+pkind;
    }
}
