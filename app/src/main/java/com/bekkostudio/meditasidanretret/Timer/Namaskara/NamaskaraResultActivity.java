package com.bekkostudio.meditasidanretret.Timer.Namaskara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        durasiFinal = getDateFormat(durasi);

        siklusLabel.setText(String.valueOf(siklus));
        durasiLabel.setText(durasiFinal);
    }

    private String getDateFormat(long durasi) {
        long hours = TimeUnit.MILLISECONDS.toHours(durasi);
        durasi -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(durasi);
        durasi -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(durasi);

        String hourFinal = String.valueOf(hours);
        String minuteFinal = String.valueOf(minutes);
        String secondFinal = String.valueOf(seconds);

        if ((int) hours < 10)
            hourFinal = "0" + hourFinal;
        if ((int) minutes < 10)
            minuteFinal = "0" + minuteFinal;
        if ((int) seconds < 10)
            secondFinal = "0" + secondFinal;

        return hourFinal + ":" + minuteFinal + ":" + secondFinal;
    }
}
