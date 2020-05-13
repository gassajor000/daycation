package com.tripdazzle.server.datamodels;

public class TripData {
    public final String title;
    public final int id;
    public final String creatorId;
    public final String description;
    public final String mainImageId;
    public final ActivityData[] activities;

    public TripData(String title, int id, String creatorId, String description, String mainImageId, ActivityData[] activities) {
        this.title = title;
        this.id = id;
        this.creatorId = creatorId;
        this.description = description;
        this.mainImageId = mainImageId;
        this.activities = activities;
    }
}
