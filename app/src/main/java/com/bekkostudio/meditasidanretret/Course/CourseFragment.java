package com.bekkostudio.meditasidanretret.Course;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bekkostudio.meditasidanretret.Course.Retret.RetretActivity;
import com.bekkostudio.meditasidanretret.R;

public class CourseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_course_fragment, container, false);

        Button retret = view.findViewById(R.id.retret);
        Button tutorial = view.findViewById(R.id.tutorial);
        Button shop = view.findViewById(R.id.shop);

        retret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RetretActivity.class);
                getActivity().startActivity(intent);
            }
        });

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TutorialActivity.class);
                getActivity().startActivity(intent);
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShopActivity.class);
                getActivity().startActivity(intent);
            }
        });


        return view;
    }
}
