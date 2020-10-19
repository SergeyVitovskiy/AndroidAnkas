package com.example.ankas.Class;

public class Category {
    private int id;
    private String title;
    private String image_url;

    public Category(int id, String title, String image_url) {
        this.id = id;
        this.title = title;
        this.image_url = image_url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }
}
