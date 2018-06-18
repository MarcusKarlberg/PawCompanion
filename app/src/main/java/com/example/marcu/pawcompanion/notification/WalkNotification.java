package com.example.marcu.pawcompanion.notification;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.activity.WalkNotificationActivity;
import com.example.marcu.pawcompanion.repository.DogRepo;

/**
 * Created by marcu on 3/19/2018.
 */

public class WalkNotification extends BroadcastReceiver {
    private static final String TAG = "WalkNotification";

    public static final String channelID1 = "channelID1";
    public static final String walkNotificationChannel = "walkNotificationChannel";

    private NotificationManager manager;
    private Intent iteratingIntent;
    private PendingIntent pendingIntent;
    private DogRepo dogRepo;

    @Override
    public void onReceive(Context context, Intent intent) {

        if(manager == null){
            //To display the notification
            this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        createChannel();
        //buildNotification(context);
        //to start new notification when clicked
        this.iteratingIntent = new Intent(context, WalkNotificationActivity.class);
        //flag_activity_clear_top - the current activity called will replace the same old activity
        iteratingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        this.pendingIntent = PendingIntent.getActivity(context, 50, iteratingIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        buildNotification(context);
    }

    public void createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelID1,walkNotificationChannel, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(R.color.primary_dark);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            manager.createNotificationChannel(channel);
            Log.d(TAG, "createChannel: WALK CHANNEL CREATED!");
        }
    }

    public void buildNotification(Context context){

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelID1)
                .setContentIntent(pendingIntent)
                .setContentTitle("Walk Time!")
                .setSmallIcon(R.drawable.ic_notification2)
                .setContentText("Time to Walk ...")
                .setColorized(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);

        manager.notify(50, notificationBuilder.build());
        Log.d(TAG, "buildNotification:  WALK NOTIFICATION BUILT!");
    }
}
