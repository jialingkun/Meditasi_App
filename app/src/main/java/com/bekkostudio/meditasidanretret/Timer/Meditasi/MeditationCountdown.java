package com.bekkostudio.meditasidanretret.Timer.Meditasi;

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

    Intent intent;

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
    String[] checkboxItem;
    int[] audioSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_countdown);

        //initialize iscompleted status
        Global.tempIsCompleted = false;

        //get parameter
        intent = getIntent();
        meditationDuration = intent.getIntExtra("meditationDuration",60);
        warmupDuration = intent.getIntExtra("warmupDuration",60);
        ambientMusic = intent.getIntExtra("ambientMusic",60);
        //sequential audio
        checkboxItem = getResources().getStringArray(R.array.checkbox_value);
        audioSource = new int[checkboxItem.length];
        audioSource[0] = R.raw.guide_dis;
        audioSource[1] = R.raw.guide_standingbab;
        audioSource[2] = R.raw.guide_pmr;
        audioSource[3] = R.raw.guide_pme;
        audioSource[4] = R.raw.guide_adjustingposture;
        audioSource[5] = R.raw.guide_315and426;
        audioSource[6] = R.raw.guide_sittingbab;
        audioSource[7] = R.raw.guide_bodyscanning;
        audioSource[8] = R.raw.guide_justsitting;
        audioSource[9] = R.raw.guide_abdominalbreathing;
        audioSource[10] = R.raw.guide_experiencingthebreath;
        audioSource[11] = R.raw.guide_countingthebreath;
        audioSource[12] = R.raw.guide_followingthebreath;


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



        //run sequential audio then Timer Countdown
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
        boolean isGuiding = false;

        for (int i = 0;i<checkboxItem.length;i++){
            Log.d("SEQUENT", "Checkbox Value "+i+":"+intent.getBooleanExtra(checkboxItem[i],false));
            if (playlistposition==(i+1) && intent.getBooleanExtra(checkboxItem[i],false)){
                audioGuideSound.release();
                audioGuideSound = MediaPlayer.create(this,audioSource[i]);
                audioGuideSound.setOnCompletionListener(audioGuideCompleteListener);
                audioGuideSound.start();
                messageWidget.setText(checkboxItem[i]);
                isGuiding = true;
                break;
            }
        }

        if (!isGuiding){
            if (playlistposition==(checkboxItem.length+1)){
                startTimer();
            }else if (playlistposition>(checkboxItem.length+2)){
                //just to make sure the loop stop
            }else{
                //loop to next audio
                startSequentialAudio();
            }
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
        if (finishSound!=null){
            finishSound.stop();
            finishSound.release();
            finishSound = null;
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
        if (warmupTimer!=null){warmupTimer.cancel();}
        if (meditationTimer!=null){meditationTimer.cancel();}
        if (audioGuideSound!=null){audioGuideSound.stop();}
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
