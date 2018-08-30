package com.bekkostudio.meditasidanretret.Course.Retret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

public class TimelineContentActivity extends AppCompatActivity {
    int contentIndex;
    RetretDays retretDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_retret_timeline_content);

        //get index number
        Intent intent = getIntent();
        contentIndex = intent.getIntExtra("contentIndex",0);

        retretDays = Global.courseRetret.get(Global.activeRetretId).retretDays[contentIndex];

        TextView videoTitle = findViewById(R.id.videoTitle);
        videoTitle.setText("Hari Ke "+(contentIndex+1));


        ImageView videoThumbnail = findViewById(R.id.videoThumbnail);
        videoThumbnail.setImageResource(retretDays.videoThumbnail);

        TextView morningDuration = findViewById(R.id.MorningDuration);
        morningDuration.setText(retretDays.morningDuration+" Menit");

        TextView nightDuration = findViewById(R.id.NightDuration);
        nightDuration.setText(retretDays.nightDuration+" Menit");

        TextView description = findViewById(R.id.Description);
        description.setText(retretDays.description);





    }
}
