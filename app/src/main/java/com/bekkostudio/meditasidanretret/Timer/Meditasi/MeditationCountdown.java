package com.bekkostudio.meditasidanretret.Timer.Meditasi;

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

public class MeditationCountdown extends AppCompatActivity {
    PowerManager.WakeLock wakeLock;

    TextView remainingTimeWidget;
    TextView messageWidget;
    Button finishWidget;

    MediaPlayer bellSound;
    MediaPlayer finishSound;
    MediaPlayer backgroundSound;
    //audio guide
    MediaPlayer audioGuideSound;
    MediaPlayer.OnCompletionListener audioGuideCompleteListener;
    int playlistposition;

    CountDownTimer warmupTimer;
    CountDownTimer meditationTimer;

    int resultTime;

    //parameter
    int meditationDuration;
    int warmupDuration;
    int ambientMusic;
    //audio guide
    boolean dis;
    boolean pmr;
    boolean bodyscanning;
    boolean guide315;
    boolean guide426;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_countdown);

        //initialize iscompleted status
        Global.tempIsCompleted = false;

        //get parameter
        Intent intent = getIntent();
        meditationDuration = intent.getIntExtra("meditationDuration",60);
        warmupDuration = intent.getIntExtra("warmupDuration",60);
        ambientMusic = intent.getIntExtra("ambientMusic",60);
        //sequential audio
        dis = intent.getBooleanExtra("DIS",false);
        pmr = intent.getBooleanExtra("PMR",false);
        bodyscanning = intent.getBooleanExtra("bodyScanning",false);
        guide315 = intent.getBooleanExtra("315",false);
        guide426 = intent.getBooleanExtra("426",false);


        //initialize sound
        bellSound = MediaPlayer.create(this,R.raw.bell);
        finishSound = MediaPlayer.create(this,R.raw.bell2);

        if (ambientMusic != 0){
            backgroundSound = MediaPlayer.create(this,ambientMusic);
        }

        //get widget
        messageWidget = findViewById(R.id.message);
        remainingTimeWidget = findViewById(R.id.remainingTime);
        finishWidget = findViewById(R.id.finish);

        //start wakelock to keep countdown awake
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyWakelockTag");
        wakeLock.acquire();



        //run sequential audio DIS, PMR, Body Scanning, 315, 426, then Timer Countdown
        playlistposition = 0;
        audioGuideSound = MediaPlayer.create(this,R.raw.guide_dis); //Initialize dummy instance so it can be released
        //set listener
        audioGuideCompleteListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //go to next audio
                startSequentialAudio();
            }
        };

        //start
        startSequentialAudio();




        //finish early on click
        finishWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.tempIsCompleted = false;
                endMeditation();
            }
        });
    }

    private void startSequentialAudio(){
        playlistposition++;

        if (playlistposition==1 && dis){
            audioGuideSound.release();
            audioGuideSound = MediaPlayer.create(this,R.raw.guide_dis);
            audioGuideSound.setOnCompletionListener(audioGuideCompleteListener);
            audioGuideSound.start();
            messageWidget.setText("DIS");
        }else if (playlistposition==2 && pmr){
            audioGuideSound.release();
            audioGuideSound = MediaPlayer.create(this,R.raw.guide_pmr);
            audioGuideSound.setOnCompletionListener(audioGuideCompleteListener);
            audioGuideSound.start();
            messageWidget.setText("PMR");
        }else if (playlistposition==3 && bodyscanning){
            audioGuideSound.release();
            audioGuideSound = MediaPlayer.create(this,R.raw.guide_bodyscanning);
            audioGuideSound.setOnCompletionListener(audioGuideCompleteListener);
            audioGuideSound.start();
            messageWidget.setText("Body Scanning");
        }else if (playlistposition==4 && guide315){
            audioGuideSound.release();
            audioGuideSound = MediaPlayer.create(this,R.raw.guide_315);
            audioGuideSound.setOnCompletionListener(audioGuideCompleteListener);
            audioGuideSound.start();
            messageWidget.setText("315");
        }else if (playlistposition==5 && guide426){
            audioGuideSound.release();
            audioGuideSound = MediaPlayer.create(this,R.raw.guide_426);
            audioGuideSound.setOnCompletionListener(audioGuideCompleteListener);
            audioGuideSound.start();
            messageWidget.setText("426");
        }else if (playlistposition==6){
            startTimer();
        }else if (playlistposition>7){
            //just to make sure the loop stop
        }else{
            //loop to next audio
            startSequentialAudio();
        }
    }

    private void startTimer(){
        //start Warmup Timer
        bellSound.start();
        messageWidget.setText("Pemanasan");
        remainingTimeWidget.setText(formatMilliSecondsToTime(warmupDuration*1000));
        resultTime = 0;
        warmupTimer = new CountDownTimer(warmupDuration*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                remainingTimeWidget.setText(formatMilliSecondsToTime(millisUntilFinished));
            }

            public void onFinish() {
                bellSound.start();
                messageWidget.setText("Meditasi");

                if (ambientMusic != 0){
                    backgroundSound.start();
                    backgroundSound.isLooping();
                }

                //start Meditation Timer
                meditationTimer = new CountDownTimer(meditationDuration*1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        remainingTimeWidget.setText(formatMilliSecondsToTime(millisUntilFinished));
                        resultTime = (int) ((meditationDuration*1000) - millisUntilFinished);
                    }

                    public void onFinish() {
                        finishSound.start();
//                        if (ambientMusic != 0){
//                            backgroundSound.stop();
//                        }

                        Global.tempIsCompleted = true;
                        endMeditation();
                    }
                }.start();
            }
        }.start();
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
        if (audioGuideSound!=null){
            audioGuideSound.stop();
            audioGuideSound.release();
            audioGuideSound = null;
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
        //Store meditation duration to database
        Global.setDuration(getApplicationContext(),new Duration(Global.getTodayDate(),resultTime/1000));
        //Go to result page
        Intent intent = new Intent(this, MeditationResult.class);
        intent.putExtra("remainingTime", formatMilliSecondsToTime(resultTime));
        this.startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }
}
