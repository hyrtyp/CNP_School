package com.hyrt.cnp.school.ui;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.GridviewImageAdapter;
import com.hyrt.cnp.school.api.BaseActivity;

/**
 * Created by GYH on 14-1-10.
 */

public class SchoolPhotoActivity extends BaseActivity implements ActionBar.TabListener {

    private String[] str= new String[]{"园所风采","活动剪影","宝宝作品"};

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolphoto);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        actionBar.setHomeButtonEnabled(false);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < str.length; i++) {
            ActionBar.Tab tab=actionBar.newTab();
            tab.setText(str[i]);
            tab.setTabListener(this);
            actionBar.addTab(tab);
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }


    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        public String[] textphoto={"校内活动","示范园","校内活动","示范园"};
        public int[] imageResId1 = new int[] { R.drawable.schoolphoto1,R.drawable.schoolphoto2
                ,R.drawable.schoolphoto3,R.drawable.schoolphoto4};
        public int[] imageResId2 = new int[] { R.drawable.schoolphoto2,R.drawable.schoolphoto3
                ,R.drawable.schoolphoto3,R.drawable.schoolphoto4};
        public int[] imageResId3 = new int[] { R.drawable.schoolphoto1,R.drawable.schoolphoto2
                ,R.drawable.schoolphoto1,R.drawable.schoolphoto2};
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new Fragmentphoto(textphoto,imageResId1);
                case 1:
                    return new Fragmentphoto(textphoto,imageResId2);
                default:
                    return new Fragmentphoto(textphoto,imageResId3);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }


    public  class Fragmentphoto extends Fragment {
        private GridView gridview;
        private int[] imageResId;
        private String[] text;
        private Context context;
        public Fragmentphoto (String [] text,int[] imageResId ){
            this.text=text;
            this.imageResId=imageResId;
            this.context=getActivity();
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.layout_fragment_schoolphoto, container, false);
            gridview = (GridView)rootView.findViewById(R.id.cnp_gridview);
            imageResId = new int[] { R.drawable.schoolphoto1,R.drawable.schoolphoto2
                    ,R.drawable.schoolphoto3,R.drawable.schoolphoto4};
            GridviewImageAdapter starTeacherAdapter=new GridviewImageAdapter(text,imageResId,this.getActivity());
            gridview.setAdapter(starTeacherAdapter);
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   ShowPop(gridview);
                }
            });
            return rootView;
        }
    }
    private  PopupWindow popWin;
    public  void ShowPop(View view) {
        View popView = this.getLayoutInflater().inflate(
                R.layout.layout_popwindwos, null);
        popWin = new PopupWindow(popView, ViewPager.LayoutParams.FILL_PARENT,
                ViewPager.LayoutParams.FILL_PARENT);
        popView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWin.dismiss();
            }
        });
        // 需要设置一下此参数，点击外边可消失
        popWin.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        popWin.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popWin.setFocusable(true);
        popWin.showAtLocation(view,
                Gravity.CENTER, 0, 0);
    }
}
