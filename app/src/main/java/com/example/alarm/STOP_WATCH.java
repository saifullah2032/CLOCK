package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;

public class STOP_WATCH extends AppCompatActivity {


    private TextView tvTimer;
    private Button btnStart, btnStop, btnLap, btnReset;
    private ListView lvLaps;

    private Handler handler = new Handler();
    private long startTime, timeInMilliseconds = 0L;
    private boolean isRunning = false;

    private ArrayList<String> laps = new ArrayList<>();
    private ArrayAdapter<String> lapsAdapter;
    private LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        animationView = findViewById(R.id.animation_view);
        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnLap = findViewById(R.id.btnLap);
        btnReset = findViewById(R.id.btnReset);
        lvLaps = findViewById(R.id.lvLaps);

        lapsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, laps);
        lvLaps.setAdapter(lapsAdapter);

        lvLaps.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                removeTask(position);
                return true;
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                handler.postDelayed(runnable, 0);
                btnStart.setVisibility(View.GONE);
                btnStop.setVisibility(View.VISIBLE);
                btnLap.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.GONE);
                isRunning = true;
                if (!animationView.isAnimating()) {
                    animationView.playAnimation();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeInMilliseconds += System.currentTimeMillis() - startTime;
                handler.removeCallbacks(runnable);
                btnStart.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.GONE);
                btnLap.setVisibility(View.GONE);
                btnReset.setVisibility(View.VISIBLE);
                isRunning = false;
                animationView.cancelAnimation();
            }
        });

        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long lapTime = System.currentTimeMillis() - startTime + timeInMilliseconds;
                laps.add(formatTime(lapTime));
                lapsAdapter.notifyDataSetChanged();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeInMilliseconds = 0L;
                startTime = 0L;
                tvTimer.setText("00:00:00");
                laps.clear();
                lapsAdapter.notifyDataSetChanged();
                btnReset.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
            }
        });
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
            else if (item.getItemId()==R.id.bottom_clock) {
                startActivity(new Intent(getApplicationContext(), Clock.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
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
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long elapsedTime = System.currentTimeMillis() - startTime + timeInMilliseconds;
            tvTimer.setText(formatTime(elapsedTime));
            handler.postDelayed(this, 1000);
        }
    };

    private String formatTime(long millis) {
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) ((millis / (1000 * 60)) % 60);
        int hours = (int) ((millis / (1000 * 60 * 60)) % 24);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void removeTask(int position) {
        if (position >= 0 && position < laps.size()) {
            laps.remove(position);
            lapsAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Lap removed", Toast.LENGTH_SHORT).show();
        }
    }
}