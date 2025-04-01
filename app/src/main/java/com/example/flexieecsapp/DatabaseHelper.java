package com.example.flexieecsapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "flexigallery.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_PHOTOS = "photos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_URI = "uri";
    public static final String COLUMN_DATE = "date";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PHOTOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_URI + " TEXT, " +
                    COLUMN_DATE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        onCreate(db);
    }

    public void insertPhoto(String title, String url, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_URI, url);
        values.put(COLUMN_DATE, date);
        db.insert(TABLE_PHOTOS, null, values);
        db.close();
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Begin a transaction to ensure atomicity
        db.beginTransaction();
        try {
            // Step 1: Delete all records from the 'images' table
            db.execSQL("DELETE FROM photos");

            // Step 2: Set the transaction as successful
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // End the transaction
            db.endTransaction();
        }
    }

    public void deleteAllExceptFirst() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Begin a transaction to ensure atomicity
        db.beginTransaction();
        try {
            // Step 1: Retrieve all images from the database
            List<Photo> imageList = getAllPhotosIMG();

            // Step 2: Ensure there is more than one image
            if (imageList.size() > 1) {
                // Step 3: Get the ID of the first image
                int firstImageId = imageList.get(0).getId();

                // Step 4: Delete all images except the first one by ID
                db.execSQL("DELETE FROM images WHERE id != ?", new String[]{String.valueOf(firstImageId)});
            }

            // Set the transaction as successful
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // End the transaction
            db.endTransaction();
        }
    }

    public List<Photo> getAllPhotosIMG() {
        List<Photo> imageList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM images";
        Cursor cursor = db.rawQuery(query, null);

        // Loop through the results and add them to the list
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
                imageList.add(new Photo(id, title, date, imageUrl));

            } while (cursor.moveToNext());
        } else {
            System.out.println("No photos found in the database.");
        }
        cursor.close();
        return imageList;
    }

    public Cursor getAllPhotos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PHOTOS, null);
    }


}
