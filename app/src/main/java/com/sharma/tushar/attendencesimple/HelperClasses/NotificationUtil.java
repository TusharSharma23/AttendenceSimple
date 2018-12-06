package com.sharma.tushar.attendencesimple.HelperClasses;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.sharma.tushar.attendencesimple.HomePage;
import com.sharma.tushar.attendencesimple.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtil {
    private static Context context;

    private NotificationUtil() {
    }

    public static void createNotification() {
        NotificationManager notificationManager =
                (NotificationManager) context.getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                context, NotificationChannel.DEFAULT_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Attendance Reminder")
                .setContentText("Mark Today's Attendance")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        Intent intent = new Intent(context, HomePage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(0, notificationBuilder.build());
    }

    public static void setContext(Context context) {
        NotificationUtil.context = context;
    }

}
