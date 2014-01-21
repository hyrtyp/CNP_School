package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.account.model.Base;
import com.hyrt.cnp.account.request.BaseRequest;
import com.hyrt.cnp.account.service.SchoolNoticeService;

/**
 * Created by GYH on 14-1-9.
 */
public class SchoolNoticeListRequest extends BaseRequest {

    private String data;

    @Inject
    private SchoolNoticeService schoolNoticeService;
    public SchoolNoticeListRequest(Class clazz, Context context,String data) {
        super(clazz, context);
        this.data=data;
    }

    @Override
    public Base run() {
        if(data.equals("school")){
            return schoolNoticeService.getNoticelistData(getRestTemplate());
        }else{
            return schoolNoticeService.getClassroomNoticelistData(getRestTemplate());
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    public String getcachekey(){
        return "Noticelist"+data;
    }
}
