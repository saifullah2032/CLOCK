package com.example.alarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Clock extends AppCompatActivity {

    private TextView tvTimeDate;
    private TextView tvLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView tvCurrentTime;
    private Handler handler = new Handler();
    private Runnable runnable;
    private Spinner citySpinner;
    private TextView tvWorldTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        tvWorldTime = findViewById(R.id.tvWorldTime);
        citySpinner = findViewById(R.id.citySpinner);
        tvTimeDate = findViewById(R.id.tvTimeDate);
        tvLocation = findViewById(R.id.tvLocation);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);

        runnable = new Runnable() {
            @Override
            public void run() {
                updateCurrentTime();
                updateTimeDate();
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);


        setupCitySpinner();
        setupCityTimeUpdater();
        setupLocation();



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
                startActivity(new Intent(getApplicationContext(), TIMER.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            else if (item.getItemId()==R.id.bottom_clock) {
                return true;
            }
            else if (item.getItemId()==R.id.bottom_home) {
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

    private void updateTimeDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd EEEE, yyyy", Locale.getDefault());
        String currentTimeDate = sdf.format(new Date());
        tvTimeDate.setText(currentTimeDate);
    }


    private void updateCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        tvCurrentTime.setText(currentTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
    private void setupLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            updateLocationName(location);
                        }
                    }
                });
    }

    private void updateLocationName(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String locationName = address.getLocality() + ", " + address.getCountryName();
                tvLocation.setText(locationName);
            } else {
                tvLocation.setText("Unable to determine location");
            }
        } catch (IOException e) {
            e.printStackTrace();
            tvLocation.setText("Unable to determine location");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupLocation();
            } else {
                Toast.makeText(this, "Permission denied to access location", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void setupCitySpinner() {
        String[] cities = {"New York", "London", "Tokyo", "Sydney"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);
    }

    private void setupCityTimeUpdater() {
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCityTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    private void updateCityTime() {
        String selectedCity = (String) citySpinner.getSelectedItem();
        String timeZoneId = "";

        switch (selectedCity) {
            case "New York":
                timeZoneId = "America/New_York";
                break;
            case "London":
                timeZoneId = "Europe/London";
                break;
            case "Tokyo":
                timeZoneId = "Asia/Tokyo";
                break;
            case "Sydney":
                timeZoneId = "Australia/Sydney";
                break;
        }

        TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy hh:mm a", Locale.getDefault());
        sdf.setTimeZone(timeZone);
        String cityTime = sdf.format(new Date());
        tvWorldTime.setText(cityTime);
    }

}