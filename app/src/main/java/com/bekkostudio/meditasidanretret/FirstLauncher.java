package com.bekkostudio.meditasidanretret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class FirstLauncher extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get Mood Database
        Global.getMood(getApplicationContext());

        //check file mood
        if (Global.moods.size() > 0){
            String lastDate = Global.moods.get(Global.moods.size()-1).date;
            String todayDates = Global.getTodayDate();

            //check lastDate
            if (lastDate.equals(todayDates)) {
                intent = new Intent(FirstLauncher.this, MainActivity.class);
            } else {
                intent = new Intent(FirstLauncher.this, MoodActivity.class);
            }
        } else {
            intent = new Intent(FirstLauncher.this, MoodActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
