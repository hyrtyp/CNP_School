package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.SchoolSearchService;
import com.hyrt.cnp.base.account.utils.Log;

/**
 * Created by Zoe on 2014-04-01.
 */
public class SchoolSearchRequest extends BaseRequest{

    private String keytName, keytDistrict, keytProperty, keytScale;

    @Inject
    private SchoolSearchService mSchoolSearchService;

    public SchoolSearchRequest(Class clazz, Context context,
                               String keytName, String keytDistrict,
                               String keytProperty, String keytScale) {
        super(clazz, context);
        this.keytName = keytName;
        this.keytDistrict = keytDistrict;
        this.keytProperty = keytProperty;
        this.keytScale = keytScale;
    }

    @Override
    public Base run() {
        return mSchoolSearchService.getSchoolSearchData(
                getRestTemplate(), keytName,
                keytDistrict, keytProperty, keytScale);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "SchoolSearch";
    }
}
