package com.example.ankas.Class;

public class ProductOrderAdmin {
    String title_product;
    int price_product;
    int quantity;
    String name;
    String surname;
    String user_tel;
    String user_email;
    String status;

    public ProductOrderAdmin(String title_product, int price_product, int quantity, String name, String surname, String user_tel, String user_email, String status) {
        this.title_product = title_product;
        this.price_product = price_product;
        this.quantity = quantity;
        this.name = name;
        this.surname = surname;
        this.user_tel = user_tel;
        this.user_email = user_email;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getStatus() {
        return status;
    }
}
