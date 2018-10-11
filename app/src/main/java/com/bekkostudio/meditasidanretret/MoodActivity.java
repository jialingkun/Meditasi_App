package com.bekkostudio.meditasidanretret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.bekkostudio.meditasidanretret.Chart.Mood;

public class MoodActivity extends AppCompatActivity implements View.OnClickListener{

    Spinner spMoodToday, spMedician;
    ArrayAdapter adapterMood, adapterMObat;
    Button btnSubmit;
    Mood mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
        btnSubmit = (Button) findViewById(R.id.btn_sumbit);
        spMoodToday = (Spinner) findViewById(R.id.spinMoodToday);
        spMedician = (Spinner) findViewById(R.id.spinMinumObat);

        adapterMood = ArrayAdapter.createFromResource(this, R.array.metode_mood, android.R.layout.simple_spinner_item);
        adapterMObat = ArrayAdapter.createFromResource(this, R.array.minum_obat, android.R.layout.simple_spinner_item);
        adapterMood.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMObat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spMoodToday.setAdapter(adapterMood);
        spMedician.setAdapter(adapterMObat);
        btnSubmit.setOnClickListener(this);
        spMoodToday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spMedician.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int statusMood = 0;
        int statusMedician = 0;

        switch (id) {
            case R.id.btn_sumbit :
                if (spMoodToday.getSelectedItem().toString().equals("Sangat Tidak Tenang")) {
                    statusMood = Mood.SANGAT_TIDAK_TENANG;
                } else if (spMoodToday.getSelectedItem().toString().equals("Tidak Tenang")) {
                    statusMood = Mood.TIDAK_TENANG;
                } else if (spMoodToday.getSelectedItem().toString().equals("Tenang")) {
                    statusMood = Mood.TENANG;
                } else if (spMoodToday.getSelectedItem().toString().equals("Sangat Tenang")) {
                    statusMood = Mood.SANGAT_TENANG;
                } else {
                    statusMood = Mood.TIDAK_MENGISI;
                }

                if (spMedician.getSelectedItem().toString().equals("Tidak Minum Obat")) {
                    statusMedician = Mood.TIDAK_MINUM_OBAT;
                } else if (spMedician.getSelectedItem().toString().equals("Kurang Dosis")) {
                    statusMedician = Mood.KURANGI_DOSIS;
                } else if (spMedician.getSelectedItem().toString().equals("Minum Obat")) {
                    statusMedician = Mood.MINUM_OBAT;
                } else {
                    statusMedician = Mood.TIDAK_MENGISI;
                }

                String dateToday = Global.getTodayDate();
                //entry data mood & medician
                mood = new Mood(dateToday, statusMood, statusMedician);

                //setMood
                Global.setMood(getApplicationContext(), mood);

                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

                //move to MainActivity
                Intent moveMainActivity = new Intent(this, MainActivity.class);
                startActivity(moveMainActivity);
                finish();
                break;
        }
    }
}
