package com.example.marcu.pawcompanion.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.marcu.pawcompanion.data.Dog;

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
        //Todo: interval time based on dogs age
        long intervalBetweenMeals = TimeUnit.MINUTES.toMillis(dog.getIntervalMealTime());

        Intent mealIntent = new Intent(context.getApplicationContext(), MealNotification.class);
        PendingIntent mealPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 100, mealIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dog.getFirstMealTime().get(MILLI_OF_SECOND), intervalBetweenMeals, mealPendingIntent);
        Log.d(TAG, "setNotifications: MEAL ALARM SET!");
    }

    public void setWalkNotification(Dog dog){

        long intervalBetweenWalks = TimeUnit.MINUTES.toMillis(dog.getIntervalWalkTime());

        Intent walkIntent = new Intent(context.getApplicationContext(), WalkNotification.class);
        PendingIntent walkPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 50,walkIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //getSystemService(String) anvands till en AlarmManager for att returnera en intent under en spesefik tid som anvandaren givit
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dog.getFirstWalkTime().get(MILLI_OF_SECOND), intervalBetweenWalks, walkPendingIntent);
        Log.d(TAG, "setNotifications: WALK ALARM SET FOR:" + dog.getName());
    }

}
