package com.alexmita.ordermanagementsystem.domain.user;

public class User {
    private final Integer id;
    private final String username;
    private final String password;

    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
