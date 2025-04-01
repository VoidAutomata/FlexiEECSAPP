package com.example.flexieecsapp;

import java.util.Date;

public class Photo {
    private int id;
    private String title;
    private Date date;
    private String imageUrl;

    public Photo(int id, String title, Date date, String imageUrl) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public Date getDate() { return date; }
    public String getImageUrl() { return imageUrl; }
}