package com.example.flexieecsapp;

import java.util.Date;

public class Photo {
    private int id;
    private String title;
    private Date date;
    private int imageResource;

    public Photo(int id, String title, Date date, int imageResource) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.imageResource = imageResource;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public Date getDate() { return date; }
    public int getImageResource() { return imageResource; }
}