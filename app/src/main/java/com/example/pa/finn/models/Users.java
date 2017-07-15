package com.example.pa.finn.models;

/**
 * Created by root on 16/7/17.
 */

public class Users {
    User[] user;

    public User[] getUser() {
        return user;
    }

    public void setUser(User[] user) {
        this.user = user;
    }

    public Users() {

    }

    public Users(User[] user) {

        this.user = user;
    }
}
