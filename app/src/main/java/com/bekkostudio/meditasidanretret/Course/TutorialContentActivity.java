package com.bekkostudio.meditasidanretret.Course;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

public class TutorialContentActivity extends AppCompatActivity {
    int contentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_tutorial_content);

        //get index number
        Intent intent = getIntent();
        contentIndex = intent.getIntExtra("contentIndex",0);

        //Set click Video thumbnail
        ImageView videoThumbnailWidget = findViewById(R.id.videoThumbnail);
        videoThumbnailWidget.setImageResource(Global.videoThumbnail[contentIndex]);
        videoThumbnailWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TutorialContentActivity.this, ExoPlayerActivity.class);
                intent.putExtra("videoUrl", Global.videoUrl[contentIndex]);
                startActivity(intent);
            }
        });

        //set title
        TextView videoTitleWidget = findViewById(R.id.videoTitle);
        videoTitleWidget.setText(Global.videoTitle[contentIndex]);

        //set description
        TextView videoDescriptionWidget = findViewById(R.id.videoDescription);
        videoDescriptionWidget.setText(Global.videoDescription[contentIndex]);

        Button videoEbookWidget = findViewById(R.id.videoEbook);
        videoEbookWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Global.videoEbookUrl[contentIndex]));
                startActivity(browserIntent);
            }
        });

    }
}
