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

    @Inject
    private SchoolNoticeService schoolNoticeService;
    public SchoolNoticeListRequest(Class clazz, Context context) {
        super(clazz, context);
    }

    @Override
    public Base run() {
        return schoolNoticeService.getModelData(getRestTemplate());
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
