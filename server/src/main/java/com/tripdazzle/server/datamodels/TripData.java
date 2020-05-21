package com.tripdazzle.server.datamodels;

import java.util.List;

public class TripData {
    public final String title;
    public final int id;
    public final String creatorId;
    public final String description;
    public final int mainImageId;
    public final ActivityData[] activities;
    public final Float reviewsAverage;
    public final List<Integer> reviews;

    public TripData(String title, int id, String creatorId, String description, int mainImageId, ActivityData[] activities, Float reviewsAverage, List<Integer> reviews) {
        this.title = title;
        this.id = id;
        this.creatorId = creatorId;
        this.description = description;
        this.mainImageId = mainImageId;
        this.activities = activities;
        this.reviewsAverage = reviewsAverage;
        this.reviews = reviews;
    }
}
