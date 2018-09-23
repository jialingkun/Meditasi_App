package com.bekkostudio.meditasidanretret.Timer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;
import com.bekkostudio.meditasidanretret.CenteringHorizontalScrollView;
import com.bekkostudio.meditasidanretret.Timer.Namaskara.NamaskaraHomeActivity;
import com.wefika.horizontalpicker.HorizontalPicker;

public class TimerFragment extends Fragment implements HorizontalPicker.OnItemSelected{

    //reference widget in xml
    TextView recentMeditationWidget0;
    TextView recentMeditationWidget1;
    TextView recentMeditationWidget2;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_timer_fragment, container, false);
        //for trigger reloading recent Meditation from another class
        Global.lastTimerFragmentObject = this;

        recentMeditationWidget0 = view.findViewById(R.id.recentMeditation0);
        recentMeditationWidget1 = view.findViewById(R.id.recentMeditation1);
        recentMeditationWidget2 = view.findViewById(R.id.recentMeditation2);
        loadRecentMeditation();

        hoursDurationWidget = view.findViewById(R.id.hoursDuration);
        hoursDurationWidget.setMinValue(0);
        hoursDurationWidget.setMaxValue(23);

        minutesDurationWidget = view.findViewById(R.id.minutesDuration);
        minutesDurationWidget.setMinValue(0);
        minutesDurationWidget.setMaxValue(59);
        minutesDurationWidget.setValue(1);

        secondsDurationWidget = view.findViewById(R.id.secondsDuration);
        secondsDurationWidget.setMinValue(0);
        secondsDurationWidget.setMaxValue(59);

        //Horizontal picker library
        warmupDurationPickerWidget = view.findViewById(R.id.warmupduration);
        warmupDuration = 5; //default 5 seconds
        warmupItem = getResources().getStringArray(R.array.warmup_value);
        warmupDurationPickerWidget.setOnItemSelectedListener(this);

        //BGM picker
        ambientMusicScrollWidget = view.findViewById(R.id.centerHorizontalScrollView);
        ambientMusicPickerWidget = view.findViewById(R.id.ambientmusic);

        ImageView image;
        LinearLayout.LayoutParams params;

        //Set the width here
        int imageWidth = 120;
        int imageHeight = 120;
        int imageMargin = 10;

        //set gap to center first item and last item
        ambientMusicScrollWidget.setLeftRightGap(getActivity(),imageWidth);

        // center thumbnail
        for(int i=0; i<Global.ambientImageItem.length; i++){
            image=new ImageView(getContext());
            //set image
            image.setImageResource(Global.ambientImageItem[i]);
            //set height width
            params = new LinearLayout.LayoutParams(Global.dpToPx(getContext(), imageWidth), Global.dpToPx(getContext(),imageHeight));
            if (i==0){
                //If first item
                params.setMargins(0,0,Global.dpToPx(getContext(),imageMargin),0);
            }else if (i==Global.ambientImageItem.length-1){
                //if last item
                params.setMargins(Global.dpToPx(getContext(),imageMargin),0,0,0);
            }else{
                params.setMargins(Global.dpToPx(getContext(),imageMargin),0,Global.dpToPx(getContext(),imageMargin),0);
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
        startTimerWidget = view.findViewById(R.id.startTimer);
        startTimerWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get parameter before start Timer
                /*meditationDuration = (hoursDurationWidget.getValue()*3600)+(minutesDurationWidget.getValue()*60)+(secondsDurationWidget.getValue());
//                Log.d("Ambient index", "onClick: "+ambientMusicScrollWidget.getActiveItem());

                ambientMusic = Global.ambientMusicItem[ambientMusicScrollWidget.getActiveItem()];
                //start timer
                Global.startTimer(getActivity(),meditationDuration,warmupDuration,ambientMusic);*/
                Intent intent = new Intent(getContext(), NamaskaraHomeActivity.class);
                getActivity().startActivity(intent);
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
                    backgroundSound = MediaPlayer.create(getActivity(),musicId);
                    backgroundSound.start();
                }
            }
        });

        return view;
    }

    @Override
    public void onPause() {
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


    public void loadRecentMeditation(){
        recentMeditationWidget0.setText(Global.recentMeditation.get(0));
        recentMeditationWidget1.setText(Global.recentMeditation.get(1));
        recentMeditationWidget2.setText(Global.recentMeditation.get(2));
    }
}
