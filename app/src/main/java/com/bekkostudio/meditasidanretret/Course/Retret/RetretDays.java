package com.bekkostudio.meditasidanretret.Course.Retret;

import java.io.Serializable;

public class RetretDays implements Serializable {
    public String videoUrl;
    public int videoThumbnail;
    public int morningDuration;//in minutes
    public int morningBGM;
    public int nightDuration;
    public int nightBGM;
    public String description;
    public boolean morningCompleted = false;
    public boolean nightCompleted = false;
}
