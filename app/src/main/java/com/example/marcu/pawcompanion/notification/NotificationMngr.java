package com.example.marcu.pawcompanion.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.utility.DogCalculator;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import static android.content.Context.ALARM_SERVICE;

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
        long timeBetweenMeals = TimeUnit.MINUTES.toMillis(DogCalculator
                .getIntervalMealTimeInMins(dog));

        LocalTime nextMealTime = DogCalculator.getNextMealTime(dog);
        long nextMeal = localTimeToMillis(nextMealTime);

        Intent intent = new Intent(context.getApplicationContext(), MealNotification.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dogData", dog);
        intent.putExtra("bundle", bundle);
        PendingIntent mealPendingIntent = PendingIntent
                .getBroadcast(context.getApplicationContext(), (int)dog.getId()
                        .longValue(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Objects.requireNonNull(alarmManager).setRepeating(AlarmManager.RTC_WAKEUP, nextMeal, timeBetweenMeals, mealPendingIntent);
        Log.d(TAG, "Notification: MEAL NOTIFICATION SET: " + nextMealTime);
    }

    public void setWalkNotification(Dog dog){
        long timeBetweenWalks = TimeUnit.MINUTES.toMillis(DogCalculator
                .getIntervalWalkTimeInMins(dog.getBirthDate(), dog.getBreed().getSizeLevel()));

        LocalTime nextWalkTime = DogCalculator.getNextWalkTime(dog);
        long nextWalk = localTimeToMillis(nextWalkTime);

        Intent intent = new Intent(context.getApplicationContext(), WalkNotification.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dogData", dog);
        intent.putExtra("bundle", bundle);
        PendingIntent walkPendingIntent = PendingIntent
                .getBroadcast(context.getApplicationContext(), (int)dog.getId()
                        .longValue()+1000,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Objects.requireNonNull(alarmManager).setRepeating(AlarmManager.RTC_WAKEUP, nextWalk, timeBetweenWalks, walkPendingIntent);
        Log.d(TAG, "Notification: WALK NOTIFICATION SET: " + nextWalkTime);
    }

    public void setAlarmToResetDailyNotificationAlarms(Dog dog){
        LocalTime time = DogCalculator.getLastWalkTime(dog).plusHours(1);
        DateTime dateTime = DateTime.now().withTime(time);

        if(time.isBefore(LocalTime.now())){
            dateTime = dateTime.plusDays(1);
        }
        long timeToReset = dateTime.getMillis();

        Intent intent = new Intent(context.getApplicationContext(), ResetAlarmsBroadcastReceiver.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("dogData", dog);
        intent.putExtra("bundle", bundle);
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(context.getApplicationContext(), (int)dog.getId()
                        .longValue()+2000,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Objects.requireNonNull(alarmManager).set(AlarmManager.RTC_WAKEUP, timeToReset, pendingIntent);
    }

    public void deleteNotifications(Dog dog){
        Intent walkIntent = new Intent(context.getApplicationContext(), WalkNotification.class);
        Intent mealIntent = new Intent(context.getApplicationContext(), MealNotification.class);

        Bundle walkBundle = new Bundle();
        walkBundle.putSerializable("dogData", dog);
        walkIntent.putExtra("bundle", walkBundle);

        Bundle mealBundle = new Bundle();
        mealBundle.putSerializable("dogData", dog);
        mealIntent.putExtra("bundle", mealBundle);

        PendingIntent walkPendingIntent = PendingIntent
                .getBroadcast(context.getApplicationContext(), (int)dog.getId()
                        .longValue()+1000,walkIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent mealPendingIntent = PendingIntent
                .getBroadcast(context.getApplicationContext(), (int)dog.getId()
                        .longValue(), mealIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(walkPendingIntent);
            alarmManager.cancel(mealPendingIntent);
        }
    }

    private static long localTimeToMillis(LocalTime time){
        DateTime dateTime = DateTime.now().withTime(time);

            return dateTime.getMillis();
    }
}
