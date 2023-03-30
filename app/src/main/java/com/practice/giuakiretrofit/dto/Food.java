package com.practice.giuakiretrofit.dto;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Food {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    @SerializedName("protein")
    private double protein;

    @SerializedName("fat")
    private double fat;

    @SerializedName("carb")
    private double carb;

    @SerializedName("energyPerServing")
    private double energyPerServing;

    @SerializedName("category")
    private Category category;

    public Food(int id, String name, String description, String image, double protein, double fat, double carb, double energyPerServing, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
        this.energyPerServing = energyPerServing;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarb() {
        return carb;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }

    public double getEnergyPerServing() {
        return energyPerServing;
    }

    public void setEnergyPerServing(double energyPerServing) {
        this.energyPerServing = energyPerServing;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
