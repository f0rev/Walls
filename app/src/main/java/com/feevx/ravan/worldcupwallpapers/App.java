package com.feevx.ravan.worldcupwallpapers;

import android.app.Application;
import android.app.Activity;

import com.feevx.ravan.worldcupwallpapers.BuildConfig;
import com.feevx.ravan.worldcupwallpapers.di.component.DaggerAppComponent;
import com.kc.unsplash.Unsplash;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

/**
 * ‍ Spelled 💫  by 🧙 maester ravan on 02/08/2018 in Arcane Castle ✨🏰✨
 **/

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        initDagger();
  }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }



    private void initDagger(){
        DaggerAppComponent.builder().application(this).build().inject(this);
    }
}