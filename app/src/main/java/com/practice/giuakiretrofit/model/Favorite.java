package com.practice.giuakiretrofit.model;

public class Favorite {
    private int id;

    private User user;
    private Food food;

    public Favorite(int id, User user, Food food) {
        this.id = id;
        this.user = user;
        this.food = food;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
