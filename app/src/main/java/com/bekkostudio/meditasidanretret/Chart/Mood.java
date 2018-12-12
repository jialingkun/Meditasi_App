package com.bekkostudio.meditasidanretret.Chart;

import java.io.Serializable;

public class Mood implements Serializable {
    public String date;
    public int moodBeforeValue;
    public int moodAfterValue;

    //Mood value constant
//    public static final int SANGAT_TENANG = 10;
//    public static final int TENANG = 7;
//    public static final int TIDAK_TENANG = 4;
//    public static final int SANGAT_TIDAK_TENANG = 1;

    //Medicine value constant
//    public static final int MINUM_OBAT = 10;
//    public static final int KURANGI_DOSIS = 5;
//    public static final int TIDAK_MINUM_OBAT = 1;

//    public static final int TIDAK_MENGISI = 0;

    public Mood(String date, int moodBeforeValue, int moodAfterValue ) {
        this.date = date;
        this.moodBeforeValue = moodBeforeValue;
        this.moodAfterValue = moodAfterValue;
    }

}
