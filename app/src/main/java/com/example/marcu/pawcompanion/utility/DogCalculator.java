package com.example.marcu.pawcompanion.utility;

import com.example.marcu.pawcompanion.data.Dog;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.Months;

import java.util.ArrayList;

public class DogCalculator {

    public static String getNextWalkTimeString(Dog dog){
        LocalTime firstWalkTime = dog.getFirstWalkTime();
        int timeIntervalBetweenWalksInMins = getIntervalWalkTimeInMins(dog.getBirthDate(), dog.getBreed().getSizeLevel());
        int numberOfWalksPerDay = getNumberOfWalksPerDay(timeIntervalBetweenWalksInMins);

        ArrayList<LocalTime> walkTimes = new ArrayList<>();
        for(int i = 0; i < numberOfWalksPerDay; i++){
            walkTimes.add(new LocalTime(firstWalkTime.plusMinutes(timeIntervalBetweenWalksInMins*i)));
        }

        return findNextTimeUnitString(walkTimes, firstWalkTime);
    }
    public static LocalTime getNextWalkTime(Dog dog){
        LocalTime firstWalkTime = dog.getFirstWalkTime();
        int timeIntervalBetweenWalksInMins = getIntervalWalkTimeInMins(dog.getBirthDate(), dog.getBreed().getSizeLevel());
        int numberOfWalksPerDay = getNumberOfWalksPerDay(timeIntervalBetweenWalksInMins);

        ArrayList<LocalTime> walkTimes = new ArrayList<>();
        for(int i = 0; i < numberOfWalksPerDay; i++){
            walkTimes.add(new LocalTime(firstWalkTime.plusMinutes(timeIntervalBetweenWalksInMins*i)));
        }

        return findNextTimeUnit(walkTimes, firstWalkTime);
    }

    public static double getDailyPortionInGrams(Dog dog){
        double weightInKg = dog.getWeight();
        int ageInMonths = getAgeInMonths(dog.getBirthDate());
        double portion = 0;

        if(ageInMonths < 4){
           if(weightInKg < 4){
               portion = weightInKg * 45;
           }else {
               portion = weightInKg * 39;
           }
        }

        else if(ageInMonths < 10){
            if(weightInKg < 6){
                portion = weightInKg * 32;
            }else if(weightInKg < 11){
                portion = weightInKg * 27.5;
            }else {
                portion = weightInKg * 23;
            }
        }

        else if(ageInMonths < 13){
            if(weightInKg < 6){
                portion = weightInKg * 26;
            }else if(weightInKg < 11){
                portion = weightInKg * 22;
            }else if (weightInKg < 16){
                portion = weightInKg * 19.6;
            }else {
                portion = weightInKg * 17.4;
            }
        }

        else {
            if(weightInKg < 3){
                portion = weightInKg * 35;
            } else if(weightInKg < 6){
                portion = weightInKg * 21;
            } else if(weightInKg < 11){
                portion = weightInKg * 18;
            }else if(weightInKg < 31){
                portion = weightInKg * 13;
            }else {
                portion = weightInKg * 11;
            }
        }
        return portion;
    }

    public static String getNextMealTimeString(Dog dog){
        LocalTime firstMealTime = dog.getFirstMealTime();
        int timeIntervalBetweenMealsInMins = getIntervalMealTimeInMins(dog);
        int numberOfMealsPerDay = getNumberOfMealsPerDay(dog);
        ArrayList<LocalTime> mealTimes = new ArrayList<>();

        for(int i = 0; i < numberOfMealsPerDay; i++){
            mealTimes.add(new LocalTime(firstMealTime.plusMinutes(timeIntervalBetweenMealsInMins*i)));
        }

        return findNextTimeUnitString(mealTimes, dog.getFirstMealTime());
    }

    public static LocalTime getNextMealTime(Dog dog){
        LocalTime firstMealTime = dog.getFirstMealTime();
        int timeIntervalBetweenMealsInMins = getIntervalMealTimeInMins(dog);
        int numberOfMealsPerDay = getNumberOfMealsPerDay(dog);
        ArrayList<LocalTime> mealTimes = new ArrayList<>();

        for(int i = 0; i < numberOfMealsPerDay; i++){
            mealTimes.add(new LocalTime(firstMealTime.plusMinutes(timeIntervalBetweenMealsInMins*i)));
        }

        return findNextTimeUnit(mealTimes, dog.getFirstMealTime());
    }

    public static double getDistancePerWalk(Dog dog) {
        double distancePerDay = getWalkingDistancePerDayInKm(dog.getBirthDate(), dog.getBreed().getActivityLevel());
        int intervalWalkTime = getIntervalWalkTimeInMins(dog.getBirthDate(), dog.getBreed().getSizeLevel());
        int numberOfWalksPerDay = getNumberOfWalksPerDay(intervalWalkTime);

        return distancePerDay/numberOfWalksPerDay;
    }

    public static int getDurationPerWalk(Dog dog){
        double durationPerDay = getWalkingDurationPerDayInMins(dog.getBirthDate(), dog.getBreed().getActivityLevel());
        int intervalWalkTime = getIntervalWalkTimeInMins(dog.getBirthDate(), dog.getBreed().getSizeLevel());
        int numberOfWalksPerDay = getNumberOfWalksPerDay(intervalWalkTime);

        return (int) Math.round(Math.abs(durationPerDay/numberOfWalksPerDay));
    }

    public static int getIntervalMealTimeInMins(Dog dog){
        LocalTime firstMealTime = dog.getFirstMealTime();
        LocalTime firstWalkTime = dog.getFirstWalkTime();

        int intervalMealTimeInMins;
        int ageInMonths = getAgeInMonths(dog.getBirthDate());

        LocalTime latestMealTime = firstWalkTime.minusHours(8);

        if(ageInMonths <= 2){
            intervalMealTimeInMins = 240;
        }
        else if(ageInMonths < 6){
            intervalMealTimeInMins = 360;
        }
        else {
            LocalTime nextMealTime = firstMealTime.plusHours(11);

            if(nextMealTime.isAfter(latestMealTime)){
                nextMealTime = latestMealTime;
            }

            intervalMealTimeInMins = Minutes.minutesBetween(firstMealTime, nextMealTime).getMinutes();
        }

        return intervalMealTimeInMins;
    }

    public static int getIntervalWalkTimeInMins(LocalDate birthday, int size){
        int intervalWalkTimeInMins;
        int ageInMonths = getAgeInMonths(birthday);

        if(ageInMonths < 3){
            intervalWalkTimeInMins = 120;
        }
        else if(ageInMonths < 6){
            intervalWalkTimeInMins = ageInMonths * 60;
        }

        else {
            if(size < 3){
                intervalWalkTimeInMins = 300;
            }
            else {
                intervalWalkTimeInMins = 360;
            }
        }

        return intervalWalkTimeInMins;
    }

    public static boolean isAdult(Dog dog){
        int size = dog.getBreed().getSizeLevel();
        int ageInMonths = getAgeInMonths(dog.getBirthDate());

        if(size < 3) return ageInMonths >= 14;
        else if(size == 3) return ageInMonths >= 17;
        else return ageInMonths >= 23;
    }

    private static String findNextTimeUnitString(ArrayList<LocalTime> times, LocalTime firstTimeUnit){
        LocalTime currentTime = LocalTime.now();
        String nextTime = null;

        for(LocalTime t : times){
            if(t.isAfter(currentTime)){
                nextTime = t.toString("HH:mm");
                break;
            }
            else if(currentTime.isAfter(t.minusMinutes(1)) && currentTime.isBefore(t.plusMinutes(11))){
                nextTime = "now";
                break;
            }
        }
        if(nextTime == null){
            nextTime = firstTimeUnit.toString("HH:mm");
        }

        return nextTime;
    }

    private static LocalTime findNextTimeUnit(ArrayList<LocalTime> times, LocalTime firstTimeUnit){
        LocalTime currentTime = LocalTime.now();
        LocalTime nextTime = null;

        for(LocalTime t : times){
            if(t.isAfter(currentTime)){
                nextTime = t;
                break;
            }
        }
        if(nextTime == null){
            nextTime = firstTimeUnit;
        }

        return nextTime;
    }

    private static int getNumberOfWalksPerDay(int timeIntervalBetweenWalksInMins){
        int numberOfWalksPerDay;

        switch (timeIntervalBetweenWalksInMins){
            case 120:
                numberOfWalksPerDay = 7;
                break;
            case 180:
                numberOfWalksPerDay = 6;
                break;
            case 240:
                numberOfWalksPerDay = 5;
                break;
            case 300:
                numberOfWalksPerDay = 4;
                break;
            case 360:
                numberOfWalksPerDay = 3;
                break;
            default:
                numberOfWalksPerDay = 3;
        }
        return numberOfWalksPerDay;
    }

    public static int getNumberOfMealsPerDay(Dog dog){
        int timeIntervalBetweenMealsInMins = getIntervalMealTimeInMins(dog);
        int numberOfMealsPerDay;

        switch (timeIntervalBetweenMealsInMins){
            case 240:
                numberOfMealsPerDay = 4;
            break;
            case 360:
                numberOfMealsPerDay = 3;
            break;
            default:
                numberOfMealsPerDay = 2;
        }

        return numberOfMealsPerDay;
    }

    private static double getWalkingDurationPerDayInMins(LocalDate birthday, int activityLevel){
        double duration;
        double walkingDurationPerDayInMins = 0;
        int ageInMonths = getAgeInMonths(birthday);

        if(ageInMonths < 5 || activityLevel < 2){
            walkingDurationPerDayInMins = 35;
        }
        else {
            if(activityLevel == 5){
                duration = 5.83 * ageInMonths;
                walkingDurationPerDayInMins = duration <= 70 ? duration : 70;
            }
            else if(activityLevel == 4){
                duration = 5 * ageInMonths;
                walkingDurationPerDayInMins = duration <= 60 ? duration : 60;
            }
            else if(activityLevel == 3){
                duration = 4.16 * ageInMonths;
                walkingDurationPerDayInMins = duration <= 50 ? duration : 50;
            }
            else if(activityLevel == 2){
                walkingDurationPerDayInMins = 40;
            }
        }

        if(walkingDurationPerDayInMins < 35){
            walkingDurationPerDayInMins = 35;
        }

        return walkingDurationPerDayInMins;
    }

    private static double getWalkingDistancePerDayInKm(LocalDate birthday, int activityLevel){
        double distance;
        double walkingDistancePerDayInKm = 0;
        int ageInMonths = getAgeInMonths(birthday);

        if(ageInMonths < 5 || activityLevel < 2){
            walkingDistancePerDayInKm = 3.5;
        }
        else {

            if(activityLevel == 5){
                distance = 0.58 * ageInMonths;
                walkingDistancePerDayInKm = distance <= 7 ? distance : 7;
            }
            else if(activityLevel == 4){
                distance = 0.5 * ageInMonths;
                walkingDistancePerDayInKm = distance <= 6 ? distance : 6;
            }
            else if(activityLevel == 3){
                distance = 0.41 * ageInMonths;
                walkingDistancePerDayInKm = distance <= 5 ? distance : 5;
            }
            else if(activityLevel == 2){
                walkingDistancePerDayInKm = 4;
            }
        }

        if(walkingDistancePerDayInKm < 3.5){
            walkingDistancePerDayInKm = 3.5;
        }

        return walkingDistancePerDayInKm;
    }

    public static LocalTime getLastWalkTime(Dog dog){
        LocalTime firstWalkTime = dog.getFirstWalkTime();
        int timeIntervalBetweenWalksInMins = getIntervalWalkTimeInMins(dog.getBirthDate(), dog.getBreed().getSizeLevel());
        int numberOfWalksPerDay = getNumberOfWalksPerDay(timeIntervalBetweenWalksInMins);

        ArrayList<LocalTime> walkTimes = new ArrayList<>();
        for(int i = 0; i < numberOfWalksPerDay; i++){
            walkTimes.add(new LocalTime(firstWalkTime.plusMinutes(timeIntervalBetweenWalksInMins*i)));
        }

        return walkTimes.get(walkTimes.size() -1);
    }

    private static int getAgeInMonths(LocalDate birthDate){
        LocalDate currentDate = LocalDate.now();
        return Months.monthsBetween(birthDate.withDayOfMonth(birthDate.getDayOfMonth()),
                currentDate.withDayOfMonth(currentDate.getDayOfMonth())).getMonths();
    }
}
