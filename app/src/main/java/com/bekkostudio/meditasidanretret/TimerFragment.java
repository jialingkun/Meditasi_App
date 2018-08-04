package com.bekkostudio.meditasidanretret;

import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        int imageMargin = 20;

        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;

        //First thumbnail
        image=new ImageView(getContext());
        image.setImageResource(Global.ambientImageItem[0]);
        params = new LinearLayout.LayoutParams(Global.dpToPx(getContext(), imageWidth), Global.dpToPx(getContext(),imageWidth));
        params.setMargins((screenWidth/2)-Global.dpToPx(getContext(),(imageWidth/2)),0,Global.dpToPx(getContext(),imageMargin),0);
        image.setLayoutParams(params);
        ambientMusicPickerWidget.addView(image);

        // center thumbnail
        for(int i=1; i<Global.ambientImageItem.length-1; i++){
            image=new ImageView(getContext());
            image.setImageResource(Global.ambientImageItem[i]);
            params = new LinearLayout.LayoutParams(Global.dpToPx(getContext(), imageWidth), Global.dpToPx(getContext(),imageWidth));
            params.setMargins(Global.dpToPx(getContext(),imageMargin),0,Global.dpToPx(getContext(),imageMargin),0);
            image.setLayoutParams(params);
            ambientMusicPickerWidget.addView(image);
        }

        //Last Thumbnail
        image=new ImageView(getContext());
        image.setImageResource(Global.ambientImageItem[Global.ambientImageItem.length-1]);
        params = new LinearLayout.LayoutParams(Global.dpToPx(getContext(), imageWidth), Global.dpToPx(getContext(),imageWidth));
        params.setMargins(Global.dpToPx(getContext(),imageMargin),0,(screenWidth/2)-Global.dpToPx(getContext(),(imageWidth/2)),0);
        image.setLayoutParams(params);
        ambientMusicPickerWidget.addView(image);

        for(int i=Global.ambientImageItem.length-2; i>0; i--){

            ambientMusicScrollWidget.setCurrentItemAndCenter(i);
        }

        ambientMusicScrollWidget.setCurrentItemAndCenter(0);



        //Click start
        startTimerWidget = view.findViewById(R.id.startTimer);
        startTimerWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get parameter before start Timer
                meditationDuration = (hoursDurationWidget.getValue()*3600)+(minutesDurationWidget.getValue()*60)+(secondsDurationWidget.getValue());
//                Log.d("Ambient index", "onClick: "+ambientMusicScrollWidget.getActiveItem());

                ambientMusic = Global.ambientMusicItem[ambientMusicScrollWidget.getActiveItem()];
                //start timer
                Global.StartTimer(getActivity().getApplicationContext(),meditationDuration,warmupDuration,ambientMusic);
            }
        });

        return view;
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
