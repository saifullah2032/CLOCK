package com.example.alarm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class AlarmRecevier extends BroadcastReceiver {

    private AlarmManager alarmManager;
    private TimePicker timePicker;
    private static final int NOTIFICATION_ID = 123;
    private static final String CHANNEL_ID = "AlarmChannel";
    private MediaPlayer mediaPlayer;
    @Override

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("STOP_ALARM".equals(action)) {
                // Handle stopping the alarm
                // Stop audio playback if needed
            } else {
                String audioUriString = intent.getStringExtra("audio_uri");
                if (audioUriString != null) {
                    Uri audioUri = Uri.parse(audioUriString);
                    MediaPlayer mediaPlayer = MediaPlayer.create(context, audioUri);
                    mediaPlayer.start();
                }
            }
        }





    private void displayNotification(Context context) {
        Toast.makeText(context, "Alarm! Wake up!", Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("Alarm")
                .setContentText("Wake up! It's time.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void cancelNotification(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(NOTIFICATION_ID);

    }

}