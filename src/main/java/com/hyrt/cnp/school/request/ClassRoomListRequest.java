package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.account.model.Base;
import com.hyrt.cnp.account.request.BaseRequest;
import com.hyrt.cnp.account.service.ClassRoomService;

/**
 * Created by GYH on 14-1-3.
 */
public class ClassRoomListRequest extends BaseRequest{

    @Inject
    private ClassRoomService schoolListService;


    public ClassRoomListRequest(Class clazz, Context context) {
        super(clazz, context);
    }
    @Override
    public Base run() {
        return schoolListService.getClassRoomListData(getRestTemplate());
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
    public String getcachekey(){
        return "ClassRoomList";
    }
}
