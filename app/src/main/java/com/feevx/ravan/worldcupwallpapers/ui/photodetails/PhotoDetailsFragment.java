package com.feevx.ravan.worldcupwallpapers.ui.photodetails;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.feevx.ravan.worldcupwallpapers.R;
import com.feevx.ravan.worldcupwallpapers.ui.MainActivity;
import com.feevx.ravan.worldcupwallpapers.viewmodels.PhotosViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ‍ Spelled 💫  by 🧙 maester ravan on 02/08/2018 in Arcane Castle ✨🏰✨
 **/

public class PhotoDetailsFragment extends Fragment {

    @Inject
    Context mContext;

    private static final String PHOTO_INDEX = "photoIndex";
    @BindView(R.id.pager)
    ViewPager mPager;
    Unbinder unbinder;
    private int mPhotoIndex;
    private PhotosViewModel mPhotoViewModel;
    private FullScreenImageAdapter mFullScreenImageAdapter;
    private MainActivity mMainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) getActivity();


    }

    public static PhotoDetailsFragment newInstance(int photoIndex) {
        PhotoDetailsFragment albumDetailsFragment = new PhotoDetailsFragment();

        Bundle args = new Bundle();
        args.putInt(PHOTO_INDEX, photoIndex);
        albumDetailsFragment.setArguments(args);

        return albumDetailsFragment;
    }

    public PhotoDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)
            mPhotoIndex = args.getInt(PHOTO_INDEX);
        configureViewmodel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMainActivity.fullScreen(true);
        mFullScreenImageAdapter = new FullScreenImageAdapter(getActivity());
        mPager.setAdapter(mFullScreenImageAdapter);
        mPager.setCurrentItem(mPhotoIndex);


    }

    private void configureViewmodel() {
        mPhotoViewModel = ViewModelProviders.of(mMainActivity).get(PhotosViewModel.class);
        mPhotoViewModel.getLivePhotoData().observe(this, data -> {
            if (data.size() > 20)
                mFullScreenImageAdapter.addData(data);
            else
                mFullScreenImageAdapter.updateData(data);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMainActivity.fullScreen(false);
    }
}
