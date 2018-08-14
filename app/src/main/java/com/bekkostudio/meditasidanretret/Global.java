package com.bekkostudio.meditasidanretret;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;

import com.bekkostudio.meditasidanretret.Timer.TimerCountdown;
import com.bekkostudio.meditasidanretret.Timer.TimerFragment;
import com.danikula.videocache.HttpProxyCacheServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Global {
    //timer
    public static TimerFragment lastTimerFragmentObject;
    public static ArrayList<String> recentMeditation;

    public static final int[] ambientImageItem = {R.drawable.ambient0,R.drawable.ambient1,R.drawable.ambient2,R.drawable.ambient3};
    public static final int[] ambientMusicItem = {0,R.raw.mt_airy,R.raw.weaving,R.raw.butterfly_space};

    //Article
    public static WebView currentWebview;


    //course
    //tutorial
    // Array of strings for ListView
    public static final String[] videoTitle ={
            "How to Meditate for dummies", "How to Meditate for dummies 2", "How to Meditate for dummies 3",
    };
    public static final int[] videoThumbnail ={
            R.drawable.ambient0, R.drawable.ambient0, R.drawable.ambient0,
    };
    public static final String[] listviewShortDescription ={
            "00:13:30", "00:13:30", "00:13:30",
    };
    public static final String[] videoUrl ={
            "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media",
            "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media",
            "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media",
    };
    public static final String[] videoDescription ={
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
    };
    public static final String[] videoEbookUrl ={
            "https://www.bekkostudio.com/",
            "https://www.bekkostudio.com/",
            "https://www.bekkostudio.com/",
    };

    //Video Cache
    private static HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        return proxy == null ? (proxy = newProxy(context.getApplicationContext())) : proxy;
    }

    private static HttpProxyCacheServer newProxy(Context context) {
        return new HttpProxyCacheServer(context);
    }



    public static void StartTimer(Context context, int meditationDuration, int warmupDuration, int ambientMusic){
        Intent intent = new Intent(context, TimerCountdown.class);
        intent.putExtra("meditationDuration", meditationDuration);
        intent.putExtra("warmupDuration", warmupDuration);
        intent.putExtra("ambientMusic", ambientMusic);
        context.startActivity(intent);
    }

    public static void getRecentMeditation(Context context){

        try {
            FileInputStream inputStream = context.openFileInput("recentMeditation.txt");
            ObjectInputStream input = new ObjectInputStream(inputStream);
            recentMeditation = (ArrayList<String>) input.readObject();
            input.close();
        } catch (FileNotFoundException e){
            recentMeditation = new ArrayList<>();
            recentMeditation.add(0,"Kosong");
            recentMeditation.add(0,"Kosong");
            recentMeditation.add(0,"Kosong");
            Log.d("ANU","FILENOTFOUND: "+ recentMeditation.size() + ", Index 2: "+ recentMeditation.get(2));

        }catch (Exception e){
            Log.d("getRecentMeditation", "Exception: " + e);
        }

    }

    public static void setRecentMeditation (Context context, String timeToSave){
        recentMeditation.remove(2);
        recentMeditation.add(0, timeToSave);

        try {
            FileOutputStream outputStream = context.openFileOutput("recentMeditation.txt", Context.MODE_PRIVATE);
            //Log.d("Context Directory", "Path: "+context.getFilesDir());
            ObjectOutputStream output = new ObjectOutputStream(outputStream);
            output.writeObject(recentMeditation);
            output.close();
        }catch (Exception e){
            Log.d("SetRecentMeditation", "Exception: " + e);
        }

    }

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }


}
