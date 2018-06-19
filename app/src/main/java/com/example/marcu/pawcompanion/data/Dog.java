package com.example.marcu.pawcompanion.data;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private double weight;
    private String food;
    private Double portionSize;
    private LocalTime firstMealTime;
    private LocalTime firstWalkTime;

    //Todo: Create logic for intervals - mock data now, 60 minute interval
    private int IntervalMealTime = 60;
    private int IntervalWalkTime = 60;

    private boolean isHighlighted;

    public Dog(String name, Breed breed, String birthDate, double weight, String firstMealTime, String firstWalkTime) {
        this.id = atomicCounter.incrementAndGet();
        this.name = name;
        this.breed = breed;
        this.weight = weight;

        setBirthDate(birthDate);
        setFirstMealTime(firstMealTime);
        setFirstWalkTime(firstWalkTime);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");
        this.birthDate = LocalDate.parse(birthDate, formatter);
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

    public int getIntervalMealTime() {
        return IntervalMealTime;
    }

    public int getIntervalWalkTime() {
        return IntervalWalkTime;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breed=" + breed +
                ", birthDate=" + birthDate +
                ", weight=" + weight +
                ", firstMealTime=" + firstMealTime +
                ", firstWalkTime=" + firstWalkTime +
                '}';
    }
}
