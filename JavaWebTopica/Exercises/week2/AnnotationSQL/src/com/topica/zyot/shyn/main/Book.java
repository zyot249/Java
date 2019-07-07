package com.topica.zyot.shyn.main;

import com.topica.zyot.shyn.annotation.Column;
import com.topica.zyot.shyn.annotation.Entity;

@Entity
public class Book {
    @Column(id = true)
    public String id;
    @Column
    public String title;
    @Column
    public String author;
    @Column
    public int available;

    public Book() {
    }

    public Book(String id, String title, String author, int available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public Book(String title, String author, int available) {
        this.title = title;
        this.author = author;
        this.available = available;
    }
}
