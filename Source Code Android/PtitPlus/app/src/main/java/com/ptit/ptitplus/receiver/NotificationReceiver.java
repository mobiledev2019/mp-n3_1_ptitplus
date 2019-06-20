package com.ptit.ptitplus.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ptit.ptitplus.MainActivity;
import com.ptit.ptitplus.R;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    NotificationManager mNotifyManager;
    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        createNotificationChannel(context);
        String mon = intent.getStringExtra("mon");
        mNotifyManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Set up the notification content intent to launch the app when
        // clicked.
        Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, 0, intent1,
                        PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, PRIMARY_CHANNEL_ID)
                .setContentTitle("title")
                .setContentText(mon)
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.image)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotifyManager.notify(0, builder.build());
    }

    public void createNotificationChannel(Context context) {

        mNotifyManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "abc",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("description 2");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
}
