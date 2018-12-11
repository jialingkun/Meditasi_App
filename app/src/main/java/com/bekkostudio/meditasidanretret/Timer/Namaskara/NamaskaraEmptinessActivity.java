package com.bekkostudio.meditasidanretret.Timer.Namaskara;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.CenteringHorizontalScrollView;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

import java.util.ArrayList;

public class NamaskaraEmptinessActivity extends AppCompatActivity implements View.OnClickListener {
    NumberPicker durasiSujud, durasiTegap;
    Button startTimer;
    EditText siklusEdit;
    String[] displayedPicker = {
            "1s", "1.5s", "2s", "2.5s", "3s", "3.5s", "4s", "4.5s", "5s"
    };

    public static final String EXTRA_DURASI_SUJUD = "extra_durasi_sujud";
    public static final String EXTRA_DURASI_TEGAP = "extra_durasi_tegap";
    public static final String EXTRA_SIKLUS = "extra_siklus";
    public static final String EXTRA_PANDUAN = "extra_panduan";



    // horizontal scroll view for nian fo method
    CenteringHorizontalScrollView panduanScrollView;
    LinearLayout panduanLinear;
    ArrayList<TextView> arrayTvPanduan;
    private String[] panduanNamaskara = {"Aktif", "Non-Aktif"};
    String panduanSelected = "Aktif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namaskara_emptiness);

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

        arrayTvPanduan = new ArrayList<>();

        // nian fo method picker
        panduanScrollView = findViewById(R.id.panduanScrollView);
        panduanLinear = findViewById(R.id.panduanLinear);

        TextView tvPanduan;
        LinearLayout.LayoutParams paramsPanduan;

        //Set text view width
        int tvPanduanWidth = 100;
        int tvPanduanHeight = 50;
        int tvPanduanMargin = 5;

        //set gap to center first item and last item
        panduanScrollView.setLeftRightGap(this,tvPanduanWidth);

        for(int i = 0; i< panduanNamaskara.length; i++){
            tvPanduan = new TextView(this);
            tvPanduan.setText(panduanNamaskara[i]);

            //set height width
            paramsPanduan = new LinearLayout.LayoutParams(Global.dpToPx(this, tvPanduanWidth), Global.dpToPx(this,tvPanduanHeight));
            if (i==0){
                //If first item
                paramsPanduan.setMargins(0,0,Global.dpToPx(this,tvPanduanMargin),0);
                tvPanduan.setTypeface(null, Typeface.BOLD);
            }else if (i==Global.ambientImageItem.length-1){
                //if last item
                paramsPanduan.setMargins(Global.dpToPx(this,tvPanduanMargin),0,0,0);
            }else{
                paramsPanduan.setMargins(Global.dpToPx(this,tvPanduanMargin),0,Global.dpToPx(this,tvPanduanMargin),0);
            }

            tvPanduan.setGravity(Gravity.CENTER);
            tvPanduan.setTextSize(18);
            tvPanduan.setLayoutParams(paramsPanduan);
            arrayTvPanduan.add(tvPanduan);
            panduanLinear.addView(tvPanduan);
        }

        panduanScrollView.setCurrentItemAndCenter(0);

        panduanScrollView.setCustomListener(new CenteringHorizontalScrollView.CustomListener() {
            @Override
            public void onItemSelected(int activeItem) {
                panduanSelected = panduanNamaskara[activeItem];
                if (activeItem == 0) {
                    arrayTvPanduan.get(0).setTypeface(null, Typeface.BOLD);
                    arrayTvPanduan.get(1).setTypeface(null, Typeface.NORMAL);
                }
                else {
                    arrayTvPanduan.get(0).setTypeface(null, Typeface.NORMAL);
                    arrayTvPanduan.get(1).setTypeface(null, Typeface.BOLD);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(NamaskaraEmptinessActivity.this, NamaskaraCountdownActivity.class);
        float valueTegap = Float.parseFloat(displayedPicker[durasiTegap.getValue()].substring(0,displayedPicker[durasiTegap.getValue()].length()-1));
        float valueSujud = Float.parseFloat(displayedPicker[durasiSujud.getValue()].substring(0,displayedPicker[durasiSujud.getValue()].length()-1));
        intent.putExtra(EXTRA_DURASI_TEGAP, valueTegap);
        intent.putExtra(EXTRA_DURASI_SUJUD, valueSujud);
        String jumlahSiklus = siklusEdit.getText().toString();
        if (jumlahSiklus.equals("")){
            jumlahSiklus = "1";
        }
        if(panduanSelected.equals("Aktif")) {
            intent.putExtra(EXTRA_PANDUAN, true);
        }
        intent.putExtra(EXTRA_SIKLUS, jumlahSiklus);
        startActivity(intent);
    }
}
