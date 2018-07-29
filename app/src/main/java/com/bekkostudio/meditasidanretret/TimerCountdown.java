package com.bekkostudio.meditasidanretret;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TimerCountdown extends AppCompatActivity {
    PowerManager.WakeLock wakeLock;
    TextView remainingTimeWidget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_countdown);

        //get parameter
        Intent intent = getIntent();
        int meditationDuration = intent.getIntExtra("meditationDuration",60);
        int warmupDuration = intent.getIntExtra("warmupDuration",60);
        int ambientMusic = intent.getIntExtra("ambientMusic",60);

        //set duration
        remainingTimeWidget = findViewById(R.id.remainingTime);
        remainingTimeWidget.setText(formatMilliSecondsToTime(meditationDuration*1000));

        //start wakelock to keep countdown awake
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyWakelockTag");
        wakeLock.acquire();

        new CountDownTimer(meditationDuration*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                remainingTimeWidget.setText(formatMilliSecondsToTime(millisUntilFinished));
            }

            public void onFinish() {
                remainingTimeWidget.setText("done!");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        wakeLock.release();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private String formatMilliSecondsToTime(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : "
                + twoDigitString(seconds);
    }

    private String twoDigitString(long number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }
}
