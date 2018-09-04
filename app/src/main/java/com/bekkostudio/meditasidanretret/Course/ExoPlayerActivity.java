package com.bekkostudio.meditasidanretret.Course;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;
import com.google.android.exoplayer2.ui.PlayerView;

public class ExoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_exo_player);

        //get video url
//        Intent intent = getIntent();
//        url = intent.getStringExtra("videoUrl");
//        String proxyUrl;
//        if (url.startsWith("http")){
//            proxyUrl = Global.getProxy(this).getProxyUrl(url);
//        }else{
//            proxyUrl = url;
//        }

        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        PlayerView playerViewWidget = findViewById(R.id.videoPlayer);

        //Bind video to widget
        playerViewWidget.setPlayer(Global.exoPlayer);
    }


    @Override
    public void onBackPressed() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onBackPressed();
    }
}
