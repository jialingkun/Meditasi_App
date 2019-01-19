package com.bekkostudio.meditasidanretret.Timer.Namaskara;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Chart.Duration;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

public class NamaskaraRepentanceActivity extends AppCompatActivity {

    PowerManager.WakeLock wakeLock;

    TextView remainingTimeWidget;
    Button finishWidget;
    Button pauseWidget;

    boolean isButtonPause;
    long milliLeft;

    MediaPlayer guideSound;

    CountDownTimer countDownTimer;

    // record start and total meditation time in millisecond
    long startTime, totalDurasi = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namaskara_repentance);

        startTime = System.currentTimeMillis();

        //get widget
        remainingTimeWidget = findViewById(R.id.remainingTime);
        finishWidget = findViewById(R.id.finish);
        pauseWidget = findViewById(R.id.pause);

        //start wakelock to keep countdown awake
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "myapp:MyWakelockTag");
        wakeLock.acquire();

        guideSound = MediaPlayer.create(this,R.raw.namaskara_gratitude);
        guideSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                endMeditation();
            }
        });

        remainingTimeWidget.setText(formatMilliSecondsToTime(guideSound.getDuration()));

        isButtonPause = true;
        //pause on click
        pauseWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isButtonPause){
                    countDownTimer.cancel();
                    guideSound.pause();
                    isButtonPause = false;
                    pauseWidget.setText("Start");
                }else{
                    countDownTimer = new CountDownTimer(milliLeft,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            milliLeft = millisUntilFinished;
                            remainingTimeWidget.setText(formatMilliSecondsToTime(millisUntilFinished));
                        }

                        @Override
                        public void onFinish() {
                            remainingTimeWidget.setText("DONE");
                        }
                    }.start();

                    guideSound.start();
                    isButtonPause = true;
                    pauseWidget.setText("Pause");
                }
            }
        });

        //finish early on click
        finishWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endMeditation();
            }
        });

        countDownTimer = new CountDownTimer(guideSound.getDuration(),1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                milliLeft = millisUntilFinished;
                remainingTimeWidget.setText(formatMilliSecondsToTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                remainingTimeWidget.setText("DONE");
            }
        }.start();

        guideSound.start();
    }

    private void endMeditation() {
        totalDurasi += System.currentTimeMillis() - startTime;
        finishWidget.setEnabled(false);

        if (guideSound!=null){
            guideSound.stop();
            guideSound.release();
            guideSound = null;
        }

        if (countDownTimer!=null){countDownTimer.cancel();}

        //Go to result page
        Intent intent = new Intent(this, NamaskaraAudioResultActivity.class);
        intent.putExtra("remainingTime", formatMilliSecondsToTime(totalDurasi));
        startActivity(intent);
        finish();
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

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onDestroy() {

        wakeLock.release();
        if (guideSound!=null){
            guideSound.stop();
            guideSound.release();
            guideSound = null;
        }

        // save to database
        int milliTotalInSecond = (int) totalDurasi / 1000;
        Duration duration = new Duration(Global.getTodayDate(), milliTotalInSecond);
        Global.setDuration(getApplicationContext(), duration);

        super.onDestroy();

    }
}
