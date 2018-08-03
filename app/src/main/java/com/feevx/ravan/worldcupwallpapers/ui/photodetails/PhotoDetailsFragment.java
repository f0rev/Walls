package com.feevx.ravan.worldcupwallpapers.ui.photodetails;


import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feevx.ravan.worldcupwallpapers.R;
import com.feevx.ravan.worldcupwallpapers.configs.GlideApp;
import com.feevx.ravan.worldcupwallpapers.configs.GlideRequest;
import com.feevx.ravan.worldcupwallpapers.ui.MainActivity;
import com.feevx.ravan.worldcupwallpapers.viewmodels.PhotosViewModel;
import com.kc.unsplash.models.Photo;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

/**
 * ‍ Spelled 💫  by 🧙 maester ravan on 02/08/2018 in Arcane Castle ✨🏰✨
 **/

public class PhotoDetailsFragment extends Fragment implements FullScreenImageAdapter.SetWallpaperClick, FullScreenImageAdapter.BackClick {

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
    private Bitmap readyBitmap;
    private CompositeDisposable mCompositeDisposable;
    private ProgressDialog mProgressDialog;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) getActivity();
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
        Bundle args = getArguments();
        if (args != null)
            mPhotoIndex = args.getInt(PHOTO_INDEX);

        mCompositeDisposable = new CompositeDisposable();
        configureViewmodel();
        configureDagger();
        mMainActivity.fullScreen(true);
        mFullScreenImageAdapter = new FullScreenImageAdapter(getActivity(), this, this);
        mPager.setAdapter(mFullScreenImageAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
        mMainActivity.fullScreen(false);
    }

    @Override
    public void onSetClicked(Photo photo) {
        mProgressDialog = ProgressDialog.show(mMainActivity, null,
                "Обой устанавливается", true);

        PublishSubject<Bitmap> stop = PublishSubject.create();
        Observable<Bitmap> bitmapObservable = getBitmapFromUrl(photo);
        mCompositeDisposable.add(bitmapObservable
                .takeUntil(stop)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    setBitmapAsWallpaper(readyBitmap);
                })
                .subscribe(bitmap -> {
                    readyBitmap = bitmap;
                    stop.onComplete();
                }, Timber::e));
    }


    public Observable<Bitmap> getBitmapFromUrl(final Photo photo) {
        GlideRequest<Bitmap> glideRequest = GlideApp.with(mContext)
                .asBitmap();
        return Observable.create(emitter -> {
            Bitmap bitmap = glideRequest.load(photo.getUrls().getFull())
                    .submit()
                    .get();
            emitter.onNext(bitmap);
        });
    }

    void setBitmapAsWallpaper(Bitmap bitmap) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
        try {
            wallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mProgressDialog.dismiss();
        }
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    private void configureViewmodel() {
        mPhotoViewModel = ViewModelProviders.of(mMainActivity).get(PhotosViewModel.class);
        mPhotoViewModel.getLivePhotoData().observe(this, data -> {
            if (data.size() > 20)
                mFullScreenImageAdapter.addData(data);
            else {
                mFullScreenImageAdapter.updateData(data);
                mPager.setCurrentItem(mPhotoIndex);
            }
        });
    }

    @Override
    public void onBackPressed() {
        assert getFragmentManager() != null;
        getFragmentManager().popBackStack();
    }
}

