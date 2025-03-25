package com.example.flexieecsapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PhotoManager {
    private static PhotoManager instance;
    private List<Photo> photos;
    private boolean isSwipeNavigation = true; // Default to swipe

    private PhotoManager() {
        photos = generateDummyPhotos();
    }

    public static PhotoManager getInstance() {
        if (instance == null) {
            instance = new PhotoManager();
        }
        return instance;
    }

    private List<Photo> generateDummyPhotos() {
        List<Photo> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -random.nextInt(365));
            Date date = cal.getTime();
            list.add(new Photo(i, "Photo " + i, date, R.drawable.photo));
        }
        return list;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void sortAscending() {
        Collections.sort(photos, (p1, p2) -> p1.getDate().compareTo(p2.getDate()));
    }

    public void sortDescending() {
        Collections.sort(photos, (p1, p2) -> p2.getDate().compareTo(p1.getDate()));
    }

    public boolean isSwipeNavigation() {
        return isSwipeNavigation;
    }

    public void setSwipeNavigation(boolean swipeNavigation) {
        isSwipeNavigation = swipeNavigation;
    }
}