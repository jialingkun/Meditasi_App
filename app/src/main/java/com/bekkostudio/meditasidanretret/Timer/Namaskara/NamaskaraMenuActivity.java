package com.bekkostudio.meditasidanretret.Timer.Namaskara;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bekkostudio.meditasidanretret.R;


public class NamaskaraMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namaskara_menu);

        ImageView emptiness = findViewById(R.id.emptiness);
        ImageView gratitude = findViewById(R.id.gratitude);
        ImageView repentance = findViewById(R.id.repentance);

        emptiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NamaskaraMenuActivity.this, NamaskaraEmptinessActivity.class);
                startActivity(intent);
            }
        });

        gratitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NamaskaraMenuActivity.this, NamaskaraGratitudeActivity.class);
                startActivity(intent);
            }
        });

        repentance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NamaskaraMenuActivity.this, NamaskaraRepentanceActivity.class);
                startActivity(intent);
            }
        });
    }
}
