package com.feevx.ravan.worldcupwallpapers.ui.photolist;


import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feevx.ravan.worldcupwallpapers.R;
import com.feevx.ravan.worldcupwallpapers.ui.MainActivity;
import com.feevx.ravan.worldcupwallpapers.ui.photodetails.PhotoDetailsFragment;
import com.feevx.ravan.worldcupwallpapers.viewmodels.PhotosViewModel;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.kc.unsplash.Unsplash;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;
import com.victor.loading.rotate.RotateLoading;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

/**
 * ‍ Spelled 💫  by 🧙 maester ravan on 02/08/2018 in Arcane Castle ✨🏰✨
 **/

public class PhotoListFragment extends Fragment implements PhotoAdapter.PhotoClickHandler {


    @Inject
    Application mContext;

    @BindView(R.id.photo_list_view)
    RecyclerView mPhotoListView;
    Unbinder unbinder;
    @BindView(R.id.rotateloading)
    RotateLoading mRotateLoading;
    private View mRootView;
    private PhotoAdapter mPhotoAdapter;
    private PhotosViewModel mPhotoViewModel;
    private MainActivity mMainActivity;
    private boolean loadingNextPage;

    public PhotoListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureViewmodel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_photo_list, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureDagger();
        configureList();


    }

    private void configureViewmodel() {
        mPhotoViewModel = ViewModelProviders.of(mMainActivity).get(PhotosViewModel.class);
        mPhotoViewModel.getLivePhotoData().observe(this, data -> {
            if (loadingNextPage) {
                mPhotoAdapter.addData(data);
                loadingNextPage = false;
            }
            else
                mPhotoAdapter.updateData(data);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        findWorldCupPhoto();
    }

    private void configureList() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        mPhotoListView.setLayoutManager(layoutManager);
        mPhotoAdapter = new PhotoAdapter(this);
        mPhotoListView.setAdapter(mPhotoAdapter);
        mPhotoListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLastItemDisplaying(mPhotoListView)) {
                    loadingNextPage = true;
                    mPhotoViewModel.loadNextPage();
                }
            }
        });
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((FlexboxLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onPhotoClicked(int position) {
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .add(R.id.fragment_container, PhotoDetailsFragment.newInstance(position), null)
                .commit();
    }

    private void findWorldCupPhoto() {

    }

    void startLoading() {
        mRotateLoading.start();
    }

    void stopLoading() {
        mRotateLoading.stop();
    }
}
