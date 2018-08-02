package com.feevx.ravan.worldcupwallpapers.configs;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * ‍ Spelled 💫  by 🧙 maester ravan on 02/08/2018 in Arcane Castle ✨🏰✨
 **/

public final class DataBinder {

    private DataBinder() {
    }

    @BindingAdapter("picture")
    public static void setPicturel(ImageView imageView, String url) {
        Context context = imageView.getContext();
        GlideApp.with(context).load(url).into(imageView);
    }
}
