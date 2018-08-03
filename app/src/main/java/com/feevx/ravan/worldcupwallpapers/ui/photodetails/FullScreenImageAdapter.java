package com.feevx.ravan.worldcupwallpapers.ui.photodetails;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
    private SetWallpaperClick mSetWallpaperClick;
    private BackClick mBackClick;

    public interface SetWallpaperClick {
        void onSetClicked(Photo photo);
    }

    public interface BackClick {
        void onBackPressed();
    }

    public FullScreenImageAdapter(Activity activity, SetWallpaperClick setWallpaperClick, BackClick backClick) {
        mActivity = activity;
        mSetWallpaperClick = setWallpaperClick;
        mBackClick = backClick;
    }

    @Override
    public int getCount() {
        return this.mPhotos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;
        Button setWallpaperBtn;
        Button backBtn;
        ProgressBar loading;

        inflater = (LayoutInflater) mActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        imgDisplay = viewLayout.findViewById(R.id.imgDisplay);
        setWallpaperBtn = viewLayout.findViewById(R.id.set_wallpaper);
        loading = viewLayout.findViewById(R.id.loading);
        backBtn = viewLayout.findViewById(R.id.back);

        GlideApp.with(mActivity).load(mPhotos.get(position).getUrls().getFull()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                loading.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                loading.setVisibility(View.GONE);
                return false;
            }
        }).into(imgDisplay);

        setWallpaperBtn.setOnClickListener(v -> mSetWallpaperClick.onSetClicked(mPhotos.get(position)));
        backBtn.setOnClickListener(v -> mBackClick.onBackPressed());

        (container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((RelativeLayout) object);

    }

    public void updateData(List<Photo> data) {
        mPhotos.clear();
        mPhotos.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<Photo> data) {
        mPhotos.addAll(data);
        notifyDataSetChanged();
    }
}
