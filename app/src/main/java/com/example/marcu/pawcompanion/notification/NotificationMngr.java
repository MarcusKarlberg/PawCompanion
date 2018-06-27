package com.example.marcu.pawcompanion.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.marcu.pawcompanion.data.Dog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

import static android.content.Context.ALARM_SERVICE;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;

/**
 * Created by marcu on 3/20/2018.
 */

public class NotificationMngr {

    private static final String TAG = "NotificationMngr";
    Context context;

    public NotificationMngr(Context context) {
        this.context = context;
    }

    public void setMealNotification(Dog dog){
        long timeBetweenMeals = TimeUnit.MINUTES.toMillis(dog.getIntervalMealTime());
        long firstMealTime = localTimeToMillis(dog.getFirstMealTime());
        Log.d(TAG, "Current Time: " + LocalTime.now());
        Intent intent = new Intent(context.getApplicationContext(), MealNotification.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dogData", dog);
        intent.putExtra("bundle", bundle);
        PendingIntent mealPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, firstMealTime, timeBetweenMeals, mealPendingIntent);
    }

    public void setWalkNotification(Dog dog){
        long timeBetweenWalks = TimeUnit.MINUTES.toMillis(dog.getIntervalWalkTime());
        long firstWalkTime = localTimeToMillis(dog.getFirstWalkTime());

        Intent intent = new Intent(context.getApplicationContext(), WalkNotification.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dogData", dog);
        intent.putExtra("bundle", bundle);
        PendingIntent walkPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 50,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, firstWalkTime, timeBetweenWalks, walkPendingIntent);
    }

    private long validateTime(long time){
        return 0;
    }

    private long localTimeToMillis(LocalTime time){
        LocalDate currentLocalDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(currentLocalDate, time);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        if(time.isBefore(LocalTime.now())){
            return zonedDateTime.plusDays(1).toInstant().toEpochMilli();
        }
        else{
            return zonedDateTime.toInstant().toEpochMilli();
        }
    }
}
