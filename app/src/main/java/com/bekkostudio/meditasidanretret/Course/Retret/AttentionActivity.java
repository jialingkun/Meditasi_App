package com.bekkostudio.meditasidanretret.Course.Retret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

public class AttentionActivity extends AppCompatActivity {
    Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_retret_attention);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set end date
                Global.setActiveRetret(getApplicationContext(),Global.activeRetretId,Global.calculateEndDate());

                //Log.d("END DATE:", "onClick: "+Global.activeRetretEndDate);

                Intent intent = new Intent(AttentionActivity.this, TimelineActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
