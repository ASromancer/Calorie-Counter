package com.practice.giuakiretrofit.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FoodTrackingHistory {
    private int id;
    private float consumedCalories;
    private float consumedGram;
    private String consumedDatetime;
    private Food food;

    public FoodTrackingHistory(int id, float consumedCalories, float consumedGram, String consumedDatetime, Food food) {
        this.id = id;
        this.consumedCalories = consumedCalories;
        this.consumedGram = consumedGram;
        this.consumedDatetime = consumedDatetime;
        this.food = food;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getConsumedCalories() {
        return consumedCalories;
    }

    public void setConsumedCalories(float consumedCalories) {
        this.consumedCalories = consumedCalories;
    }

    public float getConsumedGram() {
        return consumedGram;
    }

    public void setConsumedGram(float consumedGram) {
        this.consumedGram = consumedGram;
    }

    public String getConsumedDatetime() {
        return consumedDatetime;
    }

    public void setConsumedDatetime(String consumedDatetime) {
        this.consumedDatetime = consumedDatetime;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
    
}
