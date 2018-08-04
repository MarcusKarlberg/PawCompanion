package com.example.marcu.pawcompanion.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.marcu.pawcompanion.data.Dog;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

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
        long timeBetweenMeals = TimeUnit.MINUTES.toMillis(dog.getIntervalMealTime());
        long firstMealTime = localTimeToMillis(dog.getFirstMealTime());

        Intent intent = new Intent(context.getApplicationContext(), MealNotification.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dogData", dog);
        intent.putExtra("bundle", bundle);
        PendingIntent mealPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), (int)dog.getId().longValue(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, firstMealTime, timeBetweenMeals, mealPendingIntent);
        Log.d(TAG, "Notification: MEAL NOTIFICATION SET: " + dog.getFirstMealTime());
    }

    public void setWalkNotification(Dog dog){
        long timeBetweenWalks = TimeUnit.MINUTES.toMillis(dog.getIntervalWalkTime());
        long firstWalkTime = localTimeToMillis(dog.getFirstWalkTime());

        Intent intent = new Intent(context.getApplicationContext(), WalkNotification.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dogData", dog);
        intent.putExtra("bundle", bundle);
        PendingIntent walkPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), (int)dog.getId().longValue()+1,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, firstWalkTime, timeBetweenWalks, walkPendingIntent);
        Log.d(TAG, "Notification: WALK NOTIFICATION SET: " + dog.getFirstWalkTime());
    }

    public void setAlarmToResetDailyNotificationAlarms(Dog dog){
        long midnight = localTimeToMillis(LocalTime.MIDNIGHT);
        Intent intent = new Intent(context.getApplicationContext(), ResetAlarmsBroadcastReceiver.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("dogData", dog);
        intent.putExtra("bundle", bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), (int)dog.getId().longValue()+2,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, midnight, pendingIntent);
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

        PendingIntent walkPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), (int)dog.getId().longValue()+1,walkIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent mealPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), (int)dog.getId().longValue(), mealIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(walkPendingIntent);
        alarmManager.cancel(mealPendingIntent);
    }

    private static long localTimeToMillis(LocalTime time){
        DateTime dateTime = DateTime.now().withTime(time);

        if(time.isBefore(LocalTime.now())){
            return dateTime.plusDays(1).getMillis();
        }
        else{
            return dateTime.getMillis();
        }
    }
}
