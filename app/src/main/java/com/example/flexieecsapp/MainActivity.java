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

        // First, check if there are any photos.
        if (dbHelper.getAllPhotos().getCount() != 0) {
            // If photos exist, delete them all.
            dbHelper.deleteAllPhotos();  // Make sure to implement this method in DatabaseHelper.
        }

        // Now, insert the photos.
        dbHelper.insertPhoto("Forest River", "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0", "2025-04-03");
        dbHelper.insertPhoto("Galaxy", "https://images.unsplash.com/photo-1587840171670-8b850147754e", "2025-04-04");
        dbHelper.insertPhoto("Sunset Beach", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e", "2025-04-05");
        //dbHelper.insertPhoto("Desert Dunes", "https://images.unsplash.com/photo-1606788075760-9c65a3f6d4b6", "2025-04-06");
        dbHelper.insertPhoto("Milky Way", "https://images.unsplash.com/photo-1470770841072-f978cf4d019e", "2025-04-07");
        dbHelper.insertPhoto("Foggy Forest", "https://images.unsplash.com/photo-1501785888041-af3ef285b470", "2025-04-08");
        dbHelper.insertPhoto("Mountain Lake", "https://images.unsplash.com/photo-1508921912186-1d1a45ebb3c1", "2025-04-09");
        dbHelper.insertPhoto("City Skyline", "https://images.unsplash.com/photo-1505761671935-60b3a7427bad", "2025-04-10");
        //dbHelper.insertPhoto("Snowy Trees", "https://images.unsplash.com/photo-1489587021549-876dc54226b0", "2025-04-11");
        dbHelper.insertPhoto("Ocean Cliff", "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee", "2025-04-12");

        dbHelper.insertPhoto("Canyon View", "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa", "2025-04-13");
        dbHelper.insertPhoto("Star Trails", "https://images.unsplash.com/photo-1444084316824-dc26d6657664", "2025-04-14");
        //dbHelper.insertPhoto("Wild Waterfall", "https://images.unsplash.com/photo-1441829266145-b0e0edd72bb6", "2025-04-15");
        //dbHelper.insertPhoto("Rocky Mountains", "https://images.unsplash.com/photo-1504198453319-5ce911bafcde", "2025-04-16");
        dbHelper.insertPhoto("Jungle Path", "https://images.unsplash.com/photo-1501785888041-af3ef285b470", "2025-04-17");
        dbHelper.insertPhoto("Sunset Hills", "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05", "2025-04-18");
        dbHelper.insertPhoto("Frozen Lake", "https://images.unsplash.com/photo-1600585154340-be6161a56a0c", "2025-04-19");
        //dbHelper.insertPhoto("Cloudy Mountains", "https://images.unsplash.com/photo-1549887534-1610f0881f5b", "2025-04-20");
        dbHelper.insertPhoto("Coastal Road", "https://images.unsplash.com/photo-1506744038136-46273834b3fb", "2025-04-21");
        dbHelper.insertPhoto("Winter Forest", "https://images.unsplash.com/photo-1508780709619-79562169bc64", "2025-04-22");

        dbHelper.insertPhoto("Starburst Sky", "https://images.unsplash.com/photo-1435224654926-ecc9f7fa028c", "2025-04-23");
        dbHelper.insertPhoto("River Bend", "https://images.unsplash.com/photo-1504274066651-8d31a536b11a", "2025-04-24");
        dbHelper.insertPhoto("Icy Mountain", "https://images.unsplash.com/photo-1455218873509-8097305ee378", "2025-04-25");
        //dbHelper.insertPhoto("Cave Opening", "https://images.unsplash.com/photo-1465311444082-b6c1f7bba295", "2025-04-26");
        dbHelper.insertPhoto("Rock Formations", "https://images.unsplash.com/photo-1482192596544-9eb780fc7f66", "2025-04-27");
        dbHelper.insertPhoto("Bright Stars", "https://images.unsplash.com/photo-1435224654926-ecc9f7fa028c", "2025-04-28");
        dbHelper.insertPhoto("Tropical Island", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e", "2025-04-29");
        dbHelper.insertPhoto("Firewatch Tower", "https://images.unsplash.com/photo-1491553895911-0055eca6402d", "2025-04-30");
        dbHelper.insertPhoto("Cloud Piercing Peak", "https://images.unsplash.com/photo-1506744038136-46273834b3fb", "2025-05-01");
        dbHelper.insertPhoto("Crisp Horizon", "https://images.unsplash.com/photo-1533750516457-a7f992034fec", "2025-05-02");





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