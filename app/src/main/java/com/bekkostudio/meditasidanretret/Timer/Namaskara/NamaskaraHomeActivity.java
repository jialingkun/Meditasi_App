package com.bekkostudio.meditasidanretret.Timer.Namaskara;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.bekkostudio.meditasidanretret.R;

public class NamaskaraHomeActivity extends AppCompatActivity implements View.OnClickListener {
    NumberPicker durasiSujud, durasiTegap;
    Button startTimer;
    EditText siklusEdit;
    String[] displayedPicker = {
            "1s", "2s", "3s", "4s", "5s", "6s", "7s", "8s", "9s", "10s"
    };

    public static final String EXTRA_DURASI_SUJUD = "extra_durasi_sujud";
    public static final String EXTRA_DURASI_TEGAP = "extra_durasi_tegap";
    public static final String EXTRA_SIKLUS = "extra_siklus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namaskara_home);

        siklusEdit = findViewById(R.id.siklusEdit);

        durasiSujud = findViewById(R.id.durasiSujud);
        durasiTegap = findViewById(R.id.durasiTegap);

        durasiTegap.setMinValue(0);
        durasiTegap.setMaxValue(displayedPicker.length-1);
        durasiTegap.setDisplayedValues(displayedPicker);
        durasiTegap.setWrapSelectorWheel(false);

        durasiSujud.setMinValue(0);
        durasiSujud.setMaxValue(displayedPicker.length-1);
        durasiSujud.setDisplayedValues(displayedPicker);
        durasiSujud.setWrapSelectorWheel(false);

        startTimer = findViewById(R.id.startTimerNamaskara);
        startTimer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(NamaskaraHomeActivity.this, NamaskaraCountdownActivity.class);
        intent.putExtra(EXTRA_DURASI_TEGAP, durasiTegap.getValue());
        intent.putExtra(EXTRA_DURASI_SUJUD, durasiSujud.getValue());
        String jumlahSiklus = siklusEdit.getText().toString();
        if (jumlahSiklus.equals("")){
            jumlahSiklus = "1";
        }
        intent.putExtra(EXTRA_SIKLUS, jumlahSiklus);
        startActivity(intent);
    }
}
