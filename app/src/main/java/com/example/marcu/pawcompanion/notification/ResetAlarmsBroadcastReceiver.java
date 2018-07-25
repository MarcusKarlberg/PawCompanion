package com.example.marcu.pawcompanion.notification;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.marcu.pawcompanion.data.Dog;

import static android.content.ContentValues.TAG;

public class ResetAlarmsBroadcastReceiver extends BroadcastReceiver {

    private Dog dog;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationMngr notificationManager = new NotificationMngr(context);

        Bundle bundle = (intent.getExtras().getBundle("bundle"));
        if(bundle != null){
            dog = (Dog)bundle.getSerializable("dogData");
            Log.d(TAG, "DOG DATA:" + dog.toString());

            notificationManager.deleteNotifications(dog);
            notificationManager.setWalkNotification(dog);
            notificationManager.setMealNotification(dog);

        }else {
            Log.d(TAG, "DOG DATA EMPTY:");
        }
    }
}
