package com.feevx.ravan.worldcupwallpapers.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.kc.unsplash.Unsplash;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * ‍ Spelled 💫  by 🧙 maester ravan on 02/08/2018 in Arcane Castle ✨🏰✨
 **/

public class PhotosViewModel extends ViewModel {

    private MutableLiveData<List<Photo>> mPhotoLiveData = new MutableLiveData<List<Photo>>();
    private Unsplash mUnsplash;
    private List<Photo> mCurrentData = new ArrayList();
    private String currentQuery;
    private int currentPage;

    @Inject
    public PhotosViewModel(Unsplash unsplash) {
        mUnsplash = unsplash;
    }

    public void init() {
        searchPhotos("World Cup 2018");
    }

    public void searchPhotos(String query){
        currentQuery = query;
        currentPage = 1;
        mUnsplash.searchPhotos(currentQuery, currentPage, 20, new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Timber.d("Total Results Found %s", results.getTotal());
                mCurrentData.clear();
                mCurrentData.addAll(results.getResults());
                mPhotoLiveData.setValue(mCurrentData);
            }

            @Override
            public void onError(String error) {
                Timber.e("Unsplash %s", error);
            }
        });
    }

    public void loadNextPage(){
        currentPage++;
        Timber.d( "loadNextPage: %s", currentPage);
        mUnsplash.searchPhotos(currentQuery, currentPage, 20, new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Timber.d("Total Results Found %s", results.getTotal());
                mCurrentData.clear();
                mCurrentData.addAll(results.getResults());
                mPhotoLiveData.setValue(mCurrentData);
            }

            @Override
            public void onError(String error) {
                Timber.e("Unsplash %s", error);
            }
        });
    }

    public MutableLiveData<List<Photo>> getLivePhotoData() {
        return mPhotoLiveData;
    }
}
