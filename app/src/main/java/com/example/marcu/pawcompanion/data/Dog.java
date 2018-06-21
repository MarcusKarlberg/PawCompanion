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

    //In kilometers
    private double walkingDistancePerDay;
    //In minutes
    private double walkingDurationPerDay;

    //In minutes
    private int intervalMealTime = 10;
    private int intervalWalkTime;

    private boolean isHighlighted;

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
            this.intervalWalkTime = 480;
        }

        if(activityLevel > 3){
            //Todo: activity level should affect the intervalWalkTime
        }
    }

    public void setWalkingDistancePerDay(int activityLevel){
        int ageInMonths = getAgeInMonths(this.birthDate);

        if(activityLevel == 5){
            this.walkingDistancePerDay = 0.5 * ageInMonths;
        }
        else if(activityLevel == 4){
            this.walkingDistancePerDay = 0.42 * ageInMonths;
        }
        else if(activityLevel == 3){
            this.walkingDistancePerDay = 0.33 * ageInMonths;
        }
        else if(activityLevel == 2){
            this.walkingDistancePerDay = 0.25 * ageInMonths;
        }
        else {
            this.walkingDistancePerDay = 0.2 * ageInMonths;
        }
    }

    public void setWalkingDurationPerDay(int activityLevel){
        int ageInMonths = getAgeInMonths(this.birthDate);

        if(activityLevel == 5){
            this.walkingDurationPerDay = 5 * ageInMonths;
        }
        else if(activityLevel == 4){
            this.walkingDurationPerDay = 4.2 * ageInMonths;
        }
        else if(activityLevel == 3){
            this.walkingDurationPerDay = 3.3 * ageInMonths;
        }
        else if(activityLevel == 2){
            this.walkingDurationPerDay = 2.5 * ageInMonths;
        }
        else {
            this.walkingDurationPerDay = 2 * ageInMonths;
        }
    }

    private int getAgeInMonths(LocalDate birthDate){
        LocalDate currentDate = LocalDate.now();
        long diff = Duration.between(birthDate.atStartOfDay(), currentDate.atStartOfDay()).toMillis();
        int ageInMonths = (int) (diff/2592000000L);

        return ageInMonths;
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
