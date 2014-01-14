package com.hyrt.cnp.school.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.RepiceInfoAdapter;
import com.hyrt.cnp.school.api.BaseActivity;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-10.
 */

public class SchoolRepiceInfoActivity  extends BaseActivity implements ActionBar.TabListener {

    private String[] str= new String[]{"星期一","星期二","星期三","星期四","星期五"};

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repiceinfo);

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
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new Fragmentrecipe("2013-12-10 食谱");
                case 1:
                    return new Fragmentrecipe("2013-12-11 食谱");
                case 2:
                    return new Fragmentrecipe("2013-12-12 食谱");
                case 3:
                    return new Fragmentrecipe("2013-12-13 食谱");
                default:
                    return new Fragmentrecipe("2013-12-14 食谱");
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }


    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy
     text.
     */
    public static class Fragmentrecipe extends Fragment {


        String foottime;
        ArrayList<String> foot=new ArrayList<String>();
        String[] footstime=new String[] {"早餐","早餐配料","加餐","午餐","午餐配料","午点","晚餐","晚餐配料","日营养量","负责人"};
        public Fragmentrecipe(String foottime){
            this.foottime=foottime;
            foot.add("蜂蜜蛋糕、豆奶");
            foot.add("蜂蜜、面粉、豆奶");
            foot.add("三元酸奶、玻璃海苔");
            foot.add("米饭、红烧肉、番茄炒鸡蛋、菠菜鸡蛋汤");
            foot.add("大米、排骨、番茄、鸡蛋、菠菜");
            foot.add("苹果");
            foot.add("开花奶馒头、酱香茄子");
            foot.add("面粉、茄子、杂豆");
            foot.add("650");
            foot.add("陈晴");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.layout_repiceinfo, container,
                    false);
            TextView textView=(TextView)rootView.findViewById(R.id.foottimetext);
            textView.setText(foottime);
            ListView listView = (ListView)rootView.findViewById
                    (R.id.schoolnotice_listview);
            RepiceInfoAdapter repiceInfoAdapter = new RepiceInfoAdapter(this.getActivity
                    (),footstime,foot);
            listView.setAdapter(repiceInfoAdapter);
            return rootView;
        }
    }
}
