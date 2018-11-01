package com.bekkostudio.meditasidanretret.Timer.Meditasi;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.bekkostudio.meditasidanretret.CenteringHorizontalScrollView;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;
import com.wefika.horizontalpicker.HorizontalPicker;

public class MeditationSetting extends AppCompatActivity implements HorizontalPicker.OnItemSelected {

    //reference widget in xml
    NumberPicker hoursDurationWidget;
    NumberPicker minutesDurationWidget;
    NumberPicker secondsDurationWidget;

    //warmup horizontal picker
    HorizontalPicker warmupDurationPickerWidget;
    String[] warmupItem;

    //BGM image picker
    CenteringHorizontalScrollView ambientMusicScrollWidget;
    LinearLayout ambientMusicPickerWidget;
    //Sound on click BGM image
    MediaPlayer backgroundSound;

    Button startTimerWidget;

    //parameter to be used in countdown timer
    int meditationDuration;
    int warmupDuration;
    int ambientMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_setting);

        hoursDurationWidget = findViewById(R.id.hoursDuration);
        hoursDurationWidget.setMinValue(0);
        hoursDurationWidget.setMaxValue(23);

        minutesDurationWidget = findViewById(R.id.minutesDuration);
        minutesDurationWidget.setMinValue(0);
        minutesDurationWidget.setMaxValue(59);
        minutesDurationWidget.setValue(1);

        secondsDurationWidget = findViewById(R.id.secondsDuration);
        secondsDurationWidget.setMinValue(0);
        secondsDurationWidget.setMaxValue(59);

        //Horizontal picker library
        warmupDurationPickerWidget = findViewById(R.id.warmupduration);
        warmupDuration = 5; //default 5 seconds
        warmupItem = getResources().getStringArray(R.array.warmup_value);
        warmupDurationPickerWidget.setOnItemSelectedListener(this);

        //BGM picker
        ambientMusicScrollWidget = findViewById(R.id.centerHorizontalScrollView);
        ambientMusicPickerWidget = findViewById(R.id.ambientmusic);

        ImageView image;
        LinearLayout.LayoutParams params;

        //Set the width here
        int imageWidth = 120;
        int imageHeight = 120;
        int imageMargin = 10;

        //set gap to center first item and last item
        ambientMusicScrollWidget.setLeftRightGap(this,imageWidth);

        // center thumbnail
        for(int i=0; i<Global.ambientImageItem.length; i++){
            image=new ImageView(this);
            //set image
            image.setImageResource(Global.ambientImageItem[i]);
            //set height width
            params = new LinearLayout.LayoutParams(Global.dpToPx(this, imageWidth), Global.dpToPx(this,imageHeight));
            if (i==0){
                //If first item
                params.setMargins(0,0,Global.dpToPx(this,imageMargin),0);
            }else if (i==Global.ambientImageItem.length-1){
                //if last item
                params.setMargins(Global.dpToPx(this,imageMargin),0,0,0);
            }else{
                params.setMargins(Global.dpToPx(this,imageMargin),0,Global.dpToPx(this,imageMargin),0);
            }
            image.setLayoutParams(params);
            //add to Linearlayout
            ambientMusicPickerWidget.addView(image);

//            //On image click
//            final int musicId = Global.ambientMusicItem[i]; //final to force this variable to go inside onclicklistener
//            image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (backgroundSound!=null){
//                        backgroundSound.stop();
//                        backgroundSound.release();
//                        backgroundSound = null;
//                    }
//                    if (musicId!=0){
//                        backgroundSound = MediaPlayer.create(getActivity(),musicId);
//                        backgroundSound.start();
//                    }
//
//                }
//            });
        }

        //default first item
        ambientMusicScrollWidget.setCurrentItemAndCenter(0);


        //Click start
        startTimerWidget = findViewById(R.id.startTimer);
        startTimerWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get parameter before start Timer
                meditationDuration = (hoursDurationWidget.getValue()*3600)+(minutesDurationWidget.getValue()*60)+(secondsDurationWidget.getValue());
//                Log.d("Ambient index", "onClick: "+ambientMusicScrollWidget.getActiveItem());

                ambientMusic = Global.ambientMusicItem[ambientMusicScrollWidget.getActiveItem()];
                //start timer
                Global.startTimer(MeditationSetting.this,meditationDuration,warmupDuration,ambientMusic);
            }
        });

        //listener on scroll view item selected
        ambientMusicScrollWidget.setCustomListener(new CenteringHorizontalScrollView.CustomListener() {
            @Override
            public void onItemSelected(int activeItem) {
                //On image click
                int musicId = Global.ambientMusicItem[activeItem];
                if (backgroundSound!=null){
                    backgroundSound.stop();
                    backgroundSound.release();
                    backgroundSound = null;
                }
                if (musicId!=0){
                    backgroundSound = MediaPlayer.create(MeditationSetting.this,musicId);
                    backgroundSound.start();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        if (backgroundSound!=null){
            backgroundSound.stop();
            backgroundSound.release();
            backgroundSound = null;
        }
        super.onPause();
    }

    @Override
    public void onItemSelected(int index){

        String timeUnit = warmupItem[index].substring(warmupItem[index].length()-1);
        int timeValue = Integer.parseInt(warmupItem[index].substring(0,warmupItem[index].length()-1));
        //Log.d("TIME UNIT", "onItemSelected: "+ timeUnit);
        //Log.d("TIME VALUE", "onItemSelected: "+ timeValue);
        switch (timeUnit){
            case "s":
                warmupDuration = timeValue;
                break;
            case "m":
                warmupDuration = timeValue*60;
                break;
            case "h":
                warmupDuration = timeValue*3600;
                break;
            default:
                warmupDuration = timeValue;
        }
    }

}
