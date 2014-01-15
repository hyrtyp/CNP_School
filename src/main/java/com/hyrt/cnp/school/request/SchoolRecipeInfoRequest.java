package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.account.model.Base;
import com.hyrt.cnp.account.request.BaseRequest;
import com.hyrt.cnp.account.service.RecipeInfoService;

/**
 * Created by GYH on 14-1-3.
 */
public class SchoolRecipeInfoRequest extends BaseRequest{

    @Inject
    private RecipeInfoService schoolListService;


    public SchoolRecipeInfoRequest(Class clazz, Context context) {
        super(clazz, context);
    }
    @Override
    public Base run() {
        return schoolListService.getRecipeWeekData(getRestTemplate());
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "RecipeWeek";
    }
}
