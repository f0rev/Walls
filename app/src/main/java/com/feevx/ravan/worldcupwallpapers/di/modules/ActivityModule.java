package com.feevx.ravan.worldcupwallpapers.di.modules;

import android.app.Activity;
import android.content.Context;

import com.feevx.ravan.worldcupwallpapers.ui.MainActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * ‍ Spelled 💫  by 🧙 maester ravan on 02/08/2018 in Arcane Castle ✨🏰✨
 **/

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = {FragmentModule.class})
    abstract MainActivity contributeMainActivity();

}