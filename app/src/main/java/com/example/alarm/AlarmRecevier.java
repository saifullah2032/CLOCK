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


        // You can add more actions here when the alarm triggers, such as playing a sound or showing a notification.
        // For example:
        String action = intent.getAction();
        if (action != null && action.equals("STOP_ALARM")) {
            // Stop the alarm

            // Cancel the notification
            cancelNotification(context);
            stopMediaPlayer();
        } else {
            // Start the alarm

            // Display the notification
            displayNotification(context);
            startMediaPlayer(context);

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
    private void startMediaPlayer(Context context) {

        mediaPlayer = MediaPlayer.create(context, R.raw.videoplayback);
         // Loop the sound
        mediaPlayer.start(); // Start the media player
    }

    private void stopMediaPlayer() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {

            mediaPlayer.stop(); // Stop the media player
            mediaPlayer.release(); // Release the media player resources
            mediaPlayer = null;
        }
    }
}