package com.bekkostudio.meditasidanretret.Timer.Meditasi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.bekkostudio.meditasidanretret.CenteringHorizontalScrollView;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;
import com.wefika.horizontalpicker.HorizontalPicker;

public class MeditationSettingAudio extends AppCompatActivity{

    //audio guide
    String [] checkboxItem;
    boolean[] audioCheckStatus;

    Button nextWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_setting_audio);

        //Run mood survey if not filling the survey yet
        Global.showMoodSurvey(this,false);

        //audio guide
        checkboxItem = getResources().getStringArray(R.array.checkbox_value);
        audioCheckStatus = new boolean[checkboxItem.length];

        //Click start
        nextWidget = findViewById(R.id.startTimer);
        nextWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get checkbox status
                audioCheckStatus[0] = ((CheckBox) findViewById(R.id.checkDIS)).isChecked();
                audioCheckStatus[1] = ((CheckBox) findViewById(R.id.checkStandingBAB)).isChecked();
                audioCheckStatus[2] = ((CheckBox) findViewById(R.id.checkPMR)).isChecked();
                audioCheckStatus[3] = ((CheckBox) findViewById(R.id.checkPME)).isChecked();
                audioCheckStatus[4] = ((CheckBox) findViewById(R.id.checkAdjustingPosture)).isChecked();
                audioCheckStatus[5] = ((CheckBox) findViewById(R.id.check315and426)).isChecked();
                audioCheckStatus[6] = ((CheckBox) findViewById(R.id.checkSittingBAB)).isChecked();
                audioCheckStatus[7] = ((CheckBox) findViewById(R.id.checkBodyScanning)).isChecked();
                audioCheckStatus[8] = ((CheckBox) findViewById(R.id.checkJustSitting)).isChecked();
                audioCheckStatus[9] = ((CheckBox) findViewById(R.id.checkAbdominalBreathing)).isChecked();
                audioCheckStatus[10] = ((CheckBox) findViewById(R.id.checkExperiencingTheBreath)).isChecked();
                audioCheckStatus[11] = ((CheckBox) findViewById(R.id.checkCountingTheBreath)).isChecked();
                audioCheckStatus[12] = ((CheckBox) findViewById(R.id.checkFollowingTheBreath)).isChecked();

                //Save checkbox data temporary
                Global.audioCheckStatus = audioCheckStatus;

                //go to next setting
                Intent intent = new Intent(MeditationSettingAudio.this, MeditationSetting.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

}
