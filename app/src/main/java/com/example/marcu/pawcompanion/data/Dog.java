package com.example.marcu.pawcompanion.data;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
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
    private double weight;
    private String food;
    private Double portionSize;
    private LocalTime firstMealTime;
    private LocalTime firstWalkTime;
    private String imageUriString;

    //In kilometers
    private double walkingDistancePerDay;
    //In minutes
    private double walkingDurationPerDay;

    //In minutes
    private int intervalMealTime = 10;
    private int intervalWalkTime;

    public Dog(String name, Breed breed, String birthDate, double weight, String firstMealTime, String firstWalkTime) {
        this.id = atomicCounter.incrementAndGet();
        this.name = name;
        this.breed = breed;
        this.weight = weight;

        setBirthDate(birthDate);
        setWalkingDurationPerDay(breed.getActivityLevel());
        setWalkingDistancePerDay(breed.getActivityLevel());
        setIntervalMealTime();
        setIntervalWalkTime(breed.getActivityLevel());
        setFirstMealTime(firstMealTime);
        setFirstWalkTime(firstWalkTime);
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

    //Todo: intervals - should be in minutes. 60min would be 1h interval between reminder

    public void setIntervalMealTime(){
        int ageInMonths = getAgeInMonths(this.birthDate);

//        if(ageInMonths > 6){
//            this.intervalMealTime = 2;
//        }else{
//            this.intervalMealTime = 3;
//        }
    }

    public void setIntervalWalkTime(int activityLevel){
        /*Two months old puppy can hold his bladder for up to 3 hours.
        Time increases an hour per month of age.
        6 months of age he will be able to hold his bladder for 7-8 hours (a work day).
        No dog of any age should be made to wait longer than 8 hours!*/

        int ageInMonths = getAgeInMonths(this.birthDate);

        if(ageInMonths <= 2){
            this.intervalWalkTime = 180;
        }
        else if((ageInMonths >= 3) && (ageInMonths <= 6)){
             this.intervalWalkTime = 180 + (60 * (ageInMonths - 3));
        }
        else {
            this.intervalWalkTime = 420;
        }
    }

    public void setWalkingDistancePerDay(int activityLevel){
        int ageInMonths = getAgeInMonths(this.birthDate);
        double distance;

        if(activityLevel == 5){
            distance = 0.5 * ageInMonths;
            this.walkingDistancePerDay = distance <= 6 ? distance : 6;
        }
        else if(activityLevel == 4){
            distance = 0.42 * ageInMonths;
            this.walkingDistancePerDay = distance <= 5 ? distance : 5;
        }
        else if(activityLevel == 3){
            distance = 0.33 * ageInMonths;
            this.walkingDistancePerDay = distance <= 4 ? distance : 4;
        }
        else if(activityLevel == 2){
            distance = 0.25 * ageInMonths;
            this.walkingDistancePerDay = distance <= 3 ? distance : 3;
        }
        else {
            distance = 0.2 * ageInMonths;
            this.walkingDistancePerDay = distance <= 2.4 ? distance : 2.4;
        }
    }

    public void setWalkingDurationPerDay(int activityLevel){
        int ageInMonths = getAgeInMonths(this.birthDate);
        double duration;

        if(activityLevel == 5){
            duration = 5 * ageInMonths;
            this.walkingDurationPerDay = duration <= 60 ? duration : 60;
        }
        else if(activityLevel == 4){
            duration = 4.2 * ageInMonths;
            this.walkingDurationPerDay = duration <= 50 ? duration : 50;
        }
        else if(activityLevel == 3){
            duration = 3.3 * ageInMonths;
            this.walkingDurationPerDay = duration <= 40 ? duration : 40;
        }
        else if(activityLevel == 2){
            duration = 2.5 * ageInMonths;
            this.walkingDurationPerDay = duration <= 30 ? duration : 30;
        }
        else {
            duration = 2 * ageInMonths;
            this.walkingDurationPerDay = duration <= 24 ? duration : 24;
        }
    }

    private int getAgeInMonths(LocalDate birthDate){
        LocalDate currentDate = LocalDate.now();
        //long diff =  Duration.between(birthDate.atStartOfDay(), currentDate.atStartOfDay()).toMillis();
        //int ageInMonths = (int) (diff/2592000000L);
        //return ageInMonths;
        return Period.fieldDifference(birthDate, currentDate).getMonths();
    }

    public double getWalkingDistancePerDay() {
        return walkingDistancePerDay;
    }

    public double getWalkingDurationPerDay() {
        return walkingDurationPerDay;
    }

    public int getIntervalMealTime() {
        return intervalMealTime;
    }

    public int getIntervalWalkTime() {
        return intervalWalkTime;
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
