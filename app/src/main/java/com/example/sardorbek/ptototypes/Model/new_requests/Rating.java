package com.example.sardorbek.ptototypes.Model.new_requests;

/**
 * Created by sardorbek on 4/24/18.
 */

public class Rating {
    private String userId;
    private String bookId;
    private String rateValue;
    private String comment;


    public Rating() {
    }

    public Rating(String userId, String bookId, String rateValue, String comment) {
        this.userId = userId;
        this.bookId = bookId;
        this.rateValue = rateValue;
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

