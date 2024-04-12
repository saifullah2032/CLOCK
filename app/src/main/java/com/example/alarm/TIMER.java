package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class TIMER extends AppCompatActivity {

  TextView t1;
    CountDownTimer t2;
  Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationview);
        bottomNavigationView.setSelectedItemId(R.id.bottom_alarm);


        bottomNavigationView.setOnItemSelectedListener(item ->{
            if (item.getItemId()==R.id.bottom_alarm) {
                startActivity(new Intent(getApplicationContext(), ALARM_1.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId()==R.id.bottom_stopwatch) {
                startActivity(new Intent(getApplicationContext(), STOP_WATCH.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            else if (item.getItemId()==R.id.bottom_timer) {
                return true;
            }
            else {
                return false;
            }
        });
        t1=findViewById(R.id.textView);
        b1=findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime();
            }
        });
    }

    private void startTime() {
        t2 = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hours = (millisUntilFinished/1000) /3600;
                long minutes = ((millisUntilFinished/1000) % 3600)/60;
                long seconds = (millisUntilFinished/1000)%60;
                String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours,minutes,seconds);
                t1.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                Toast.makeText(TIMER.this,"TIME'S UP\nGET UP!!⌛",Toast.LENGTH_SHORT).show();
                MediaPlayer alarm = MediaPlayer.create(TIMER.this,R.raw.alarm);
                alarm.start();

            }
        }.start();
    }
}