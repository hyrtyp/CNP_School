package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.SendwordService;

/**
 * Created by GYH on 14-1-8.
 */
public class SendwordRequest extends BaseRequest {

    @Inject
    private SendwordService sendwordService;

    public SendwordRequest(Class clazz, Context context) {
        super(clazz, context);
    }

    @Override
    public Base run() {
        return sendwordService.getSendwordData(getRestTemplate());
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "Sendword";
    }
}
