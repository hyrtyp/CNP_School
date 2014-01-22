package com.hyrt.cnp.school.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private String pkind;


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
        pkind=(id+1)+"";
        activity = (SchoolPhotoActivity)getActivity();
        SchoolPhotoListRequestListener sendwordRequestListener
                = new SchoolPhotoListRequestListener(getActivity(),id);
        activity.spiceManager.execute(new SchoolPhotoListRequest(Photo.Model.class, activity,pkind), "frag", DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    public void initData(Photo.Model model){
        this.model=model;
        String[] resKeys=new String[]{"getImagethpath","getTitle"};
        int[] reses=new int[]{R.id.gridview_image,R.id.gridview_name};
        photoImageAdapter=new PhotoImageAdapter(activity,model.getData(),R.layout.layout_item_gridview_image,resKeys,reses);
        gridview.setAdapter(photoImageAdapter);
    }
}
