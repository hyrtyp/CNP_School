package com.hyrt.cnp.school.api;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Key;
import com.hyrt.cnp.school.R;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.spicelist.BitmapSpiceManager;

import java.util.HashMap;
import java.util.Map;

import roboguice.RoboGuice;
import roboguice.util.RoboContext;

/**
 * Created by GYH on 14-1-3.
 */
public class BaseActivity extends ActionBarActivity implements RoboContext {

    protected HashMap<Key<?>,Object> scopedObjects = new HashMap<Key<?>, Object>();
    private SpiceManager contentManager = new SpiceManager( JacksonSpringAndroidSpiceService.class );
    private BitmapSpiceManager spiceManagerBinary = new BitmapSpiceManager();
    protected View viewTitleBar;
    protected ActionBar actionBar;
    protected ImageView backimage;
    protected TextView titletext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoboGuice.getInjector(this).injectMembers(this);
        actionBar  = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        initTitleview();
        getSupportActionBar().setDisplayShowCustomEnabled(true);
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

    @Override
    public Map<Key<?>, Object> getScopedObjectMap() {
        return scopedObjects;
    }

    /*
    * 顶部实现布局
    * */
    protected void initTitleview(){
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        viewTitleBar = getLayoutInflater().inflate(R.layout.layout_actionbar_title, null);
        backimage=(ImageView)viewTitleBar.findViewById(R.id.action_bar_title_back);
        titletext=(TextView)viewTitleBar.findViewById(R.id.action_bar_title_text);
        actionBar.setCustomView(viewTitleBar, lp);
        backimage.setVisibility(View.GONE);
        actionBar.setIcon(R.drawable.actionbar_title_back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("abc")
                .setIcon(R.drawable.actionbar_right)
                .setShowAsAction(
                        MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }
}
