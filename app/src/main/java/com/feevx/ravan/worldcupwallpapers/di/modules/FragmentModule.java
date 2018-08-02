package com.feevx.ravan.worldcupwallpapers.di.modules;


import com.feevx.ravan.worldcupwallpapers.ui.photodetails.PhotoDetailsFragment;
import com.feevx.ravan.worldcupwallpapers.ui.photolist.PhotoListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * ‍ Spelled 💫  by 🧙 maester ravan on 02/08/2018 in Arcane Castle ✨🏰✨
 **/

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract PhotoListFragment contributeWallpapersListFragment();

    @ContributesAndroidInjector
    abstract PhotoDetailsFragment contributePhotoDetailsFragment();


}
