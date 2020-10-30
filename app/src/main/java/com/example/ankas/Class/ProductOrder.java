package com.example.ankas.Class;

public class ProductOrder {
    int id;
    int article;
    String title_product;
    String image_url;
    int quantity;
    int price_product;
    String status;
    String comment;

    public ProductOrder(int id, int article, String title_product, String image_url, int quantity, int price_product, String status, String comment) {
        this.id = id;
        this.article = article;
        this.title_product = title_product;
        this.image_url = image_url;
        this.quantity = quantity;
        this.price_product = price_product;
        this.status = status;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public int getArticle() {
        return article;
    }

    public String getTitle_product() {
        return title_product;
    }

    public String getImage_url() {
        return image_url;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice_product() {
        return price_product;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }
}
