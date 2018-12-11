package com.bekkostudio.meditasidanretret.Timer.Namaskara;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Chart.Note;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

public class NamaskaraAudioResultActivity extends AppCompatActivity {

    TextView resultTimeWidget;
    Button doneWidget;

    //note
    EditText noteWidget;
    CheckBox importantWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namaskara_audio_result);

        //get parameter
        Intent intent = getIntent();
        String remainingTime = intent.getStringExtra("remainingTime");

        //get widget
        resultTimeWidget = findViewById(R.id.resultTime);
        doneWidget = findViewById(R.id.done);

        //note
        noteWidget = findViewById(R.id.note);
        importantWidget = findViewById(R.id.important);

        resultTimeWidget.setText(remainingTime);

        //Click Done
        doneWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endMeditation();
            }
        });
    }

    @Override
    public void onBackPressed() {
        endMeditation();
        return;
    }


    public void endMeditation(){
        saveNote();
        finish();
    }


    public void saveNote(){
        if (noteWidget.getText().length()>0){
            //set note
            String text = noteWidget.getText().toString();
            boolean important = importantWidget.isChecked();
            String dateToday = Global.getTodayDate();
            Note note = new Note(dateToday, text, important);
            Global.setNote(getApplicationContext(), note);
        }
    }
}
