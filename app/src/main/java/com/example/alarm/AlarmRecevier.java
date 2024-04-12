package com.example.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmRecevier extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm! Wake up!", Toast.LENGTH_SHORT).show();
        // You can add more actions here when the alarm triggers, such as playing a sound or showing a notification.
        // For example:
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.videoplayback);
        mediaPlayer.start();
    }
}