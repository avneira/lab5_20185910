package com.example.lab5_20185910;

import android.Manifest;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class NotificationHelper {
    public static final String CHANNEL_ID = "TASK_REMINDER_CHANNEL";
    private static final String CHANNEL_ID_HIGH = "channelHighPriority";
    private static final String CHANNEL_ID_DEFAULT = "channelDefaultPriority";
    private static final String CHANNEL_ID_LOW = "channelLowPriority";


    public static void showNotification(Context context, String title, String message, String importance) {
        private static final String CHANNEL_ID_HIGH = "channelHighPriority";
        private static final String CHANNEL_ID_DEFAULT = "channelDefaultPriority";
        private static final String CHANNEL_ID_LOW = "channelLowPriority";

        public static void showNotification(Context context, String title, String message, String importance) {
            String channelId;
            int priority;

            switch (importance) {
                case "high":
                    channelId = CHANNEL_ID_HIGH;
                    priority = NotificationCompat.PRIORITY_HIGH;
                    break;
                case "default":
                    channelId = CHANNEL_ID_DEFAULT;
                    priority = NotificationCompat.PRIORITY_DEFAULT;
                    break;
                case "low":
                default:
                    channelId = CHANNEL_ID_LOW;
                    priority = NotificationCompat.PRIORITY_LOW;
                    break;
            }

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setPriority(priority)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                notificationManager.notify(1, builder.build());
            }
        }
    }
}
