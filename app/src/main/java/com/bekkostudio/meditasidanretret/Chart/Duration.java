package com.bekkostudio.meditasidanretret.Chart;

import java.io.Serializable;

public class Duration implements Serializable {
    public String date;
    public int duration; // in Second

    public Duration(String date, int duration){
        this.date = date;
        this.duration = duration;
    }
}
