package com.hyrt.cnp.school.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.SchoolSearch;
import com.hyrt.cnp.base.view.XListView;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.SchoolSearchResultAdapter;
import com.hyrt.cnp.school.adapter.SchoolSearchSpinnerAdapter;
import com.hyrt.cnp.school.request.SchoolSearchRequest;
import com.hyrt.cnp.school.requestListener.SchoolSearchRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoe on 2014-04-01.
 */
public class SchoolSearchResultActivity extends BaseActivity {

    //ui
    private LinearLayout layoutDistrict, layoutProperty, layoutStaffNum;
    private TextView tvPosition, tvDistrict, tvProperty, tvStaffNum, searchResultPrompt;
    private View mDistrictView, mPropertyView, mStaffNumView;
    private ListView mDistrictListView, mPropertyListView, mStaffNumListView;
    private XListView xlvSearchResult;

    private PopupWindow mDistrictPopupWindow, mPropertyPopupWindow, mStaffNumPopupWindow;

    private SchoolSearchSpinnerAdapter mDistrictAdapter, mPropertyAdapter, mStaffNumAdapter;
    private SchoolSearchResultAdapter mSearchResultAdapter;

    private String position;

    private List<SchoolSearch> mDatas = new ArrayList<SchoolSearch>();

    private int mRefreshState = -1;//加载状态
    private static final int ON_REFRESH_DATA = 0;//刷新数据
    private static final int ON_LOAD_MORE = 1;//加载更多

    private String mkeytName = "";
    private String mKeytDistrict = "";
    private String mKeytProperty = "";
    private String mKeytStaffNum = "";

    private double lng = 0;//纬度
    private double lat = 0;//经度
    private double tempLng = 0;
    private double tempLat = 0;
    private String provinceId;

    private static final String TAG = "SchoolSearchResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_search_result);
        findView();
        setListener();
        Intent intent = getIntent();
        mkeytName = intent.getStringExtra("name");
        if(mkeytName == null){
            mkeytName = "";
        }

        xlvSearchResult.setPullLoadEnable(true);
        xlvSearchResult.setPullRefreshEnable(true);
        mSearchResultAdapter = new SchoolSearchResultAdapter(this, mDatas);
        xlvSearchResult.setAdapter(mSearchResultAdapter);
        loadPosition();
        initImageLoader();
        loadData();
    }


    public void loadPosition(){

       LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getAllProviders();
        android.util.Log.i(TAG, "providers:"+ providers.size());
        if (providers.size() > 0) {
            Location mLocation = lm.getLastKnownLocation(providers.get(0));
            android.util.Log.i(TAG, "mLocation:"+ mLocation);
            if(mLocation != null){
                lng = mLocation.getLongitude();
                lat = mLocation.getLatitude();
                android.util.Log.i(TAG, "getLatitude:"+ mLocation.getLatitude()+" getLongitude:"+mLocation.getLongitude());
            }
        }

        /*PositionRequestListener mPositionRequestListener = new PositionRequestListener(this);
        mPositionRequestListener.setListener(mPositionListener);
        PositionInfoRequest mPositionRequest = new PositionInfoRequest(PositionInfo.Model.class, this, lat, lng );
        spiceManager.execute(
                mPositionRequest, mPositionRequest.getcachekey(),
                DurationInMillis.ONE_SECOND * 10,
                mPositionRequestListener.start());*/

        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://api.map.baidu.com/geocoder/v2/?" +
                        "ak=3KTS4mpYnE8ZSGr4v1fEFmPO&location="+lat+","+lng+"&output=json";
                URL url = null;
                try{
                    url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(2000);
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
                        // 获取返回的数据
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String lines;
                        String result = "";
                        while ((lines = br.readLine()) != null) {

                            result += lines;
                        }
                        android.util.Log.i("tag", "result:"+result);
                        JSONObject mJson = new JSONObject(result);
                        JSONObject resultJson = mJson.getJSONObject("result");
                        JSONObject addressComponentJson = resultJson.getJSONObject("addressComponent");
                        final String province = addressComponentJson.getString("province");
                        android.util.Log.i("tag", "province:"+province);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                position = province;
                                tvPosition.setText(province);
                            }
                        });
                    } else {
                        Log.i("tag", "Get方式请求失败");
                    }
                }catch (MalformedURLException e){

                }catch (IOException e) {

                }catch (JSONException e) {

                }
            }
        });
        mThread.start();
    }

    /*private PositionRequestListener.RequestListener mPositionListener
            = new PositionRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(PositionInfo data) {
            Toast.makeText(SchoolSearchResultActivity.this, data+"", 1).show();
            if(data != null){
                String city = (data.getAddressComponent()).getCity();
                Toast.makeText(SchoolSearchResultActivity.this, city, 1).show();
            }
        }

        @Override
        public void onRequestFailure(SpiceException e) {

        }
    };*/

    public void initImageLoader(){
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }

    @Override
    protected void initTitleview() {
        actionBar.hide();
    }

    /**
     * 加载数据
     */
    private void loadData(){


        SchoolSearchRequestListener mRequestListener = new SchoolSearchRequestListener(this);
        mRequestListener.setListener(mOnRequestListener);
        SchoolSearchRequest mRequest = new SchoolSearchRequest(
                SchoolSearch.Model.class, this,
                mkeytName, mKeytDistrict, mKeytProperty, mKeytStaffNum, lng, lat, provinceId);
        spiceManager.execute(
                mRequest, mRequest.getcachekey(),
                DurationInMillis.ONE_SECOND * 10,
                mRequestListener.start());
    }



    /**
     * 加载数据监听
     */
    private SchoolSearchRequestListener.RequestListener mOnRequestListener
            = new SchoolSearchRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(List<SchoolSearch> datas) {
            if(mRefreshState != ON_LOAD_MORE){
                mDatas.clear();
                xlvSearchResult.setSelection(0);
            }
            if(datas != null){
                mDatas.addAll(datas);
            }
            if (mDatas.size() <= 0) {
               xlvSearchResult.setVisibility(View.GONE);
               searchResultPrompt.setVisibility(View.VISIBLE);
            }else{
                xlvSearchResult.setVisibility(View.VISIBLE);
                searchResultPrompt.setVisibility(View.GONE);
            }
            if(mSearchResultAdapter != null){
                mSearchResultAdapter.notifyDataSetChanged();
            }
            mRefreshState = -1;
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            xlvSearchResult.setVisibility(View.GONE);
            searchResultPrompt.setVisibility(View.VISIBLE);
        }
    };

    /**
     * 显示区域pop
     */
    private void showDistrictPop(){
        int spinnerCenterHeight = 0;
        mDistrictView = LayoutInflater.from(this).inflate(R.layout.school_search_spinner, null);
        if(mDistrictListView == null){
            mDistrictListView = (ListView) mDistrictView.findViewById(R.id.lv_school_search_spinner);
            ImageView ivSpinnerCenter = (ImageView) mDistrictView.findViewById(R.id.iv_school_search_spinner_center);
            mDistrictListView.setDividerHeight(0);
            String[] array = new String[]{"东城区", "西城区", "宣武区", "崇文区","海淀区", "朝阳区", "丰台区", "石景山区"};

            if (array.length > 1) {
               LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) ivSpinnerCenter.getLayoutParams();
                if (array.length <= 8) {
                    spinnerCenterHeight = getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height)*(array.length-1);
                } else {
                    spinnerCenterHeight = getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height)*7;
                }

                mParams.height = spinnerCenterHeight;
            }
            mDistrictAdapter = new SchoolSearchSpinnerAdapter(array, this);
            mDistrictAdapter.setListener(mDistrictOnItemClickListener);
            mDistrictListView.setAdapter(mDistrictAdapter);
            RelativeLayout.LayoutParams mDistrictListViewParams = (RelativeLayout.LayoutParams) mDistrictListView.getLayoutParams();
            mDistrictListViewParams.height = spinnerCenterHeight + getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height);
        }
        if(mDistrictPopupWindow == null){
            int popHeight = spinnerCenterHeight + getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height) +
                    getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_bottom_height);
            mDistrictPopupWindow = new PopupWindow(mDistrictView,
                    getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_width),
                    popHeight);
        }
        if(mDistrictPopupWindow.isShowing()){
            mDistrictPopupWindow.dismiss();
        }else{
            mDistrictPopupWindow.showAsDropDown(
                    layoutDistrict, 0,
                    getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_offsetY));
        }
    }

    /**
     * 显示性质pop
     */
    private void showPropertyPop(){
        int spinnerCenterHeight = 0;
        if(mPropertyListView == null){
            mPropertyView = LayoutInflater.from(this).inflate(R.layout.school_search_spinner, null);
            mPropertyListView = (ListView) mPropertyView.findViewById(R.id.lv_school_search_spinner);
            ImageView ivSpinnerCenter = (ImageView) mPropertyView.findViewById(R.id.iv_school_search_spinner_center);
            mPropertyListView.setDividerHeight(0);
            String[] array = new String[]{"公办", "民办"};

            if (array.length > 1) {
                LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) ivSpinnerCenter.getLayoutParams();
                if (array.length <= 8) {
                    spinnerCenterHeight = getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height)*(array.length-1);
                } else {
                    spinnerCenterHeight = getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height)*7;
                }

                mParams.height = spinnerCenterHeight;
            }
            mPropertyAdapter = new SchoolSearchSpinnerAdapter(array, this);
            mPropertyAdapter.setListener(mPropertyOnItemClickListener);
            mPropertyListView.setAdapter(mPropertyAdapter);
            RelativeLayout.LayoutParams mPropertyListViewParams = (RelativeLayout.LayoutParams) mPropertyListView.getLayoutParams();
            mPropertyListViewParams.height = spinnerCenterHeight + getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height);
        }
        if(mPropertyPopupWindow == null){
            int popHeight = spinnerCenterHeight + getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height) +
                    getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_bottom_height);
            mPropertyPopupWindow = new PopupWindow(mPropertyView,
                    getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_width),
                    popHeight);
        }
        if(mPropertyPopupWindow.isShowing()){
            mPropertyPopupWindow.dismiss();
        }else{
            mPropertyPopupWindow.showAsDropDown(
                    layoutProperty, 0,
                    getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_offsetY));
        }
    }

    /**
     * 显示办园规模pop
     */
    private void showStaffNumPop(){
        int spinnerCenterHeight = 0;
        if(mStaffNumListView == null){
            mStaffNumView = LayoutInflater.from(this).inflate(R.layout.school_search_spinner, null);
            mStaffNumListView = (ListView) mStaffNumView.findViewById(R.id.lv_school_search_spinner);
            ImageView ivSpinnerCenter = (ImageView) mStaffNumView.findViewById(R.id.iv_school_search_spinner_center);
            mStaffNumListView.setDividerHeight(0);
            String[] array = new String[]{"0-50人", "50-100人", "100-200人", "200-300人", "300-500人", "500人以上"};

            if (array.length > 1) {
                LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) ivSpinnerCenter.getLayoutParams();
                if (array.length <= 8) {
                    spinnerCenterHeight = getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height)*(array.length-1);
                } else {
                    spinnerCenterHeight = getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height)*7;
                }

                mParams.height = spinnerCenterHeight;
            }
            mStaffNumAdapter = new SchoolSearchSpinnerAdapter(array, this);
            mStaffNumAdapter.setListener(mStaffNumOnItemClickListener);
            mStaffNumListView.setAdapter(mStaffNumAdapter);
            RelativeLayout.LayoutParams mStaffNumListViewParams = (RelativeLayout.LayoutParams) mStaffNumListView.getLayoutParams();
            mStaffNumListViewParams.height = spinnerCenterHeight + getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height);
        }
        if(mStaffNumPopupWindow == null){
            int popHeight = spinnerCenterHeight + getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_height) +
                    getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_bottom_height);
            mStaffNumPopupWindow = new PopupWindow(mStaffNumView,
                    getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_width),
                    popHeight);
        }
        if(mStaffNumPopupWindow.isShowing()){
            mStaffNumPopupWindow.dismiss();
        }else{
            mStaffNumPopupWindow.showAsDropDown(
                    layoutStaffNum, 0,
                    getResources().getDimensionPixelOffset(R.dimen.search_result_spinner_offsetY));
        }
    }

    SchoolSearchSpinnerAdapter.OnItemClickListener mDistrictOnItemClickListener = new SchoolSearchSpinnerAdapter.OnItemClickListener() {
        @Override
        public void onClick(String text, int position) {
            if(mDistrictPopupWindow != null){
                mDistrictPopupWindow.dismiss();
            }
            mKeytDistrict = text;
            tvDistrict.setText(text);
            if (mKeytDistrict.length() > 0) {
                mRefreshState = ON_REFRESH_DATA;
                loadData();
            }
        }
    };

    SchoolSearchSpinnerAdapter.OnItemClickListener mPropertyOnItemClickListener = new SchoolSearchSpinnerAdapter.OnItemClickListener() {
        @Override
        public void onClick(String text, int position) {
            if(mPropertyPopupWindow != null){
                mPropertyPopupWindow.dismiss();
            }
            if("公办".equals(text)){
                mKeytProperty = "1";
            }else if("民办".equals(text)){
                mKeytProperty = "2";
            }else{
                mKeytProperty = "";
            }
            tvProperty.setText(text);
            if (mKeytProperty.length() > 0) {
                mRefreshState = ON_REFRESH_DATA;
                loadData();
            }
        }
    };

    SchoolSearchSpinnerAdapter.OnItemClickListener mStaffNumOnItemClickListener = new SchoolSearchSpinnerAdapter.OnItemClickListener() {
        @Override
        public void onClick(String text, int position) {
            if(mStaffNumPopupWindow != null){
                mStaffNumPopupWindow.dismiss();
            }
            switch (position){
                case 0:
                    mKeytStaffNum = "0-50";
                    break;
                case 1:
                    mKeytStaffNum = "50-100";
                    break;
                case 2:
                    mKeytStaffNum = "100-200";
                    break;
                case 3:
                    mKeytStaffNum = "200-300";
                    break;
                case 4:
                    mKeytStaffNum = "300-500";
                    break;
                case 5:
                    mKeytStaffNum = "500-0";
                    break;
                default:
                    mKeytStaffNum = "";
                    break;
            }
            tvStaffNum.setText(text);
            if (mKeytStaffNum.length() > 0) {
                mRefreshState = ON_REFRESH_DATA;
                loadData();
            }
        }
    };

    /**
     * 设置监听
     */
    private void setListener(){
        layoutDistrict.setOnClickListener(mOnClickListener);
        layoutProperty.setOnClickListener(mOnClickListener);
        layoutStaffNum.setOnClickListener(mOnClickListener);
        tvPosition.setOnClickListener(mOnClickListener);
        xlvSearchResult.setXListViewListener(mSearchReusltListener);
        xlvSearchResult.setOnItemClickListener(mXlvSearchResultOnItemClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_search_result_position) {
                Intent intent = new Intent();
                intent.setClass(SchoolSearchResultActivity.this, SchoolCityListActivity.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 1);
            } else if (view.getId() == R.id.layout_school_search_result_district) {
                showDistrictPop();
            } else if (view.getId() == R.id.layout_school_search_result_property) {
                showPropertyPop();
            } else if (view.getId() == R.id.layout_school_search_result_staff_num) {
                showStaffNumPop();
            }
        }
    };

    XListView.IXListViewListener mSearchReusltListener = new XListView.IXListViewListener() {
        @Override
        public void onRefresh() {
            if (mRefreshState == ON_REFRESH_DATA || mRefreshState == ON_LOAD_MORE) {
                Toast.makeText(SchoolSearchResultActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
            } else {
                mRefreshState = ON_REFRESH_DATA;
                loadData();
            }
            xlvSearchResult.stopRefresh();
        }

        @Override
        public void onLoadMore() {
           /* if (mRefreshState == ON_REFRESH_DATA || mRefreshState == ON_LOAD_MORE) {
                Toast.makeText(SchoolSearchResultActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
            } else {
                mRefreshState = ON_LOAD_MORE;
                loadData();
            }*/
            xlvSearchResult.stopLoadMore();
        }
    };

    private AdapterView.OnItemClickListener mXlvSearchResultOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(mDistrictPopupWindow != null && mDistrictPopupWindow.isShowing()){
                mDistrictPopupWindow.dismiss();
            }
            if(mPropertyPopupWindow != null && mPropertyPopupWindow.isShowing()){
                mPropertyPopupWindow.dismiss();
            }
            if(mStaffNumPopupWindow != null && mStaffNumPopupWindow.isShowing()){
                mStaffNumPopupWindow.dismiss();
            }
            int sid = mDatas.get(i-1).getNursery_id();
            Intent intent = new Intent();
            intent.setClass(SchoolSearchResultActivity.this, SchoolIndexActivity.class);
            intent.putExtra("mSid", sid);
            startActivity(intent);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            String provinceName = data.getStringExtra("provinceName");
            provinceId = data.getStringExtra("provinceId");
            if(lng != 0){
                tempLng = lng;
                lng = 0;
            }
            if(lat != 0){
                tempLat = lat;
                lat = 0;
            }
            if(provinceId == null){
                lat = tempLat;
                lng = tempLng;
            }
            tvPosition.setText(provinceName);
            loadData();
        }
    }

    /**
     * 遍历ui
     */
    private void findView() {
        tvPosition = (TextView) findViewById(R.id.tv_search_result_position);
        layoutDistrict = (LinearLayout) findViewById(R.id.layout_school_search_result_district);
        layoutProperty = (LinearLayout) findViewById(R.id.layout_school_search_result_property);
        layoutStaffNum = (LinearLayout) findViewById(R.id.layout_school_search_result_staff_num);
        tvDistrict = (TextView) findViewById(R.id.tv_school_search_result_district);
        tvProperty = (TextView) findViewById(R.id.tv_school_search_result_property);
        tvStaffNum = (TextView) findViewById(R.id.tv_school_search_result_staff_num);
        xlvSearchResult = (XListView) findViewById(R.id.xlv_school_search_result_content);
        searchResultPrompt = (TextView) findViewById(R.id.tv_school_search_result_prompt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoader.getInstance().getDiscCache().clear();

    }
}
