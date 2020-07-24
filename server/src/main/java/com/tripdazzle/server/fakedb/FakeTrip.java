package com.tripdazzle.server.fakedb;

import com.tripdazzle.server.datamodels.ActivityData;
import com.tripdazzle.server.datamodels.TripData;
import com.tripdazzle.server.datamodels.location.LocationData;

import java.util.List;

public class FakeTrip {
    public final String title;
    public final int id;
    public final String creatorId;
    public LocationData location;
    public String description;
    public int mainImageId;
    public ActivityData[] activities;
    public Float reviewsAverage;
    public List<Integer> reviews;

    public FakeTrip(String title, int id, String creatorId, LocationData location, String description, int mainImageId, ActivityData[] activities, Float reviewsAverage, List<Integer> reviews) {
        this.title = title;
        this.id = id;
        this.creatorId = creatorId;
        this.location = location;
        this.description = description;
        this.mainImageId = mainImageId;
        this.activities = activities;
        this.reviewsAverage = reviewsAverage;
        this.reviews = reviews;
    }

    public TripData toTripData(FakeDatabase.ImageFactory imageFactory, FakeDatabase.UserFactory userFactory){
        return new TripData(title, id, userFactory.getCreator(creatorId), description, location, imageFactory.getImage(mainImageId), activities, reviewsAverage, reviews);
    }
}
