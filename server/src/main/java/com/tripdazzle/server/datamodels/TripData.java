package com.tripdazzle.server.datamodels;

import java.util.List;

public class TripData {
    public final String title;
    public final int id;
    public final CreatorData creator;
    public final String description;
    public final String location;
    public final BitmapData mainImage;
    public final ActivityData[] activities;
    public final Float reviewsAverage;
    public final List<Integer> reviews;

    public TripData(String title, int id, CreatorData creator, String description, String location, BitmapData mainImage, ActivityData[] activities, Float reviewsAverage, List<Integer> reviews) {
        this.title = title;
        this.id = id;
        this.creator = creator;
        this.description = description;
        this.location = location;
        this.mainImage = mainImage;
        this.activities = activities;
        this.reviewsAverage = reviewsAverage;
        this.reviews = reviews;
    }
}
