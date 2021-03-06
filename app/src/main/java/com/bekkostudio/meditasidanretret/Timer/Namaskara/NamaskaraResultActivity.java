package com.bekkostudio.meditasidanretret.Timer.Namaskara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bekkostudio.meditasidanretret.Chart.Note;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

import java.util.concurrent.TimeUnit;

public class NamaskaraResultActivity extends AppCompatActivity {
    Button doneButton;
    TextView siklusLabel, durasiLabel;
    int siklus;
    long durasi;
    String durasiFinal;

    //note
    EditText noteWidget;
    CheckBox importantWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namaskara_result);

        doneButton = findViewById(R.id.done);
        siklusLabel = findViewById(R.id.siklusResultLabel);
        durasiLabel = findViewById(R.id.durasiResultLabel);

        //note
        noteWidget = findViewById(R.id.note);
        importantWidget = findViewById(R.id.important);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endMeditation();
            }
        });

        siklus = getIntent().getIntExtra(NamaskaraCountdownActivity.EXTRA_SIKLUS, 0);
        durasi = getIntent().getLongExtra(NamaskaraCountdownActivity.EXTRA_DURASI, 0);

        // formatting millisecond to hour, minute, second
        durasiFinal = Global.getDateFormat(durasi);

        siklusLabel.setText(String.valueOf(siklus));
        durasiLabel.setText(durasiFinal);
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
