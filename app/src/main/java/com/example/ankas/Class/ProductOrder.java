package com.example.ankas.Class;

public class ProductOrder {
    int article;
    String title_product;
    int price_product;
    int quantity;
    String comment;
    String status;
    String image_url;

    public ProductOrder(int article, String title_product, int price_product, int quantity, String comment, String status, String image_url) {
        this.article = article;
        this.title_product = title_product;
        this.price_product = price_product;
        this.quantity = quantity;
        this.comment = comment;
        this.status = status;
        this.image_url = image_url;
    }

    public int getArticle() {
        return article;
    }

    public String getTitle_product() {
        return title_product;
    }

    public int getPrice_product() {
        return price_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getComment() {
        return comment;
    }

    public String getStatus() {
        return status;
    }

    public String getImage_url() {
        return image_url;
    }
}
