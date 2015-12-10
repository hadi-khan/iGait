package com.igaitapp.virtualmd.igait;

import java.io.Serializable;
import java.util.Date;

/**
 * A gait health holds the health value, start time, and end time of a walk event.
 */
public class GaitHealth implements Serializable {
    // The necessary info for gait health.
    private int health;
    private Date startTime, endTime;
    boolean video;

    // Blank constructor, Not really necessary.
    public GaitHealth() {

    }

    // Full constructor.
    public GaitHealth(int health, Date startTime, Date endTime, boolean video) {
        this.health = health;
        this.startTime = startTime;
        this.endTime = endTime;
        this.video = video;
    }

    // The various setters and getters for gait health's necessary info.

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean hasVideo() {
        return video;
    }

    public void setVideo(boolean priority) {
        this.video = priority;
    }
}