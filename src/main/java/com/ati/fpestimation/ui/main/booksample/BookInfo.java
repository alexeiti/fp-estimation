package com.ati.fpestimation.ui.main.booksample;

import java.util.Date;

public class BookInfo {

    private String title;
    private String author;
    private BookLender currentLender;
    private Date purchaseDate;
    private int numberOfPages;
    private float rating;
    private int internalId;
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

    public void setCurrentLender(BookLender currentLender) {
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    private class BookLender {
        String firstName;
        String lastName;
        Date lendDate;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Date getLendDate() {
            return lendDate;
        }

        public void setLendDate(Date lendDate) {
            this.lendDate = lendDate;
        }
    }
}
