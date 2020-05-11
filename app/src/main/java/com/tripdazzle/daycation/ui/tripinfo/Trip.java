package com.tripdazzle.daycation.ui.tripinfo;

public class Trip {
    public final String title;
    public final int id;
    public final String creatorId;
    public final String description;
    public final String mainImageId;
    public final Activity[] activities;

    public Trip(String title, int id, String creatorId, String description, String mainImageId, Activity[] activities) {
        this.title = title;
        this.id = id;
        this.creatorId = creatorId;
        this.description = description;
        this.mainImageId = mainImageId;
        this.activities = activities;
    }
}

