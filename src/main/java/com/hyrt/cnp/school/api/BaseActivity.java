package com.hyrt.cnp.school.api;

import android.os.Bundle;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.spicelist.BitmapSpiceManager;

import roboguice.activity.RoboActivity;

/**
 * Created by GYH on 14-1-3.
 */
public class BaseActivity extends RoboActivity {

    private SpiceManager contentManager = new SpiceManager( JacksonSpringAndroidSpiceService.class );
    private BitmapSpiceManager spiceManagerBinary = new BitmapSpiceManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        contentManager.start(this);
        spiceManagerBinary.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        contentManager.shouldStop();
        spiceManagerBinary.shouldStop();
        super.onStop();
    }
    public SpiceManager getSpiceManager(){
        return contentManager;
    }
    public BitmapSpiceManager getSpiceManagerBinary(){
        return spiceManagerBinary;
    }
}
