package com.example.pa.finn.models;

/**
 * Created by pa on 24/6/17.
 */

public class User {
    String name, image;
    boolean male;
    int dobYears, dobMonth, dobDays;
    float weight, height, bmi;

    public User(String name, String image, boolean male, int dobYears, int dobMonth, int dobDays, float weight, float height, float bmi) {
        this.name = name;
        this.image = image;
        this.male = male;
        this.dobYears = dobYears;
        this.dobMonth = dobMonth;
        this.dobDays = dobDays;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public int getDobYears() {
        return dobYears;
    }

    public void setDobYears(int dobYears) {
        this.dobYears = dobYears;
    }

    public int getDobMonth() {
        return dobMonth;
    }

    public void setDobMonth(int dobMonth) {
        this.dobMonth = dobMonth;
    }

    public int getDobDays() {
        return dobDays;
    }

    public void setDobDays(int dobDays) {
        this.dobDays = dobDays;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }
}
