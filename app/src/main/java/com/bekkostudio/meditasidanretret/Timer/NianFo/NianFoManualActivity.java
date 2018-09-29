package com.bekkostudio.meditasidanretret.Timer.NianFo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.R;

public class NianFoManualActivity extends AppCompatActivity implements View.OnClickListener {
    Button pauseButton, finishEarlyButton, nextButton;
    TextView countSiklusLabel;

    //Sound on click BGM image
    MediaPlayer backgroundSound;

    int jumlahSiklus;
    int musicId;
    int counterSiklus = 1;
    // pause button text status
    boolean isButtonPause = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nian_fo_manual);

        pauseButton = findViewById(R.id.pauseButton);
        finishEarlyButton = findViewById(R.id.finishEarlyButton);
        nextButton = findViewById(R.id.nianFoNextButton);
        countSiklusLabel = findViewById(R.id.countSiklusLabel);

        jumlahSiklus = Integer.valueOf(getIntent().getStringExtra(NianFoHomeActivity.EXTRA_SIKLUS));
        musicId = getIntent().getIntExtra(NianFoHomeActivity.EXTRA_MUSIC_ID, 0);

        countSiklusLabel.setText(String.valueOf(counterSiklus));

        pauseButton.setOnClickListener(this);
        finishEarlyButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        backgroundSound = MediaPlayer.create(this, musicId);
        backgroundSound.start();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.nianFoNextButton) {
            counterSiklus++;

            if (counterSiklus > jumlahSiklus) {
                endMeditation();
                return;
            }

            if (backgroundSound.isPlaying()){
                backgroundSound.pause();
                backgroundSound.seekTo(0);
            }
            countSiklusLabel.setText(String.valueOf(counterSiklus));
            backgroundSound.start();
        }
        else if(v.getId() == R.id.pauseButton) {
            if (isButtonPause) {
                isButtonPause = false;
                pauseButton.setText("Start");
                if (backgroundSound.isPlaying()){
                    backgroundSound.pause();
                }
            }
            else {
                isButtonPause = true;
                pauseButton.setText("Pause");
                if (musicId != 0) {
                    backgroundSound.start();
                }
            }
        }
        else if(v.getId() == R.id.finishEarlyButton) {
            if (backgroundSound.isPlaying()){
                backgroundSound.stop();
            }
            endMeditation();
        }
    }

    private void endMeditation() {
        countSiklusLabel.setText("DONE");
        nextButton.setEnabled(false);
        pauseButton.setEnabled(false);
        finishEarlyButton.setEnabled(false);

        if (backgroundSound!=null){
            backgroundSound.stop();
            backgroundSound.release();
            backgroundSound = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backgroundSound!=null){
            backgroundSound.stop();
            backgroundSound.release();
            backgroundSound = null;
        }
    }
}
