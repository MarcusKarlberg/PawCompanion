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

import com.example.marcu.pawcompanion.R;
import com.example.marcu.pawcompanion.activity.WalkNotificationActivity;
import com.example.marcu.pawcompanion.data.Dog;
import com.example.marcu.pawcompanion.utility.DogCalculator;

/**
 * Created by marcu on 3/19/2018.
 */

public class WalkNotification extends BroadcastReceiver {
    private static final String TAG = "WalkNotification";

    public static final String channelId = "channel_ID_1";
    public static final String walkNotificationChannel = "walkNotificationChannel";

    private NotificationManager manager;
    private Intent iteratingIntent;
    private PendingIntent pendingIntent;
    private Dog dog;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(manager == null){
            this.manager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
        }

        createChannel();
        Bundle bundle = intent.getBundleExtra("bundle");
        validateData(bundle);
        setupPendingIntent(bundle, context);
        buildNotification(context);
    }

    public void createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId,
                    walkNotificationChannel, NotificationManager.IMPORTANCE_HIGH);

            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(R.color.primary_dark);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            manager.createNotificationChannel(channel);
            Log.d(TAG, "createChannel: WALK CHANNEL CREATED!");
        }
    }

    private void validateData(Bundle bundle){
        if(bundle != null){
            dog = (Dog)bundle.getSerializable("dogData");
        }else {
            Log.d(TAG, "DOG DATA EMPTY:");
        }
    }

    private void setupPendingIntent(Bundle bundle, Context context){
        this.iteratingIntent = new Intent(context, WalkNotificationActivity.class);
        iteratingIntent.putExtra("bundle", bundle);
        iteratingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.pendingIntent = PendingIntent.getActivity(context,
                (int)dog.getId().longValue()+1,
                iteratingIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void buildNotification(Context context){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("Walk " + dog.getName() + "!")
                .setSmallIcon(R.drawable.ic_notification2)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Distance: " + DogCalculator.getDistancePerWalk(dog) + " km")
                        .addLine("Duration: " + DogCalculator.getDistancePerWalk(dog) + " mins"))
                .setColorized(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        manager.notify((int)dog.getId().longValue()+1, notificationBuilder.build());
        Log.d(TAG, "buildNotification:  WALK NOTIFICATION BUILT!");
    }
}
