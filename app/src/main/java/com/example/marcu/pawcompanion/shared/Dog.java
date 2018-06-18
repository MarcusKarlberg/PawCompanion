package com.example.marcu.pawcompanion.shared;
import java.io.Serializable;
import java.util.*;

/**
 * Created by marcu on 3/14/2018.
 */

public class Dog implements Serializable{

    private String name;
    private String breed;
    private Date bDate;
    private int ageInMonths;
    private double weight;
    private String food;
    private Double portionSize;

    private boolean isHighlighted;

    public Dog() {
    }

    public Dog(String name) {
        this.name = name;
        //Todo: delete when mockdata in dogrepo is deleted
    }

    public Dog(String name, double weight) {
        this.name = name;
        //Todo:
        //this.bDate = bDate;
        //this.breed = breed;
        this.weight = weight;
    }

    public int getAgeInMonths() {
        return ageInMonths;
    }

    public void setAgeInMonths(int ageInMonths) {
        this.ageInMonths = ageInMonths;
    }

    public boolean getIsHighlighted() {
        return isHighlighted;
    }

    public void setIsHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Double getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(Double portionSize) {
        this.portionSize = portionSize;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }
}
