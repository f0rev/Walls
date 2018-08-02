package com.feevx.ravan.worldcupwallpapers.ui.photolist;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.feevx.ravan.worldcupwallpapers.R;
import com.feevx.ravan.worldcupwallpapers.databinding.PhotoItemBinding;
import com.kc.unsplash.models.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * ‍ Spelled 💫  by 🧙 maester ravan on 02/08/2018 in Arcane Castle ✨🏰✨
 **/

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private final List<Photo> mData = new ArrayList<>();
    private PhotoClickHandler mPhotoClickHandler;

    public PhotoAdapter(PhotoClickHandler photoClickHandler) {
        mPhotoClickHandler = photoClickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        PhotoItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.photo_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Photo photo = mData.get(position);
        holder.mBinding.setPhoto(photo);
        holder.mBinding.photo.setOnClickListener(v -> mPhotoClickHandler.onPhotoClicked(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateData(List<Photo> data){
        mData.clear();
        mData.addAll(data);
        notifyItemRangeChanged(0, 20);
    }

    public void addData(List<Photo> data){
        mData.addAll(data);
        notifyItemRangeInserted(data.size()-20, 20);

    }

    public interface PhotoClickHandler {
        void onPhotoClicked(int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final PhotoItemBinding mBinding;

        ViewHolder(final PhotoItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }


    }




}
