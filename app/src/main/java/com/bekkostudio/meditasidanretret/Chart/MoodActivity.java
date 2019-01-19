package com.bekkostudio.meditasidanretret.Chart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;
import com.wefika.horizontalpicker.HorizontalPicker;

public class MoodActivity extends AppCompatActivity implements HorizontalPicker.OnItemSelected {

    boolean afterMeditation;
    Button btnSubmit;
    TextView title;
    Mood mood;

    //Mood horizontal picker
    HorizontalPicker moodValuePickerWidget;
    String[] moodItem;
    int moodValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        Intent intent = getIntent();
        afterMeditation = intent.getBooleanExtra("afterMeditation",false);

        btnSubmit = findViewById(R.id.btn_sumbit);
        //Horizontal picker library
        moodValuePickerWidget = findViewById(R.id.valueMood);
        moodValue = 1; //default 1
        moodItem = getResources().getStringArray(R.array.mood_value);
        moodValuePickerWidget.setOnItemSelectedListener(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitMood();
            }
        });

        if (afterMeditation){
            title = findViewById(R.id.moodToday);
            title.setText("Bagaimana Mood Anda setelah Meditasi?");
        }

    }

    private void submitMood(){
        if (afterMeditation){
            mood = new Mood(Global.moods.get(Global.moods.size()-1).date, Global.moods.get(Global.moods.size()-1).moodBeforeValue, moodValue);
            Global.updateLastMood(getApplicationContext(), mood);
        }else{
            mood = new Mood(Global.getTodayDate(), moodValue, moodValue);
            Global.setMood(getApplicationContext(), mood);
        }
        finish();
    }

    @Override
    public void onItemSelected(int index){
        moodValue = Integer.parseInt(moodItem[index]);
    }

    @Override
    public void onBackPressed() {
        if (afterMeditation){
            super.onBackPressed();
        }else{
//            finishAffinity();
            setResult(RESULT_OK, null);
            finish();
        }

    }
}
