package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.MessageFormat;
import java.util.Locale;

public class STOP_WATCH extends AppCompatActivity {

    TextView t1;
    Button b1,b2,b3;
    int seconds,minutes,milliseconds;
    long millisecond,starttime,timebuff,updatetime = 0L;
    Handler h1;
    private final Runnable run = new Runnable() {
        @Override
        public void run() {
            millisecond= SystemClock.uptimeMillis()-starttime;
            updatetime = timebuff+millisecond;
            seconds=(int)(updatetime/1000);
            minutes=seconds/60;
            seconds = seconds%60;
            milliseconds=(int) (updatetime%1000);
            t1.setText(MessageFormat.format("{0}:{1}:{2}",minutes,String.format(Locale.getDefault(),"%02d", seconds),String.format(Locale.getDefault(),"%02d",milliseconds)));
            h1.postDelayed(this,0);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        t1 = findViewById(R.id.textView);
        b1 = findViewById(R.id.replay);
        b2 = findViewById(R.id.play);
        b3 = findViewById(R.id.stop);
        h1=new Handler(Looper.getMainLooper());
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starttime=SystemClock.uptimeMillis();
                h1.postDelayed(run,0);
                b1.setEnabled(false);
                b3.setEnabled(true);
                b2.setEnabled(false);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b3.setEnabled(false);
                b1.setEnabled(true);
                b2.setEnabled(true);
                timebuff += millisecond;
                h1.removeCallbacks(run);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setEnabled(false);
                b2.setEnabled(true);
                b3.setEnabled(false);
                millisecond=0L;
                starttime=0L;
                timebuff=0L;
                updatetime=0L;
                seconds=0;
                minutes=0;
                milliseconds=0;
                t1.setText("00:00:00");

            }
        });
        t1.setText("00:00:00");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationview);
        bottomNavigationView.setSelectedItemId(R.id.bottom_alarm);


        bottomNavigationView.setOnItemSelectedListener(item ->{
            if (item.getItemId()==R.id.bottom_alarm) {
                startActivity(new Intent(getApplicationContext(), ALARM_1.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId()==R.id.bottom_stopwatch) {
                return true;
            }
            else if (item.getItemId()==R.id.bottom_timer) {
                startActivity(new Intent(getApplicationContext(), TIMER.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }else if (item.getItemId()==R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;

            }
            else {
                return false;
            }
        });
    }
}