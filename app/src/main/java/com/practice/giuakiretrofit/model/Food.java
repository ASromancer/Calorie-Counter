package com.practice.giuakiretrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Food implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("protein")
    @Expose
    private double protein;

    @SerializedName("fat")
    @Expose
    private double fat;

    @SerializedName("carb")
    @Expose
    private double carb;

    @SerializedName("energyPerServing")
    private double energyPerServing;

    public Food() {
    }

    public Food(int id, String name, String description, String image, double protein, double fat, double carb, double energyPerServing) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
        this.energyPerServing = energyPerServing;
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
}
