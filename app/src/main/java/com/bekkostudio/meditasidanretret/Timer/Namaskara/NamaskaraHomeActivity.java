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
    String[] displayedSujud = {
            "10s", "20s", "30s", "1m", "3m", "5m"
    };
    String[] displayedTegap = {
            "10s", "20s", "30s", "1m", "3m", "5m"
    };

    public static final int SEPULUH_DETIK = 0;
    public static final int DUAPULUH_DETIK = 1;
    public static final int TIGAPULUH_DETIK = 2;
    public static final int SATU_MENIT = 3;
    public static final int TIGA_MENIT = 4;
    public static final int LIMA_MENIT = 5;

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
        durasiTegap.setMaxValue(displayedTegap.length-1);
        durasiTegap.setDisplayedValues(displayedTegap);
        durasiTegap.setWrapSelectorWheel(false);

        durasiSujud.setMinValue(0);
        durasiSujud.setMaxValue(displayedSujud.length-1);
        durasiSujud.setDisplayedValues(displayedSujud);
        durasiSujud.setWrapSelectorWheel(false);

        startTimer = findViewById(R.id.startTimerNamaskara);
        startTimer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(NamaskaraHomeActivity.this, NamaskaraCountdownActivity.class);
        intent.putExtra(EXTRA_DURASI_TEGAP, durasiTegap.getValue());
        intent.putExtra(EXTRA_DURASI_SUJUD, durasiSujud.getValue());
        intent.putExtra(EXTRA_SIKLUS, siklusEdit.getText().toString());
        startActivity(intent);
    }
}
