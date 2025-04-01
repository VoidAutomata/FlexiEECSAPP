package com.example.flexieecsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class PhotoViewerActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private Button btnPrevious, btnNext;
    private PhotoManager photoManager;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        System.out.println("PhotoViewerActivity");

        photoManager = PhotoManager.getInstance();
        currentPosition = getIntent().getIntExtra("position", 0);

        viewPager = findViewById(R.id.view_pager);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);

        PhotoViewerAdapter adapter = new PhotoViewerAdapter(photoManager.getPhotos());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition, false);

        if (photoManager.isSwipeNavigation()) {
            viewPager.setUserInputEnabled(true);
            btnPrevious.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
        } else {
            viewPager.setUserInputEnabled(false);
            btnPrevious.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnPrevious.setOnClickListener(v -> {
                if (currentPosition > 0) {
                    currentPosition--;
                    viewPager.setCurrentItem(currentPosition);
                }
            });
            btnNext.setOnClickListener(v -> {
                if (currentPosition < photoManager.getPhotos().size() - 1) {
                    currentPosition++;
                    viewPager.setCurrentItem(currentPosition);
                }
            });
        }
    }
}