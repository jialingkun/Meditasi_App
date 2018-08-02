package com.bekkostudio.meditasidanretret;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class TimerFragment extends Fragment {

    //reference widget in xml
    TextView recentMeditationWidget0;
    TextView recentMeditationWidget1;
    TextView recentMeditationWidget2;

    NumberPicker hoursDurationWidget;
    NumberPicker minutesDurationWidget;
    NumberPicker secondsDurationWidget;
    EditText warmupDurationWidget;
    EditText ambientMusicWidget;
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

        ambientMusicWidget = view.findViewById(R.id.ambientmusic);
        warmupDurationWidget = view.findViewById(R.id.warmupDuration);

        startTimerWidget = view.findViewById(R.id.startTimer);
        startTimerWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get parameter before start Timer
                meditationDuration = (hoursDurationWidget.getValue()*3600)+(minutesDurationWidget.getValue()*60)+(secondsDurationWidget.getValue());
                warmupDuration = Integer.parseInt(warmupDurationWidget.getText().toString());
                switch (Integer.parseInt(ambientMusicWidget.getText().toString())) {
                    case 0:
                        ambientMusic = R.raw.butterfly_space;
                        break;
                    case 1:
                        ambientMusic = R.raw.mt_airy;
                        break;
                    case 2:
                        ambientMusic = R.raw.weaving;
                        break;
                    default:
                        ambientMusic = 0;
                }

                //start timer
                Global.StartTimer(getActivity().getApplicationContext(),meditationDuration,warmupDuration,ambientMusic);
            }
        });

        return view;
    }


    public void loadRecentMeditation(){
        recentMeditationWidget0.setText(Global.recentMeditation.get(0));
        recentMeditationWidget1.setText(Global.recentMeditation.get(1));
        recentMeditationWidget2.setText(Global.recentMeditation.get(2));
    }
}
