package com.example.flexieecsapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.bumptech.glide.Glide;




public class PhotoViewerAdapter extends RecyclerView.Adapter<PhotoViewerAdapter.ViewHolder> {
    private List<Photo> photos;

    public PhotoViewerAdapter(List<Photo> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_full, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        System.out.println("Loading img from url: "+photo.getImageUrl());

        Glide.with(holder.itemView.getContext())
                .load(photo.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.error_image_foreground)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_full);
        }
    }
}