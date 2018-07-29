package com.bekkostudio.meditasidanretret;

import android.content.Context;
import android.content.Intent;

public class Global {
    public static void StartTimer(Context context, int meditationDuration, int warmupDuration, int ambientMusic){
        Intent intent = new Intent(context, TimerCountdown.class);
        intent.putExtra("meditationDuration", meditationDuration);
        intent.putExtra("warmupDuration", warmupDuration);
        intent.putExtra("ambientMusic", ambientMusic);
        context.startActivity(intent);
    }


}
