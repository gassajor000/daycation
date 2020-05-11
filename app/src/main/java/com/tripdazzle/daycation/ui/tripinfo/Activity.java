package com.tripdazzle.daycation.ui.tripinfo;

public class Activity {
    public final ActivityType type;
    public final String location;
    public final String description;

    public Activity(ActivityType type, String location, String description) {
        this.type = type;
        this.location = location;
        this.description = description;
    }
}
