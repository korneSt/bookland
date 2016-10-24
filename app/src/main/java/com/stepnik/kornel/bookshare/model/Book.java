package com.stepnik.kornel.bookshare.model;

import java.io.Serializable;

/**
 * Created by korSt on 20.10.2016.
 */

public class Book implements Serializable{
    private long id;
    private String title;
    private String year;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return year;
    }

    public void setAuthor(String author) {
        this.year = author;
    }
}
