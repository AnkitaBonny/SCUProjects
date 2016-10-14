package com.example.computer1.myarttherapy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager;
    NotificationCompat.Builder builder;

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {

            builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.paint_brush)
                    .setContentTitle("MyArtTherapy ")
                    .setContentText("Hi!")
                    .setAutoCancel(true)
                    .setContentInfo("Launch App! ")
                    .setColor(25500);
            Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(50);
            builder.setDefaults(Notification.DEFAULT_SOUND);

        }
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent(context, MainActivity.class);

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent mainIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);

        builder.setContentIntent(mainIntent);
        Notification notification = builder.build();

        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1, builder.build());

    }

}

