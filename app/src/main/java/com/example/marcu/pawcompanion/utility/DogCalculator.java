package com.example.marcu.pawcompanion.utility;

import com.example.marcu.pawcompanion.data.Dog;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.Months;

public class DogCalculator {

    public static double getDistancePerWalk(Dog dog) {
        double distancePerDay = getWalkingDistancePerDayInKm(dog.getBirthDate(), dog.getBreed().getActivityLevel());
        int intervalWalkTime = getIntervalWalkTimeInMins(dog.getBirthDate());
        int numberOfWalksPerDay;

        switch (intervalWalkTime){
            case 120:
                numberOfWalksPerDay = 7;
            break;
            case 180:
                numberOfWalksPerDay = 7;
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
            default: numberOfWalksPerDay = 2;
        }
        return distancePerDay/numberOfWalksPerDay;
    }

    public static int getDurationPerWalk(Dog dog){
        double durationPerDay = getWalkingDurationPerDayInMins(dog.getBirthDate(), dog.getBreed().getActivityLevel());
        int intervalWalkTime = getIntervalWalkTimeInMins(dog.getBirthDate());
        int numberOfWalksPerDay;

        switch (intervalWalkTime){
            case 120:
                numberOfWalksPerDay = 7;
            break;
            case 180:
                numberOfWalksPerDay = 7;
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
                numberOfWalksPerDay = 2;
        }

        return (int) Math.round(Math.abs(durationPerDay/numberOfWalksPerDay));
    }

    public static int getIntervalMealTimeInMins(LocalDate birthday, LocalTime firstMealTime, LocalTime firstWalkTime){
        int intervalMealTimeInMins = 0;
        int ageInMonths = getAgeInMonths(birthday);

        LocalTime latestMealTime = firstWalkTime.minusHours(10);

        if(ageInMonths <= 2){
            intervalMealTimeInMins = 240;
        }
        else if(ageInMonths > 2 && ageInMonths <= 4){
            intervalMealTimeInMins = 360;
        }
        else {
            LocalTime nextMealTime = firstMealTime.plusHours(12);

            if(nextMealTime.isAfter(latestMealTime)){
                nextMealTime = latestMealTime;
            }

            intervalMealTimeInMins = Minutes.minutesBetween(firstMealTime, nextMealTime).getMinutes();
        }

        return intervalMealTimeInMins;
    }

    public static int getIntervalWalkTimeInMins(LocalDate birthday){
        int intervalWalkTimeInMins;
        int ageInMonths = getAgeInMonths(birthday);

        // puppy - break every one to two hours
        if(ageInMonths <= 2){
            intervalWalkTimeInMins = 120;
        }

        //can hold it for as many hours as she is months old
        else if(ageInMonths > 2 && ageInMonths < 5){
            intervalWalkTimeInMins = ageInMonths * 60;
        }

        //can hold it for as many hours as she is months old, plus one
        else if((ageInMonths > 4) && (ageInMonths < 7)){
            intervalWalkTimeInMins = 60 * ageInMonths + 60;
        }
        else {
            //a dog should not have to hold it longer than 8 hours
            intervalWalkTimeInMins = 480;
        }
        return intervalWalkTimeInMins;
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

    private static int getAgeInMonths(LocalDate birthDate){
        LocalDate currentDate = LocalDate.now();
        return Months.monthsBetween(birthDate.withDayOfMonth(birthDate.getDayOfMonth()),
                currentDate.withDayOfMonth(currentDate.getDayOfMonth())).getMonths()+1;
    }
}
