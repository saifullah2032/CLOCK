package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class TIMER extends AppCompatActivity {


    private EditText etTimeInput;
    private Button btnStartTimer;
    private TextView tvCountdown;
    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;
    private LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        etTimeInput = findViewById(R.id.etTimeInput);
        btnStartTimer = findViewById(R.id.btnStartTimer);
        tvCountdown = findViewById(R.id.tvCountdown);
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        animationView = findViewById(R.id.animation_view);
        btnStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeInput = etTimeInput.getText().toString();
                if (timeInput.isEmpty()) {
                    Toast.makeText(TIMER.this, "Please enter a valid time", Toast.LENGTH_SHORT).show();
                    return;
                }

                int timeInSeconds = Integer.parseInt(timeInput);
                startCountdown(timeInSeconds);
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
                startActivity(new Intent(getApplicationContext(), STOP_WATCH.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            else if (item.getItemId()==R.id.bottom_clock) {
                startActivity(new Intent(getApplicationContext(), Clock.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            else if (item.getItemId()==R.id.bottom_timer) {
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
    private void startCountdown(int timeInSeconds) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(timeInSeconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                tvCountdown.setText(formatTime(secondsRemaining));
                if (!animationView.isAnimating()) {
                    animationView.playAnimation();
                }
            }

            public void onFinish() {
                tvCountdown.setText("00:00");
                Toast.makeText(TIMER.this, "Time's up!", Toast.LENGTH_SHORT).show();
                playAlarm();
                animationView.cancelAnimation();
            }
        }.start();
    }

    private void playAlarm() {
        if (mediaPlayer != null) {
            mediaPlayer.start();

        }
    }
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();

            mediaPlayer = null;
        }
    }

}
