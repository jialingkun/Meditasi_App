package com.bekkostudio.meditasidanretret;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
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
        ambientMusicPickerWidget = view.findViewById(R.id.ambientmusic);
        for(int i=0; i<Global.ambientImageItem.length; i++){
            ImageView image=new ImageView(getContext());
            image.setImageResource(Global.ambientImageItem[i]);
            ambientMusicPickerWidget.addView(image);
        }

        //Click start
        startTimerWidget = view.findViewById(R.id.startTimer);
        startTimerWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get parameter before start Timer
                meditationDuration = (hoursDurationWidget.getValue()*3600)+(minutesDurationWidget.getValue()*60)+(secondsDurationWidget.getValue());
//                switch (Integer.parseInt(ambientMusicWidget.getText().toString())) {
//                    case 0:
//                        ambientMusic = R.raw.butterfly_space;
//                        break;
//                    case 1:
//                        ambientMusic = R.raw.mt_airy;
//                        break;
//                    case 2:
//                        ambientMusic = R.raw.weaving;
//                        break;
//                    default:
//                        ambientMusic = 0;
//                }

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
