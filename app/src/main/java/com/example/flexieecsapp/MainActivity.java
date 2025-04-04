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

        // insert photos
        dbHelper.insertPhoto("Silhouette to perfection ", "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0", "2002-10-16");
        dbHelper.insertPhoto("Old Iphone Picture", "https://images.unsplash.com/photo-1587840171670-8b850147754e", "2021-04-15");
        dbHelper.insertPhoto("Sunset Beach", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e", "2003-02-23");
        dbHelper.insertPhoto("Cottage in the middle of nowhere", "https://images.unsplash.com/photo-1470770841072-f978cf4d019e", "2022-06-06");
        dbHelper.insertPhoto("Canoeing in crystal clear water", "https://images.unsplash.com/photo-1501785888041-af3ef285b470", "2005-09-18");
        dbHelper.insertPhoto("Hiking", "https://images.unsplash.com/photo-1508921912186-1d1a45ebb3c1", "2015-07-21");
        dbHelper.insertPhoto("The Big Ben", "https://images.unsplash.com/photo-1505761671935-60b3a7427bad", "2011-05-19");
        dbHelper.insertPhoto("Endless Road", "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee", "2000-08-06");
        dbHelper.insertPhoto("Satelite View", "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa", "2004-06-28");
        dbHelper.insertPhoto("Downtown Skyline", "https://images.unsplash.com/photo-1444084316824-dc26d6657664", "2008-07-04");
        dbHelper.insertPhoto("Sunset Hills", "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05", "2016-01-13");
        dbHelper.insertPhoto("Modern Home Design", "https://images.unsplash.com/photo-1600585154340-be6161a56a0c", "2023-05-30");
        dbHelper.insertPhoto("Person Using a Mac", "https://images.unsplash.com/photo-1508780709619-79562169bc64", "2020-06-19");
        dbHelper.insertPhoto("Starburst Sky", "https://images.unsplash.com/photo-1435224654926-ecc9f7fa028c", "2009-11-08");
        dbHelper.insertPhoto("Airpods Pro", "https://images.unsplash.com/photo-1504274066651-8d31a536b11a", "2017-03-15");
        dbHelper.insertPhoto("Waterfall in Africa", "https://images.unsplash.com/photo-1455218873509-8097305ee378", "2001-12-03");
        dbHelper.insertPhoto("Foggy Forest", "https://images.unsplash.com/photo-1482192596544-9eb780fc7f66", "2019-02-28");
        dbHelper.insertPhoto("Retro Nike Shoe", "https://images.unsplash.com/photo-1491553895911-0055eca6402d", "2010-09-11");
        dbHelper.insertPhoto("Student Studying", "https://images.unsplash.com/photo-1533750516457-a7f992034fec", "2013-05-24");
        dbHelper.insertPhoto("Money", "https://unsplash.com/photos/8lnbXtxFGZw/download?force=true", "2014-01-07");
        dbHelper.insertPhoto("Computer Engineering: Arduino Board", "https://unsplash.com/photos/3GZi6OpSDcY/download?force=true", "2006-07-26");
        dbHelper.insertPhoto("Beautiful View", "https://unsplash.com/photos/1Z2niiBPg5A/download?force=true", "2024-08-05");
        dbHelper.insertPhoto("IPhone 15 UI", "https://unsplash.com/photos/9e9PD9blAto/download?force=true", "2007-06-03");
        dbHelper.insertPhoto("Minamlistic Desk with a MAC", "https://unsplash.com/photos/1SAnrIxw5OY/download?force=true", "1999-01-29");
        dbHelper.insertPhoto("Modern Classy White Chair", "https://unsplash.com/photos/1P6AnKDw6S8/download?force=true", "2018-09-09");
        dbHelper.insertPhoto("Gaming Setup", "https://images.pexels.com/photos/3165335/pexels-photo-3165335.jpeg", "2025-04-18");
        dbHelper.insertPhoto("RGB Keyboard", "https://images.pexels.com/photos/2041397/pexels-photo-2041397.jpeg", "2012-11-27");
        dbHelper.insertPhoto("Music System", "https://images.pexels.com/photos/744318/pexels-photo-744318.jpeg", "2003-10-20");
        dbHelper.insertPhoto("Love Background", "https://images.pexels.com/photos/220072/pexels-photo-220072.jpeg", "2002-01-01");
        dbHelper.insertPhoto("Girl in a Park", "https://images.pexels.com/photos/394566/pexels-photo-394566.jpeg", "2006-10-07");
        dbHelper.insertPhoto("PlayStation Controller", "https://images.pexels.com/photos/1298601/pexels-photo-1298601.jpeg", "2023-12-01");
        dbHelper.insertPhoto("PC Setup", "https://images.pexels.com/photos/777001/pexels-photo-777001.jpeg", "1999-08-18");
        dbHelper.insertPhoto("Gaming Keyboard", "https://images.pexels.com/photos/735911/pexels-photo-735911.jpeg", "2015-04-03");
        dbHelper.insertPhoto("Old Chair in a Dark Room", "https://images.pexels.com/photos/696407/pexels-photo-696407.jpeg", "2022-10-02");
        dbHelper.insertPhoto("Girls Rule", "https://images.pexels.com/photos/2115257/pexels-photo-2115257.jpeg", "2024-01-19");
        dbHelper.insertPhoto("Nike Shoes", "https://images.pexels.com/photos/786003/pexels-photo-786003.jpeg", "2016-06-22");
        dbHelper.insertPhoto("Milky Way Galaxy", "https://images.pexels.com/photos/110854/pexels-photo-110854.jpeg", "2005-05-10");
        dbHelper.insertPhoto("Spiral Galaxy", "https://images.pexels.com/photos/2150/sky-space-dark-galaxy.jpg", "2000-07-29");
        dbHelper.insertPhoto("Space Shuttle Launch", "https://images.pexels.com/photos/23764/pexels-photo.jpg", "2021-01-13");
        dbHelper.insertPhoto("Aurora Borealis", "https://images.pexels.com/photos/1933319/pexels-photo-1933319.jpeg", "2014-10-25");
        dbHelper.insertPhoto("Small Town by the River", "https://images.pexels.com/photos/356807/pexels-photo-356807.jpeg", "2020-03-16");
        dbHelper.insertPhoto("Rocket in Orbit", "https://images.pexels.com/photos/586030/pexels-photo-586030.jpeg", "2017-11-15");
        dbHelper.insertPhoto("Meteor Shower", "https://images.pexels.com/photos/1252890/pexels-photo-1252890.jpeg", "2008-09-08");
        dbHelper.insertPhoto("Hubble Telescope", "https://images.pexels.com/photos/256381/pexels-photo-256381.jpeg", "1999-03-05");
        dbHelper.insertPhoto("Dandelion Puffs", "https://images.pexels.com/photos/206717/pexels-photo-206717.jpeg", "2018-04-29");
        dbHelper.insertPhoto("Outer Space", "https://images.pexels.com/photos/998641/pexels-photo-998641.jpeg", "2007-12-20");
        dbHelper.insertPhoto("Children Playing", "https://images.pexels.com/photos/296301/pexels-photo-296301.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", "2004-04-04");
        dbHelper.insertPhoto("Holding Soccer Jersey", "https://images.pexels.com/photos/27271619/pexels-photo-27271619/free-photo-of-a-man-holding-a-soccer-jersey-with-the-number-7-on-it.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", "2010-06-11");
        dbHelper.insertPhoto("Santiago Bernabeau", "https://images.pexels.com/photos/3845970/pexels-photo-3845970.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", "2022-08-28");
        dbHelper.insertPhoto("Professional Swimmer", "https://images.pexels.com/photos/863988/pexels-photo-863988.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", "2011-02-26");
        dbHelper.insertPhoto("Girl with Balloons", "https://images.pexels.com/photos/2304791/pexels-photo-2304791.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", "2009-03-27");
        dbHelper.insertPhoto("Colosseum", "https://images.pexels.com/photos/1797161/pexels-photo-1797161.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", "2019-12-31");
        dbHelper.insertPhoto("Taj Mahal at Sunrise", "https://images.pexels.com/photos/30638767/pexels-photo-30638767/free-photo-of-majestic-view-of-the-taj-mahal-at-sunrise.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", "2017-06-13");

        System.out.println("loading photos from database");

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