package com.example.marcu.pawcompanion.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.marcu.pawcompanion.data.Dog;

import static android.content.ContentValues.TAG;

public class ResetAlarmsBroadcastReceiver extends BroadcastReceiver {

    private Dog dog;
    private static final String TAG = "ResetBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ResetAlarmsBroadcastReceiver INITIATED");
        NotificationMngr notificationManager = new NotificationMngr(context);

        Bundle bundle = (intent.getExtras().getBundle("bundle"));
        if(bundle != null){
            dog = (Dog)bundle.getSerializable("dogData");
            Log.d(TAG, "onReceive: DOG DATA:" + dog.toString());

            notificationManager.deleteNotifications(dog);
            notificationManager.setWalkNotification(dog);
            notificationManager.setMealNotification(dog);
            notificationManager.setAlarmToResetDailyNotificationAlarms(dog);
            Log.d(TAG, "onReceive: NOTIFICATIONS RESET");
        }else {
            Log.d(TAG, "onReceive: RESET NOTIFICATIONS FAILED");
            Log.d(TAG, "onReceive: DOG DATA EMPTY:");
        }
    }
}
