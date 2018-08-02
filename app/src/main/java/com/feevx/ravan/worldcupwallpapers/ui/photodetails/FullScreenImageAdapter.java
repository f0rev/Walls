package com.feevx.ravan.worldcupwallpapers.ui.photodetails;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.feevx.ravan.worldcupwallpapers.R;
import com.feevx.ravan.worldcupwallpapers.configs.GlideApp;
import com.feevx.ravan.worldcupwallpapers.configs.widgets.TouchImageView;
import com.kc.unsplash.models.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * ‍ Spelled 💫  by 🧙 maester ravan on 03/08/2018 in Arcane Castle ✨🏰✨
 **/

public class FullScreenImageAdapter extends PagerAdapter {
    private Activity mActivity;
    private List<Photo> mPhotos = new ArrayList<>();
    private LayoutInflater inflater;

    // constructor
    public FullScreenImageAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return this.mPhotos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;
        Button btnClose;

        inflater = (LayoutInflater) mActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        imgDisplay =  viewLayout.findViewById(R.id.imgDisplay);
        btnClose =  viewLayout.findViewById(R.id.btnClose);

        GlideApp.with(mActivity).load(mPhotos.get(position).getUrls().getFull()).into(imgDisplay);

        // close button click event
//        btnClose.setOnClickListener(v -> mActivity.finish());

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    public void updateData(List<Photo> data){
        mPhotos.clear();
        mPhotos.addAll(data);
        notifyDataSetChanged();
    }
    public void addData(List<Photo> data){
        mPhotos.addAll(data);
        notifyDataSetChanged();
    }
}
