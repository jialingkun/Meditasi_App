package com.bekkostudio.meditasidanretret;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerCountdown extends AppCompatActivity {
    PowerManager.WakeLock wakeLock;

    TextView remainingTimeWidget;
    TextView messageWidget;
    Button finishWidget;

    MediaPlayer bellSound;
    MediaPlayer backgroundSound;

    CountDownTimer warmupTimer;
    CountDownTimer meditationTimer;

    int resultTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_countdown);

        //get parameter
        Intent intent = getIntent();
        final int meditationDuration = intent.getIntExtra("meditationDuration",60);
        int warmupDuration = intent.getIntExtra("warmupDuration",60);
        final int ambientMusic = intent.getIntExtra("ambientMusic",60);

        //initialize sound
        bellSound = MediaPlayer.create(this,R.raw.bell);

        if (ambientMusic != 0){
            backgroundSound = MediaPlayer.create(this,ambientMusic);
            backgroundSound.start();
            backgroundSound.isLooping();
        }


        //get widget
        messageWidget = findViewById(R.id.message);
        remainingTimeWidget = findViewById(R.id.remainingTime);
        finishWidget = findViewById(R.id.finish);

        //set message
        messageWidget.setText("Pemanasan");
        remainingTimeWidget.setText(formatMilliSecondsToTime(warmupDuration*1000));

        //start wakelock to keep countdown awake
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyWakelockTag");
        wakeLock.acquire();

        //start Warmup Timer
        bellSound.start();
        resultTime = 0;
        warmupTimer = new CountDownTimer(warmupDuration*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                remainingTimeWidget.setText(formatMilliSecondsToTime(millisUntilFinished));
            }

            public void onFinish() {
                bellSound.start();
                messageWidget.setText("Meditasi");

                //start Meditation Timer
                meditationTimer = new CountDownTimer(meditationDuration*1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        remainingTimeWidget.setText(formatMilliSecondsToTime(millisUntilFinished));
                        resultTime = (int) ((meditationDuration*1000) - millisUntilFinished);
                    }

                    public void onFinish() {
                        bellSound.start();
                        if (ambientMusic != 0){
                            backgroundSound.stop();
                        }

                        endMeditation();
                    }
                }.start();
            }
        }.start();


        //finish early on click
        finishWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endMeditation();
            }
        });
    }

    @Override
    protected void onDestroy() {
        wakeLock.release();
        if (backgroundSound!=null){
            backgroundSound.stop();
            backgroundSound.release();
            backgroundSound = null;
        }
        if (bellSound!=null){
            bellSound.stop();
            bellSound.release();
            bellSound = null;
        }
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

    private void endMeditation(){
        warmupTimer.cancel();
        if (meditationTimer!=null){meditationTimer.cancel();}
        Intent intent = new Intent(this, MeditationResult.class);
        intent.putExtra("remainingTime", formatMilliSecondsToTime(resultTime));
        this.startActivity(intent);
        finish();
    }
}
