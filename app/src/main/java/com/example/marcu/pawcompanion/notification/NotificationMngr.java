package com.example.marcu.pawcompanion.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        long timeBetweenMeals = TimeUnit.MINUTES.toMillis(dog.getIntervalMealTime());
        long firstMealTime = dog.getFirstMealTime().get(MILLI_OF_SECOND);

        Intent intent = new Intent(context.getApplicationContext(), MealNotification.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dogData", dog);
        intent.putExtra("bundle", bundle);
        PendingIntent mealPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, firstMealTime, timeBetweenMeals, mealPendingIntent);

        Log.d(TAG, "Meal alarm set for: " + dog.getFirstMealTime());
        Log.d(TAG, "Time between meals is: " + dog.getIntervalMealTime() + " minutes");
    }

    public void setWalkNotification(Dog dog){
        long timeBetweenWalks = TimeUnit.MINUTES.toMillis(dog.getIntervalWalkTime());
        long firstWalkTime = dog.getFirstWalkTime().get(MILLI_OF_SECOND);

        Intent intent = new Intent(context.getApplicationContext(), WalkNotification.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dogData", dog);
        intent.putExtra("bundle", bundle);
        PendingIntent walkPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 50,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, firstWalkTime, timeBetweenWalks, walkPendingIntent);

        Log.d(TAG, "Walk alarm set for: " + dog.getFirstWalkTime());
        Log.d(TAG, "Time between walks is: " + dog.getIntervalWalkTime() + " minutes");
    }
}
