package com.tiy.web;

/**
 * Created by dbashizi on 1/4/17.
 */
public class User {
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private int id;

    public int getId() {
        return id;
    }

    public User(String userName, String firstName, String lastName, String password, int id) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
