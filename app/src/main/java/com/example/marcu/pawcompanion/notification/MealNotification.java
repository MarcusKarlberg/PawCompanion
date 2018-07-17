package com.example.marcu.pawcompanion.notification;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.marcu.pawcompanion.activity.MealNotificationActivity;
import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.data.Dog;
/**
 * Created by marcu on 3/18/2018.
 */

public class MealNotification extends BroadcastReceiver {
    private static final String TAG = "MealNotification";

    public static final String channelId = "channel_ID_2";
    public static final String channelName = "mealNotificationChannel";

    private NotificationManager manager;
    private Intent iteratingIntent;
    private PendingIntent pendingIntent;
    private Dog dog;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(manager == null){
            //To display the notification
            this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getBundleExtra("bundle");
        if(bundle != null){
            dog = (Dog)bundle.getSerializable("dogData");
        }else {
            Log.d(TAG, "DOG DATA EMPTY:");
        }

        createChannel();
        //to start new notification when clicked
        this.iteratingIntent = new Intent(context, MealNotificationActivity.class);
        //flag_activity_clear_top - the current activity called will replace the same old activity
        iteratingIntent.putExtra("bundle", bundle);
        iteratingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        this.pendingIntent = PendingIntent.getActivity(context, (int)dog.getId().longValue(), iteratingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        buildNotification(context);
    }

    public void createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(R.color.primary_dark);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            manager.createNotificationChannel(channel);
            Log.d(TAG, "createChannel: MEAL CHANNEL CREATED");
        }
    }
    //Todo: TEST: added channel .setChannelId(channel2Id) and removed .setPriority(Notification.PRIORITY_MAX)
    public void buildNotification(Context context){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("Feed: " + dog.getName() + "!")
                .setSmallIcon(R.drawable.ic_notification2)
                .setContentText("Time to feed " + dog.getName())
                .setColorized(true)
                .setChannelId(channelId)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        manager.notify((int)dog.getId().longValue()+1, notificationBuilder.build());
    }

}