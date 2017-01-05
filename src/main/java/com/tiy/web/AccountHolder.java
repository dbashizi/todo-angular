package com.tiy.web;

import java.time.LocalDateTime;

/**
 * Created by dbashizi on 1/5/17.
 */
public class AccountHolder {
    private String fullName;
    private LocalDateTime dateJoined;
    private boolean active;

    public AccountHolder(String fullName, LocalDateTime dateJoined, boolean active) {
        this.fullName = fullName;
        this.dateJoined = dateJoined;
        this.active = active;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDateTime getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDateTime dateJoined) {
        this.dateJoined = dateJoined;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
