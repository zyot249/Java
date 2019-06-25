package com.topica.zyot.shyn.annotation;

public class Book {
    public static final float discount = 0.25f;
    @MyNotNull
    private String title;
    @MyNotNull
    private String author;
    private Integer available;

    @MyNotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @MyNotNull
    public String getAuthor() {
        return author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
