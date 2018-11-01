package com.bekkostudio.meditasidanretret.Timer.NianFo;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bekkostudio.meditasidanretret.CenteringHorizontalScrollView;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

import java.util.ArrayList;

public class NianFoHomeActivity extends AppCompatActivity {
    // horizontal scroll view for nian fo method
    CenteringHorizontalScrollView metodeScrollView;
    LinearLayout metodeLinear;
    ArrayList<TextView> arrayTvMetode;

    //BGM image picker
    CenteringHorizontalScrollView ambientMusicScrollWidget;
    LinearLayout ambientMusicPickerWidget;

    //Sound on click BGM image
    MediaPlayer backgroundSound;

    Button startButton;
    EditText siklusEdit;

    int musicId;

    public static String EXTRA_MUSIC_ID = "extra_music_id";
    public static final String EXTRA_SIKLUS = "extra_siklus";

    private String[] metodeNianFo = {"Manual", "Automatic"};
    String metodeSelected = "Manual";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nian_fo_home);

        arrayTvMetode = new ArrayList<>();

        startButton = findViewById(R.id.startTimerNianFo);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicId == 0) {
                    Toast.makeText(NianFoHomeActivity.this, "Please select a spell", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = null;
                if(metodeSelected.equals("Manual")) {
                    intent = new Intent(NianFoHomeActivity.this, NianFoManualActivity.class);
                }
                else if (metodeSelected.equals("Automatic")) {
                    intent = new Intent(NianFoHomeActivity.this, NianFoAutomaticActivity.class);
                }
                intent.putExtra(EXTRA_MUSIC_ID, musicId);
                String jumlahSiklus = siklusEdit.getText().toString();
                if (jumlahSiklus.equals("")){
                    jumlahSiklus = "1";
                }
                intent.putExtra(EXTRA_SIKLUS, jumlahSiklus);
                startActivity(intent);
            }
        });

        siklusEdit = findViewById(R.id.siklusEdit);

        // nian fo method picker
        metodeScrollView = findViewById(R.id.metodeScrollView);
        metodeLinear = findViewById(R.id.metodeLinear);

        TextView tvMetode;
        LinearLayout.LayoutParams paramsMetode;

        //Set text view width
        int tvMetodeWidth = 100;
        int tvMetodeHeight = 80;
        int tvMetodeMargin = 10;

        //set gap to center first item and last item
        metodeScrollView.setLeftRightGap(this,tvMetodeWidth);

        for(int i = 0; i< metodeNianFo.length; i++){
            tvMetode = new TextView(this);
            tvMetode.setText(metodeNianFo[i]);

            //set height width
            paramsMetode = new LinearLayout.LayoutParams(Global.dpToPx(this, tvMetodeWidth), Global.dpToPx(this,tvMetodeHeight));
            if (i==0){
                //If first item
                paramsMetode.setMargins(0,0,Global.dpToPx(this,tvMetodeMargin),0);
                tvMetode.setTypeface(null, Typeface.BOLD);
            }else if (i==Global.ambientImageItem.length-1){
                //if last item
                paramsMetode.setMargins(Global.dpToPx(this,tvMetodeMargin),0,0,0);
            }else{
                paramsMetode.setMargins(Global.dpToPx(this,tvMetodeMargin),0,Global.dpToPx(this,tvMetodeMargin),0);
            }

            tvMetode.setGravity(Gravity.CENTER);
            tvMetode.setTextSize(18);
            tvMetode.setLayoutParams(paramsMetode);
            arrayTvMetode.add(tvMetode);
            metodeLinear.addView(tvMetode);
        }

        metodeScrollView.setCurrentItemAndCenter(0);

        metodeScrollView.setCustomListener(new CenteringHorizontalScrollView.CustomListener() {
            @Override
            public void onItemSelected(int activeItem) {
                metodeSelected = metodeNianFo[activeItem];
                if (activeItem == 0) {
                    arrayTvMetode.get(0).setTypeface(null, Typeface.BOLD);
                    arrayTvMetode.get(1).setTypeface(null, Typeface.NORMAL);
                }
                else {
                    arrayTvMetode.get(0).setTypeface(null, Typeface.NORMAL);
                    arrayTvMetode.get(1).setTypeface(null, Typeface.BOLD);
                }
            }
        });

        //BGM picker
        ambientMusicScrollWidget = findViewById(R.id.centerHorizontalScrollView);
        ambientMusicPickerWidget = findViewById(R.id.mantraMusic);

        ImageView image;
        LinearLayout.LayoutParams params;

        //Set the width here
        int imageWidth = 120;
        int imageHeight = 120;
        int imageMargin = 10;

        //set gap to center first item and last item
        ambientMusicScrollWidget.setLeftRightGap(this,imageWidth);

        // center thumbnail
        for(int i = 0; i< Global.ambientImageItem.length; i++){
            image=new ImageView(this);
            //set image
            image.setImageResource(Global.ambientImageItem[i]);
            //set height width
            params = new LinearLayout.LayoutParams(Global.dpToPx(this, imageWidth), Global.dpToPx(this,imageHeight));
            if (i==0){
                //If first item
                params.setMargins(0,0,Global.dpToPx(this,imageMargin),0);
            }else if (i==Global.ambientImageItem.length-1){
                //if last item
                params.setMargins(Global.dpToPx(this,imageMargin),0,0,0);
            }else{
                params.setMargins(Global.dpToPx(this,imageMargin),0,Global.dpToPx(this,imageMargin),0);
            }
            image.setLayoutParams(params);
            //add to Linearlayout
            ambientMusicPickerWidget.addView(image);
        }

        //default first item
        ambientMusicScrollWidget.setCurrentItemAndCenter(0);

        //listener on scroll view item selected
        ambientMusicScrollWidget.setCustomListener(new CenteringHorizontalScrollView.CustomListener() {
            @Override
            public void onItemSelected(int activeItem) {
                //On image click
                musicId = Global.nianFoMusicItem[activeItem];
                if (backgroundSound!=null){
                    backgroundSound.stop();
                    backgroundSound.release();
                    backgroundSound = null;
                }
                if (musicId!=0){
                    backgroundSound = MediaPlayer.create(NianFoHomeActivity.this,musicId);
                    backgroundSound.start();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (backgroundSound!=null){
            backgroundSound.stop();
            backgroundSound.release();
            backgroundSound = null;
        }
    }
}
