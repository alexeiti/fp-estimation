package com.ati.booklibrary.domain;

import com.ati.common.domain.EmployeeInfo;

import java.util.Date;

public class BookInfo {

    private int internalId;
    private String title;
    private String author;
    private EmployeeInfo currentLender;
    private Date purchaseDate;
    private int numberOfPages;

    private String isbn;

    public int getInternalId() {
        return internalId;
    }

    public BookInfo(int internalId) {

        this.internalId = internalId;
    }

    public boolean isAvailable() {
        return currentLender == null;
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

    public Object getCurrentLender() {
        return currentLender;
    }

    public void setCurrentLender(EmployeeInfo currentLender) {
        this.currentLender = currentLender;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
