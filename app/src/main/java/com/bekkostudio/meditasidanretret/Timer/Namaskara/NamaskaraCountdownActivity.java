package com.bekkostudio.meditasidanretret.Timer.Namaskara;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bekkostudio.meditasidanretret.Chart.Duration;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

public class NamaskaraCountdownActivity extends AppCompatActivity implements View.OnClickListener{
    TextView countSiklusLabel, posisiLabel;
    Button pauseButton, finishEarlyButton;
    MediaPlayer bellTegap, bellSujud;
    PowerManager.WakeLock wakeLock;
    float durasiSujud, durasiTegap;
    int siklus;
    long durasiSujudInSecond, durasiTegapInSecond;
    private int counter = 1;

    CountDownTimer timerSujud, timerTegap;
    // total millisecond until Count Down Timer onFinish
    long milliLeft;
    // total duration
    long milliTotal = 0;
    // check if timer is running
    boolean isRunningTegap = false;
    boolean isRunningSujud = false;
    // pause button text status
    boolean isButtonPause = true;
    // save which timer is paused
    String pauseTimer;

    public static String EXTRA_SIKLUS = "extra_siklus";
    public static String EXTRA_DURASI = "extra_durasi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namaskara_countdown);

        //initialize iscompleted status
        Global.tempIsCompleted = false;

        //start wakelock to keep countdown awake
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyWakelockTag");
        wakeLock.acquire();

        countSiklusLabel = findViewById(R.id.countSiklusLabel);
        posisiLabel = findViewById(R.id.posisiLabel);
        pauseButton = findViewById(R.id.pauseButton);
        finishEarlyButton = findViewById(R.id.finishEarlyButton);

        pauseButton.setOnClickListener(this);
        finishEarlyButton.setOnClickListener(this);

        // initialize sound
        bellTegap = MediaPlayer.create(this,R.raw.bell);
        bellSujud = MediaPlayer.create(this,R.raw.bell_2);

        // get parameter
        durasiTegap = getIntent().getFloatExtra(NamaskaraHomeActivity.EXTRA_DURASI_TEGAP, 0);
        durasiSujud = getIntent().getFloatExtra(NamaskaraHomeActivity.EXTRA_DURASI_SUJUD, 0);
        siklus = Integer.valueOf(getIntent().getStringExtra(NamaskaraHomeActivity.EXTRA_SIKLUS));

        // get the actual time duration in second
        durasiTegapInSecond =(long) ((durasiTegap + 1) * 1000);
        durasiSujudInSecond =(long) ((durasiSujud + 1) * 1000);

        // start a bell recursive loop
        bellTegap.start();
        startTimerTegap(durasiTegapInSecond);
    }

    private void startTimerTegap(final long durasiTegap) {
        if(counter > siklus) {
            Global.tempIsCompleted = true;
            counter--;
            endMeditation();
            return;
        }

        isRunningTegap = true;
        countSiklusLabel.setText(String.valueOf(counter));
        posisiLabel.setText("Posisi Berdiri");
        timerTegap = new CountDownTimer(durasiTegap, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                milliLeft = millisUntilFinished;
                milliTotal += 1000;
            }

            @Override
            public void onFinish() {
                milliTotal += 1000;
                bellSujud.start();
                posisiLabel.setText("Posisi Sujud");
                isRunningTegap = false;
                startTimerSujud(durasiSujudInSecond);
            }
        }.start();
    }

    private void startTimerSujud(final long durasiSujud) {
        isRunningSujud = true;
        timerSujud = new CountDownTimer(durasiSujud, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                milliLeft = millisUntilFinished;
                milliTotal += 1000;
            }

            @Override
            public void onFinish() {
                milliTotal += 1000;
                isRunningSujud = false;
                counter++;
                bellTegap.start();
                startTimerTegap(durasiTegapInSecond);
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pauseButton) {
            if (isButtonPause) {
                if (isRunningTegap) {
                    timerTegap.cancel();
                    pauseTimer = "tegap";
                }
                else if (isRunningSujud) {
                    timerSujud.cancel();
                    pauseTimer = "sujud";
                }
                pauseButton.setText("Start");
                isButtonPause = false;
            }
            else {
                if (pauseTimer.equals("tegap")) {
                    startTimerTegap(milliLeft);
                    isRunningTegap = true;
                    isRunningSujud = false;
                }
                else {
                    counter++;
                    bellTegap.start();
                    startTimerTegap(durasiTegapInSecond);
                    isRunningTegap = true;
                    isRunningSujud = false;
                }
                pauseButton.setText("Pause");
                isButtonPause = true;
            }
        }
        else if (v.getId() == R.id.finishEarlyButton) {
            if (isRunningTegap) {
                timerTegap.cancel();
            }
            else if (isRunningSujud) {
                timerSujud.cancel();
            }
            endMeditation();
        }
    }

    private void endMeditation() {
        countSiklusLabel.setText("Done");
        pauseButton.setEnabled(false);
        finishEarlyButton.setEnabled(false);

        Intent intent = new Intent(this, NamaskaraResultActivity.class);
        intent.putExtra(EXTRA_SIKLUS, counter);
        intent.putExtra(EXTRA_DURASI, milliTotal);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //endMeditation();
        if(timerSujud != null) {
            timerSujud.cancel();
        }
        if (timerTegap != null) {
            timerTegap.cancel();
        }

        // save to database
        int milliTotalInSecond = (int) milliTotal / 1000;
        Duration duration = new Duration(Global.getTodayDate(), milliTotalInSecond);
        Global.setDuration(getApplicationContext(), duration);

    }
}
