package com.example.ankas.Class;

public class Review {
    int id_product;
    int evalution;
    String review;

    public Review(int id_product, int evalution, String review) {
        this.id_product = id_product;
        this.evalution = evalution;
        this.review = review;
    }

    public int getId_product() {
        return id_product;
    }

    public int getEvalution() {
        return evalution;
    }

    public String getReview() {
        return review;
    }
}
