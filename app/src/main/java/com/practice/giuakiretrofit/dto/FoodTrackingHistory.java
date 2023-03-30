package com.practice.giuakiretrofit.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FoodTrackingHistory {
    private Food food;
    private Double consumedGram;
    private Double consumedCalories;
    private String consumedDatetime;

    public FoodTrackingHistory(Food food, Double consumedGram, Double consumedCalories, String consumedDatetime) {
        this.food = food;
        this.consumedGram = consumedGram;
        this.consumedCalories = consumedCalories;
        this.consumedDatetime = consumedDatetime;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Double getConsumedGram() {
        return consumedGram;
    }

    public void setConsumedGram(Double consumedGram) {
        this.consumedGram = consumedGram;
    }

    public Double getConsumedCalories() {
        return consumedCalories;
    }

    public void setConsumedCalories(Double consumedCalories) {
        this.consumedCalories = consumedCalories;
    }

    public String getConsumedDatetime() {
        return consumedDatetime;
    }

    public void setConsumedDatetime(String consumedDatetime) {
        this.consumedDatetime = consumedDatetime;
    }
}
