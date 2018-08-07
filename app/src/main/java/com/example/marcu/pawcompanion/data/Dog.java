package com.example.marcu.pawcompanion.data;

import com.example.marcu.pawcompanion.utility.DogCalculator;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by marcu on 3/14/2018.
 */

public final class Dog implements Serializable{

    private static final AtomicLong atomicCounter = new AtomicLong(0);
    private Long id;
    private String name;
    private Breed breed;
    private LocalDate birthDate;
    private double weightInKgs;
    private String food;
    private Double portionSizeInGrams;
    private LocalTime firstMealTime;
    private LocalTime firstWalkTime;
    private String imageUriString;
    private boolean isSelected;

    public Dog(String name, Breed breed, String birthDate, double weight, String firstMealTime, String firstWalkTime) {
        this.id = atomicCounter.incrementAndGet();
        this.name = name;
        this.breed = breed;
        this.weightInKgs = weight;

        setFirstMealTime(firstMealTime);
        setFirstWalkTime(firstWalkTime);
        setBirthDate(birthDate);
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getImageUriString() {
        return imageUriString;
    }

    public void setImageUriString(String imageUriString) {
        this.imageUriString = imageUriString;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getFirstMealTime(){
        return  firstMealTime;
    }

    public void setFirstMealTime(String time) {
        this.firstMealTime = LocalTime.parse(time);
    }

    public LocalTime getFirstWalkTime(){
        return firstWalkTime;
    }

    public void setFirstWalkTime(String time) {
        this.firstWalkTime = LocalTime.parse(time);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public  LocalDate getBirthDate(){
            return birthDate;
    }

    public void setBirthDate(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
        this.birthDate = LocalDate.parse(birthDate, formatter);
    }

    public double getWeight() {
        return weightInKgs;
    }

    public void setWeight(double weight) {
        this.weightInKgs = weight;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Double getPortionSize() {
        return portionSizeInGrams;
    }

    public void setPortionSize(Double portionSize) {
        this.portionSizeInGrams = portionSize;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breed=" + breed +
                ", birthDate=" + birthDate +
                ", weight=" + weightInKgs +
                ", firstMealTime=" + firstMealTime +
                ", firstWalkTime=" + firstWalkTime +
                ", imageUriString='" + imageUriString + '\'' +
                '}';
    }
}
