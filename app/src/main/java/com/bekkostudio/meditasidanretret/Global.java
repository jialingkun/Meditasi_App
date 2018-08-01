package com.bekkostudio.meditasidanretret;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Global {
    public static ArrayList<String> recentMeditation;


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
            Log.d("ANU","FILENOTFOUND: "+ recentMeditation.size());

        }catch (Exception e){

        }

    }


}
