package com.sharma.tushar.attendencesimple.HelperClasses;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.sharma.tushar.attendencesimple.HomePage;
import com.sharma.tushar.attendencesimple.R;

public class NotificationUtil {
    private static final String NOTIFICATION_CHANNEL_ID = "notificationChannel";

    private NotificationUtil() {
    }

    public static Notification createNotification(Context context) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Attendance Reminder")
                .setContentText("Mark Today's Attendance")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, HomePage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        notificationBuilder.setContentIntent(pendingIntent);
        return notificationBuilder.build();
    }
}
