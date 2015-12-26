package com.example.travik.wekker.Alarm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmService extends IntentService {

    public AlarmService(){
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent){
        sendNotification("Wake up");
    }

    private void sendNotification(String notification){
        Log.d("AlarmService", "Preparing to send notification ...: " + notification);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, AlarmActivity.class), 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Alarm")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notification))
                .setContentText(notification);

        notificationBuilder.setContentIntent(contentIntent);
        notificationManager.notify(1, notificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }
}
