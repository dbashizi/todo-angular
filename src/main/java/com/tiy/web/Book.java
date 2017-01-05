package com.tiy.web;

import java.time.LocalDateTime;

/**
 * Created by dbashizi on 1/5/17.
 */
public class Book {
    private boolean checkedOut;
    private LocalDateTime dueDate;
    private String title;
    private String author;
    private String genre;
    private User user;

    public Book(boolean checkedOut, LocalDateTime dueDate, String title, String author, String genre, User user) {
        this.checkedOut = checkedOut;
        this.dueDate = dueDate;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    private LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
