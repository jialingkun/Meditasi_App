package com.bekkostudio.meditasidanretret.Timer.NianFo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Chart.Duration;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

public class NianFoManualActivity extends AppCompatActivity implements View.OnClickListener {
    Button finishEarlyButton, nextButton;
    TextView countSiklusLabel;

    //Sound on click BGM image
    MediaPlayer backgroundSound;
    MediaPlayer bellSound;

    //108 ketukan
    MediaPlayer knockSound;
    int intervalKetukan;

    int jumlahSiklus;
    int musicId;
    int counterSiklus = 1;
    // record start and total meditation time in millisecond
    long startTime, totalDurasi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nian_fo_manual);

        startTime = System.currentTimeMillis();

        finishEarlyButton = findViewById(R.id.finishEarlyButton);
        nextButton = findViewById(R.id.nianFoNextButton);
        countSiklusLabel = findViewById(R.id.countSiklusLabel);

        jumlahSiklus = Integer.valueOf(getIntent().getStringExtra(NianFoHomeActivity.EXTRA_SIKLUS));
        musicId = getIntent().getIntExtra(NianFoHomeActivity.EXTRA_MUSIC_ID, 0);

        countSiklusLabel.setText(String.valueOf(counterSiklus));

        finishEarlyButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        backgroundSound = MediaPlayer.create(this, musicId);
        backgroundSound.start();

        knockSound = MediaPlayer.create(NianFoManualActivity.this,R.raw.knock);
        intervalKetukan = 108;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.nianFoNextButton) {
            counterSiklus++;
            if (counterSiklus<=jumlahSiklus){
                if (backgroundSound.isPlaying()){
                    backgroundSound.pause();
                    backgroundSound.seekTo(0);
                }
                countSiklusLabel.setText(String.valueOf(counterSiklus));
                backgroundSound.start();
            }

            if (counterSiklus == jumlahSiklus) {
                // check if this is a last meditation, then play a bell sound
                backgroundSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        bellSound = MediaPlayer.create(NianFoManualActivity.this,R.raw.bell_2);
                        bellSound.start();
                        endMeditation();
                    }
                });
            }else if (counterSiklus%intervalKetukan==0){
                knockSound.start();
            }
        } else if(v.getId() == R.id.finishEarlyButton) {
            if (backgroundSound.isPlaying()){
                backgroundSound.stop();
            }
            endMeditation();
        }
    }

    private void endMeditation() {
        totalDurasi += System.currentTimeMillis() - startTime;
        countSiklusLabel.setText("DONE");
        nextButton.setEnabled(false);
        finishEarlyButton.setEnabled(false);

        if (backgroundSound!=null){
            backgroundSound.stop();
            backgroundSound.release();
            backgroundSound = null;
        }

        Intent intent = new Intent(this, NianFoResultActivity.class);
        intent.putExtra(NianFoResultActivity.EXTRA_SIKLUS, counterSiklus);
        intent.putExtra(NianFoResultActivity.EXTRA_DURASI, totalDurasi);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        if (knockSound!=null){
            knockSound.stop();
            knockSound.release();
            knockSound = null;
        }

        // save to database
        int milliTotalInSecond = (int) totalDurasi / 1000;
        Duration duration = new Duration(Global.getTodayDate(), milliTotalInSecond);
        Global.setDuration(getApplicationContext(), duration);
    }
}
