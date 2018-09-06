package com.bekkostudio.meditasidanretret.Course.Retret;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bekkostudio.meditasidanretret.Course.ExoPlayerActivity;
import com.bekkostudio.meditasidanretret.Course.TutorialContentActivity;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;

public class TimelineContentActivity extends AppCompatActivity {
    int contentIndex;
    RetretDays retretDays;
//    String filePath;
    int warmupDuration = 10; //universal warmup duration, change this value
    int meditationDuration;
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

//        ImageView videoThumbnail = findViewById(R.id.videoThumbnail);
//        videoThumbnail.setImageResource(retretDays.videoThumbnail);


        //set exoplayer
        String url = retretDays.videoUrl;
        String proxyUrl = Global.getProxy(this).getProxyUrl(url);
        PlayerView playerViewWidget = findViewById(R.id.exoplayer);
        initializeExoPlayer(proxyUrl,playerViewWidget);


        Button fullscreenWidget = findViewById(R.id.fullscreenPlayer);
        fullscreenWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimelineContentActivity.this, ExoPlayerActivity.class);
                startActivity(intent);
            }
        });




        TextView morningDuration = findViewById(R.id.MorningDuration);
        morningDuration.setText(retretDays.morningDuration+" Menit");

        TextView nightDuration = findViewById(R.id.NightDuration);
        nightDuration.setText(retretDays.nightDuration+" Menit");

        TextView description = findViewById(R.id.Description);
        description.setText(retretDays.description);


//        //Download and play video
//        filePath = getCacheDir()+"/"+Global.md5(retretDays.videoUrl);
//        videoThumbnail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                File file = new File (filePath);
//                if (file.exists()){
//                    Intent intent = new Intent(TimelineContentActivity.this, ExoPlayerActivity.class);
//                    intent.putExtra("videoUrl", filePath);
//                    startActivity(intent);
//                }else{
//                    downloadVideo(retretDays.videoUrl);
//                }
//
//            }
//        });


        //start meditation
        Button morningStart = findViewById(R.id.MorningStart);
        morningStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCurrentTimeBetweenTwoHours(4,11)){
                    meditationDuration = retretDays.morningDuration * 60; //minutes to seconds
                    Global.startTimer(TimelineContentActivity.this,meditationDuration,warmupDuration,retretDays.morningBGM);
                }else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(TimelineContentActivity.this);
                    alert.setTitle("Peringatan");
                    alert.setMessage("Sesi pagi hanya bisa dimulai dari jam 4 sampai 11 pagi");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                }
            }
        });
        Button nightStart = findViewById(R.id.NightStart);
        nightStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCurrentTimeBetweenTwoHours(16,23)){
                    meditationDuration = retretDays.nightDuration * 60; //minutes to seconds
                    Global.startTimer(TimelineContentActivity.this,meditationDuration,warmupDuration,retretDays.morningBGM);
                }else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(TimelineContentActivity.this);
                    alert.setTitle("Peringatan");
                    alert.setMessage("Sesi malam hanya bisa dimulai dari jam 4 sampai 11 malam");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                }

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


    public boolean isCurrentTimeBetweenTwoHours(int fromHour, int toHour) {
        //Current Time
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        //Start Time
        Calendar from = Calendar.getInstance();
        from.set(Calendar.HOUR_OF_DAY, fromHour);
        from.set(Calendar.MINUTE, 0);
        //Stop Time
        Calendar to = Calendar.getInstance();
        to.set(Calendar.HOUR_OF_DAY, toHour);
        to.set(Calendar.MINUTE, 0);

        if(to.before(from)) {
            if (now.after(to)) to.add(Calendar.DATE, 1);
            else from.add(Calendar.DATE, -1);
        }
        return now.after(from) && now.before(to);
    }


//    public void downloadVideo(String url){
//        new DownloadFile().execute(url);
//    }


    /**
     * Async Task to download file from URL
     */
    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String fileName;
        private String folder;

        private boolean isDownloaded;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(TimelineContentActivity.this);
            this.progressDialog.setTitle("Download Video, Mohon Tunggu...");
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();


                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream());


                //Extract file name from URL
                fileName = Global.md5(f_url[0]);
                Log.d("FILENAME", "doInBackground: "+fileName);

                //Cache directory path to save file
                folder = getCacheDir()+"/";

//                //Create folder if it does not exist
//                File directory = new File(folder);
//
//                if (!directory.exists()) {
//                    directory.mkdirs();
//                }

                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    Log.d("Download", "Progress: " + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                return "Downloaded at: "+folder + fileName;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return "Something went wrong";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            // dismiss the dialog after the file was downloaded
            this.progressDialog.dismiss();

            // Display File path after downloading
            Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_LONG).show();
        }
    }
}
