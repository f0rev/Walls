package com.feevx.ravan.worldcupwallpapers.di.modules;

import android.app.Application;
import android.content.Context;

import com.feevx.ravan.worldcupwallpapers.App;
import com.feevx.ravan.worldcupwallpapers.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kc.unsplash.Unsplash;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ‍ Spelled 💫  by 🧙 maester ravan on 02/08/2018 in Arcane Castle ✨🏰✨
 **/

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    Unsplash provideUnsplash(Application application) {
        return new Unsplash(application.getString(R.string.unsplash_acces_key));
    }

}
