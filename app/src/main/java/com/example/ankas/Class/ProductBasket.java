package com.example.ankas.Class;

public class ProductBasket {
    private int article; // Индификатор
    private String title; // Название
    private String price; // Цена
    private String brand; // Бренд
    private String image_url; // Изобраджение
    private String description; // Описание
    private int quantity; // Кол-во товара
    private int quantity_basket; // Кол-во товара в корзине

    public ProductBasket(int article, String title, String price, String brand, String image_url, String description, int quantity, int quantity_basket) {
        this.article = article;
        this.title = title;
        this.price = price;
        this.brand = brand;
        this.image_url = image_url;
        this.description = description;
        this.quantity = quantity;
        this.quantity_basket = quantity_basket;
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

    public int getQuantity() {
        return quantity;
    }

    public int getQuantity_basket() {
        return quantity_basket;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setQuantity_basket(int quantity_basket) {
        this.quantity_basket = quantity_basket;
    }
}
