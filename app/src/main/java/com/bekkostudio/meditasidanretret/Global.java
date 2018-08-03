package com.bekkostudio.meditasidanretret;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Global {

    public static TimerFragment lastTimerFragmentObject;
    public static ArrayList<String> recentMeditation;

    public static final int[] ambientImageItem = {R.drawable.ambient0,R.drawable.ambient1,R.drawable.ambient2,R.drawable.ambient3};
    public static final int[] ambientMusicItem = {R.raw.butterfly_space,R.raw.mt_airy,R.raw.weaving,R.raw.butterfly_space};


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


}
