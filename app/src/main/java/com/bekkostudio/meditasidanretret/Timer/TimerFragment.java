package com.bekkostudio.meditasidanretret.Timer;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bekkostudio.meditasidanretret.Course.Retret.RetretActivity;
import com.bekkostudio.meditasidanretret.Course.ShopActivity;
import com.bekkostudio.meditasidanretret.Course.TutorialActivity;
import com.bekkostudio.meditasidanretret.R;
public class TimerFragment extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_timer_fragment, container, false);

        Button meditasi = view.findViewById(R.id.meditasi);
        Button namaskara = view.findViewById(R.id.namaskara);
        Button nianfo = view.findViewById(R.id.nianfo);

        meditasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MeditationSetting.class);
                getActivity().startActivity(intent);
            }
        });

        namaskara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NAMASKARA GANTI INTENT DI BAWAH DENGAN CLASS NAMASKARA
                Intent intent = new Intent(getActivity(), MeditationSetting.class);
                getActivity().startActivity(intent);
            }
        });

        nianfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GANTI INTENT DI BAWAH DENGAN CLASS NIANFO
                Intent intent = new Intent(getActivity(), MeditationSetting.class);
                getActivity().startActivity(intent);
            }
        });


        return view;
    }
}