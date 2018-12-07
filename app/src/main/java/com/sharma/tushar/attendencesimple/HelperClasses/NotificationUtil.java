package com.sharma.tushar.attendencesimple.HelperClasses;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.sharma.tushar.attendencesimple.HomePage;
import com.sharma.tushar.attendencesimple.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtil {
    private static Context context;
    private static final int NOTIFICATION_ID = 55;
    private static final String NOTIFICATION_CHANNEL_ID = "notificationChannel";

    private NotificationUtil() {
    }

    public static Notification createNotification() {
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

    public static void setContext(Context context) {
        NotificationUtil.context = context;
    }

    public class NotificationPublisher extends BroadcastReceiver {

        public static final String NOTIFICATION_INTENT_EXTRA = "create notification";

        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager notificationManager =
                    (NotificationManager) context.getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

            Notification notification = intent.getParcelableExtra(NOTIFICATION_INTENT_EXTRA);
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
