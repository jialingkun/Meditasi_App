package com.bekkostudio.meditasidanretret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MeditationResult extends AppCompatActivity {

    TextView resultTimeWidget;
    Button doneWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_result);

        //get parameter
        Intent intent = getIntent();
        String remainingTime = intent.getStringExtra("remainingTime");

        //get widget
        resultTimeWidget = findViewById(R.id.resultTime);
        doneWidget = findViewById(R.id.done);

        resultTimeWidget.setText(remainingTime);
        doneWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
