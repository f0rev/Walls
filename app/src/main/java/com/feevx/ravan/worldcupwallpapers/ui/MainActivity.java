package com.feevx.ravan.worldcupwallpapers.ui;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.feevx.ravan.worldcupwallpapers.R;
import com.feevx.ravan.worldcupwallpapers.ui.photodetails.PhotoDetailsFragment;
import com.feevx.ravan.worldcupwallpapers.ui.photolist.PhotoListFragment;
import com.feevx.ravan.worldcupwallpapers.utils.GeneralUtils;
import com.feevx.ravan.worldcupwallpapers.viewmodels.PhotosViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * ‍ Spelled 💫  by 🧙 maester ravan on 02/08/2018 in Arcane Castle ✨🏰✨
 **/

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    Application mContext;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    private PhotosViewModel mPhotoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configureDagger();
        configureViewModel();
        configureSearchView();

        if (savedInstanceState == null) {
            PhotoListFragment fragment = new PhotoListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }

    private void configureSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mPhotoViewModel.searchPhotos(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        EditText editText = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setOnEditorActionListener((v, keyAction, keyEvent) -> {
            if (
                    keyAction == EditorInfo.IME_ACTION_SEARCH ||
                            (keyEvent != null && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode()
                                    && keyEvent.getAction() == KeyEvent.ACTION_DOWN)) {
                GeneralUtils.toggleKeybordVisibility(this);
                mPhotoViewModel.searchPhotos(mSearchView.getQuery().toString());

                return true;
            }
            return false;
        });
    }


    private void configureDagger() {
        AndroidInjection.inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    private void configureViewModel() {
        mPhotoViewModel = ViewModelProviders.of(this, viewModelFactory).get(PhotosViewModel.class);
        mPhotoViewModel.init();
        mPhotoViewModel.getLivePhotoData().observe(this, data -> {
        });
    }

    public void fullScreen(boolean hide) {
        ActionBar actionBar = getSupportActionBar();
        Window window = getWindow();
        if (hide) {
            mSearchView.setVisibility(View.GONE);
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            actionBar.hide();
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            actionBar.show();
            mSearchView.setVisibility(View.VISIBLE);

        }
    }
}
