package com.tripdazzle.server.datamodels;

public class TripData {
    public final String title;
    public final int id;
    public final String creatorId;
    public final String description;
    public final int mainImageId;
    public final ActivityData[] activities;
    public final Float reviewsAverage;
    public final Integer numReviews;

    public TripData(String title, int id, String creatorId, String description, int mainImageId, ActivityData[] activities, Float reviewsAverage, Integer numReviews) {
        this.title = title;
        this.id = id;
        this.creatorId = creatorId;
        this.description = description;
        this.mainImageId = mainImageId;
        this.activities = activities;
        this.reviewsAverage = reviewsAverage;
        this.numReviews = numReviews;
    }
}
