package com.hyrt.cnp.school.fragement;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.hyrt.cnp.account.model.Photo;
import com.hyrt.cnp.school.R;
import com.hyrt.cnp.school.adapter.PhotoImageAdapter;
import com.hyrt.cnp.school.request.SchoolPhotoListRequest;
import com.hyrt.cnp.school.requestListener.SchoolPhotoListRequestListener;
import com.hyrt.cnp.school.ui.SchoolPhotoActivity;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-14.
 */
public  class Fragmentphoto extends Fragment {
    private GridView gridview;
    private int[] imageResId;
    private String[] text;
    private Context context;
    private PopupWindow popWin;
    private PhotoImageAdapter photoImageAdapter=null;
    private SchoolPhotoActivity activity;
    private int id;
    private Photo.Model model;


    public Fragmentphoto (String [] text,int[] imageResId,int id){
        this.text=text;
        this.imageResId=imageResId;
        this.context=getActivity();
        this.id=id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_schoolphoto, container, false);
        gridview = (GridView)rootView.findViewById(R.id.cnp_gridview);
        imageResId = new int[] { R.drawable.schoolphoto1,R.drawable.schoolphoto2
                ,R.drawable.schoolphoto3,R.drawable.schoolphoto4};
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((BaseActivity)getActivity()).ShowPop(gridview,((Photo)(photoImageAdapter.getItem(i))).getImagepics());
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(photoImageAdapter==null){
            loadphotoysfc();
        }else{
            gridview.setAdapter(photoImageAdapter);
        }
    }

    private void loadphotoysfc() {
        activity = (SchoolPhotoActivity)getActivity();
        SchoolPhotoListRequestListener sendwordRequestListener
                = new SchoolPhotoListRequestListener(getActivity(),id);
        activity.spiceManager.execute(new SchoolPhotoListRequest(Photo.Model.class, activity), "frag", DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    public void initData(Photo.Model model){
        this.model=model;
        String[] resKeys=new String[]{"getImagethpath","getTitle"};
        int[] reses=new int[]{R.id.gridview_image,R.id.gridview_name};
        photoImageAdapter=new PhotoImageAdapter(activity,model.getData(),R.layout.layout_item_gridview_image,resKeys,reses);
        gridview.setAdapter(photoImageAdapter);
    }

    private  void ShowPop(View view) {
        View popView = this.getActivity().getLayoutInflater().inflate(
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
