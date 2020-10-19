package com.example.ankas.Class;

public class Subcategory {

    private int id;
    private int category_id;
    private String title;
    private String image_url;

    public Subcategory(int id, int category_id, String title, String image_url) {
        this.id = id;
        this.category_id = category_id;
        this.title = title;
        this.image_url = image_url;
    }

    public int getId() {
        return id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }
}
