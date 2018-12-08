package com.sharma.tushar.attendencesimple.HelperClasses;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationService extends BroadcastReceiver {

    public static final String NOTIFICATION_INTENT_EXTRA = "create notification";

    private static final int NOTIFICATION_ID = 55;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION_INTENT_EXTRA);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
