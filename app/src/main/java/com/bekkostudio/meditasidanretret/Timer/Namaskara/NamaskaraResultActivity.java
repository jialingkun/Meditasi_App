package com.bekkostudio.meditasidanretret.Timer.Namaskara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

import java.util.concurrent.TimeUnit;

public class NamaskaraResultActivity extends AppCompatActivity {
    Button doneButton;
    TextView siklusLabel, durasiLabel;
    int siklus;
    long durasi;
    String durasiFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namaskara_result);

        doneButton = findViewById(R.id.done);
        siklusLabel = findViewById(R.id.siklusResultLabel);
        durasiLabel = findViewById(R.id.durasiResultLabel);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        siklus = getIntent().getIntExtra(NamaskaraCountdownActivity.EXTRA_SIKLUS, 0);
        durasi = getIntent().getLongExtra(NamaskaraCountdownActivity.EXTRA_DURASI, 0);

        // formatting millisecond to hour, minute, second
        durasiFinal = Global.getDateFormat(durasi);

        siklusLabel.setText(String.valueOf(siklus));
        durasiLabel.setText(durasiFinal);
    }
}
