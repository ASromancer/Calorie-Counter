package com.practice.giuakiretrofit.dto;

public class UserAvatar {


    private String lastName;
    private String firstName;
    private String email;
    private int id;

    public UserAvatar(String lastName, String firstName, String email, int id) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
