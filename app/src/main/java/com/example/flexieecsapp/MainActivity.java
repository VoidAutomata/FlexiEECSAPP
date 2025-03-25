package com.example.flexieecsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GalleryAdapter adapter;
    private Button btnGrid, btnList, btnSwipe, btnButton;
    private Spinner spinnerSort;
    private PhotoManager photoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoManager = PhotoManager.getInstance();
        photoManager.sortDescending(); // Initial sort: latest first

        recyclerView = findViewById(R.id.recycler_view);
        btnGrid = findViewById(R.id.btn_grid);
        btnList = findViewById(R.id.btn_list);
        btnSwipe = findViewById(R.id.btn_swipe);
        btnButton = findViewById(R.id.btn_button);
        spinnerSort = findViewById(R.id.spinner_sort);

        adapter = new GalleryAdapter(this, photoManager.getPhotos());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Default: 3-column grid
        adapter.setGridView(true);

        btnGrid.setOnClickListener(v -> {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            adapter.setGridView(true);
            adapter.notifyDataSetChanged();
        });

        btnList.setOnClickListener(v -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setGridView(false);
            adapter.notifyDataSetChanged();
        });

        btnSwipe.setOnClickListener(v -> photoManager.setSwipeNavigation(true));
        btnButton.setOnClickListener(v -> photoManager.setSwipeNavigation(false));

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) { // Latest First
                    photoManager.sortDescending();
                } else { // Earliest First
                    photoManager.sortAscending();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}