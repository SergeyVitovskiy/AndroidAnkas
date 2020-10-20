package com.example.ankas.Class;

public class Review {
    int id;
    int evalution;
    String review;

    public Review(int id, int evalution, String review) {
        this.id = id;
        this.evalution = evalution;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public int getEvalution() {
        return evalution;
    }

    public String getReview() {
        return review;
    }
}
