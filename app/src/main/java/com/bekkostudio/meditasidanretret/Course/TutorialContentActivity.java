package com.bekkostudio.meditasidanretret.Course;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class TutorialContentActivity extends AppCompatActivity {
    int contentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_tutorial_content);

        //get index number
        Intent intent = getIntent();
        contentIndex = intent.getIntExtra("contentIndex",0);

//        //Set click Video thumbnail
//        ImageView videoThumbnailWidget = findViewById(R.id.videoThumbnail);
//        videoThumbnailWidget.setImageResource(Global.videoThumbnail[contentIndex]);
//        videoThumbnailWidget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TutorialContentActivity.this, ExoPlayerActivity.class);
//                intent.putExtra("videoUrl", Global.videoUrl[contentIndex]);
//                startActivity(intent);
//            }
//        });


        //set exoplayer
        String url = Global.videoUrl[contentIndex];
        String proxyUrl = Global.getProxy(this).getProxyUrl(url);
        PlayerView playerViewWidget = findViewById(R.id.exoplayer);
        initializeExoPlayer(proxyUrl,playerViewWidget);


        Button fullscreenWidget = findViewById(R.id.fullscreenPlayer);
        fullscreenWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialContentActivity.this, ExoPlayerActivity.class);
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



    private void initializeExoPlayer(String url, PlayerView playerView){
        // 1. Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        Global.exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        // 3. Bind Player to the view
        playerView.setPlayer(Global.exoPlayer);

        // 4. Preparing Player
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "MeditasiDanRetret"), defaultBandwidthMeter);
        //create URI
        Uri videoUri = Uri.parse(url);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);
        // Prepare the player with the source.
        Global.exoPlayer.prepare(videoSource);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DESTROYED", "onDestroy: ");
        if (Global.exoPlayer!=null) {
            Global.exoPlayer.release();
            Global.exoPlayer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Global.exoPlayer!=null){
            PlayerView playerViewWidget = findViewById(R.id.exoplayer);
            playerViewWidget.setPlayer(null);
            playerViewWidget.setPlayer(Global.exoPlayer);
        }
    }
}
