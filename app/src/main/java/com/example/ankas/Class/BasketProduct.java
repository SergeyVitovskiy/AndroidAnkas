package com.example.ankas.Class;

public class BasketProduct {
    private int article;
    private String title;
    private String price;
    private String brand;
    private String image_url;
    private String description;
    private String availability;

    public BasketProduct(int article, String title, String price, String brand, String image_url, String description, String availability) {
        this.article = article;
        this.title = title;
        this.price = price;
        this.brand = brand;
        this.image_url = image_url;
        this.description = description;
        this.availability = availability;
    }

    public int getArticle() {
        return article;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getDescription() {
        return description;
    }

    public String getAvailability() {
        return availability;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
