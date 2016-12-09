package com.stepnik.kornel.bookshare.models;

import java.io.Serializable;

/**
 * Created by korSt on 20.10.2016.
 */

public class Book implements Serializable{
    private long id;
    private long ownerId;
    private long userId;
    private String title;
    private String author;
    private int year;
    private double localLat;
    private double localLon;
    private int isbn;
    private String coverLink;
    int condition;

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getCover() {
        return coverLink;
    }

    public void setCover(String cover) {
        this.coverLink = cover;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public String getImagePath() {
        return coverLink;
    }

    public void setImagePath(String imagePath) {
        this.coverLink = imagePath;
    }

    public double getLocalLat() {
        return localLat;
    }

    public void setLocalLat(double localLat) {
        this.localLat = localLat;
    }

    public double getLocalLon() {
        return localLon;
    }

    public void setLocalLon(double localLon) {
        this.localLon = localLon;
    }

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
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Book() {

    }
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
