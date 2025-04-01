package com.example.flexieecsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GalleryAdapter adapter;
    private Button btnGrid, btnList, btnSwipe, btnButton;
    private Spinner spinnerSort;
    private PhotoManager photoManager;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this); // 🔹 Initialize DB helper
        photoManager = PhotoManager.getInstance();

        //dbHelper.insertPhoto("Space thing", "https://w7.pngwing.com/pngs/691/438/png-transparent-desktop-blue-space-nebula-space-texture-blue-atmosphere.png", "2025-04-01");

        System.out.println("loading photos from database");
        // 🔹 Load photos from database
        loadPhotosFromDatabase();

        photoManager.sortDescending(); // Optional default sort

        recyclerView = findViewById(R.id.recycler_view);
        btnGrid = findViewById(R.id.btn_grid);
        btnList = findViewById(R.id.btn_list);
        btnSwipe = findViewById(R.id.btn_swipe);
        btnButton = findViewById(R.id.btn_button);
        spinnerSort = findViewById(R.id.spinner_sort);

        System.out.println("Photo list: "+photoManager.getPhotos().size());
        adapter = new GalleryAdapter(this, photoManager.getPhotos());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Default view
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
                if (position == 0) {
                    photoManager.sortDescending();
                } else {
                    photoManager.sortAscending();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    // 🔹 Load photos from the database
    private void loadPhotosFromDatabase() {
        Cursor cursor = dbHelper.getAllPhotos();
        System.out.println("cursor: "+cursor);
        ArrayList<Photo> loadedPhotos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            System.out.println("Photo exists");
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));

                // Parse date string to Date object
                Date date;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    date = sdf.parse(dateStr);
                } catch (Exception e) {
                    e.printStackTrace();
                    date = new Date(); // fallback to current date
                }

                String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_URI));
                loadedPhotos.add(new Photo(id, title, date, imageUrl));

            } while (cursor.moveToNext());
        } else {
            System.out.println("No photos found in the database.");
        }
        cursor.close();

        photoManager.setPhotos(loadedPhotos); // Make sure this method is added in PhotoManager
    }

}