package com.bekkostudio.meditasidanretret.Timer.Namaskara;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

public class NamaskaraCountdownActivity extends AppCompatActivity implements View.OnClickListener{
    TextView countSiklusLabel, posisiLabel;
    Button pauseButton, finishEarlyButton;
    MediaPlayer bellSound;
    PowerManager.WakeLock wakeLock;
    int durasiSujud, durasiTegap, siklus;
    long durasiSujudInSecond, durasiTegapInSecond;
    private int counter = 1;

    CountDownTimer timerSujud, timerTegap;
    // total millisecond until onFinished
    long milliLeft;
    // check if timer is running
    boolean isRunningTegap = false;
    boolean isRunningSujud = false;
    // pause button text status
    boolean isButtonPause = true;
    // save which timer is paused
    String pauseTimer;

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
        bellSound = MediaPlayer.create(this,R.raw.bell);

        // get parameter
        durasiTegap = getIntent().getIntExtra(NamaskaraHomeActivity.EXTRA_DURASI_TEGAP, 0);
        durasiSujud = getIntent().getIntExtra(NamaskaraHomeActivity.EXTRA_DURASI_SUJUD, 0);
        siklus = Integer.valueOf(getIntent().getStringExtra(NamaskaraHomeActivity.EXTRA_SIKLUS));

        // get the actual time duration in second
        switch (durasiTegap) {
            case NamaskaraHomeActivity.SEPULUH_DETIK:
                durasiTegapInSecond = 10000;
                break;
            case NamaskaraHomeActivity.DUAPULUH_DETIK:
                durasiTegapInSecond = 20000;
                break;
            case NamaskaraHomeActivity.TIGAPULUH_DETIK:
                durasiTegapInSecond = 30000;
                break;
            case NamaskaraHomeActivity.SATU_MENIT:
                durasiTegapInSecond = 60000;
                break;
            case NamaskaraHomeActivity.TIGA_MENIT:
                durasiTegapInSecond = 180000;
                break;
            case NamaskaraHomeActivity.LIMA_MENIT:
                durasiTegapInSecond = 300000;
                break;
        }
        switch (durasiSujud) {
            case NamaskaraHomeActivity.SEPULUH_DETIK:
                durasiSujudInSecond = 10000;
                break;
            case NamaskaraHomeActivity.DUAPULUH_DETIK:
                durasiSujudInSecond = 20000;
                break;
            case NamaskaraHomeActivity.TIGAPULUH_DETIK:
                durasiSujudInSecond = 30000;
                break;
            case NamaskaraHomeActivity.SATU_MENIT:
                durasiSujudInSecond = 60000;
                break;
            case NamaskaraHomeActivity.TIGA_MENIT:
                durasiSujudInSecond = 180000;
                break;
            case NamaskaraHomeActivity.LIMA_MENIT:
                durasiSujudInSecond = 300000;
                break;
        }

        // start a bell recursive loop
        startTimerTegap(durasiTegapInSecond);
    }

    private void startTimerTegap(final long durasiTegap) {
        if(counter > siklus) {
            Global.tempIsCompleted = true;
            endMeditation();
            return;
        }

        bellSound.start();
        countSiklusLabel.setText(String.valueOf(counter));
        posisiLabel.setText("Posisi Tegap");
        timerTegap = new CountDownTimer(durasiTegap, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                milliLeft = millisUntilFinished;
                isRunningTegap = true;
            }

            @Override
            public void onFinish() {
                bellSound.start();
                posisiLabel.setText("Posisi Sujud");
                isRunningTegap = false;
                startTimerSujud(durasiSujudInSecond);
            }
        }.start();
    }

    private void startTimerSujud(final long durasiSujud) {
        timerSujud = new CountDownTimer(durasiSujud, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                milliLeft = millisUntilFinished;
                isRunningSujud = true;
            }

            @Override
            public void onFinish() {
                isRunningSujud = false;
                counter++;
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
                    startTimerSujud(milliLeft);
                    isRunningTegap = false;
                    isRunningSujud = true;
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
    }
}
