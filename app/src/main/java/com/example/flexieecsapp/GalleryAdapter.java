package com.example.flexieecsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.bumptech.glide.Glide;

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_GRID = 0;
    private static final int VIEW_TYPE_LIST = 1;

    private Context context;
    private List<Photo> photos;
    private boolean isGridView;

    public GalleryAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
        this.isGridView = true;
    }

    public void setGridView(boolean gridView) {
        isGridView = gridView;
    }

    @Override
    public int getItemViewType(int position) {
        return isGridView ? VIEW_TYPE_GRID : VIEW_TYPE_LIST;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_GRID) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_photo_grid, parent, false);
            return new GridViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_photo_list, parent, false);
            return new ListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Photo photo = photos.get(position);

        if (holder instanceof GridViewHolder) {
            Glide.with(context)
                    .load(photo.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(((GridViewHolder) holder).imageView);

        } else if (holder instanceof ListViewHolder) {
            ListViewHolder listHolder = (ListViewHolder) holder;

            Glide.with(context)
                    .load(photo.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(listHolder.imageView);

            listHolder.titleTextView.setText(photo.getTitle());
            listHolder.dateTextView.setText(photo.getDate().toString());
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PhotoViewerActivity.class);
            intent.putExtra("position", position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        GridViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView dateTextView;

        ListViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
        }
    }
}