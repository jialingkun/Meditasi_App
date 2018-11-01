package com.bekkostudio.meditasidanretret.Chart;

import java.io.Serializable;

public class Note implements Serializable {
    public String date;
    public String noteValue;
    public boolean important;

    public Note(String date, String noteValue, boolean important ) {
        this.date = date;
        this.noteValue = noteValue;
        this.important = important;

    }
}
