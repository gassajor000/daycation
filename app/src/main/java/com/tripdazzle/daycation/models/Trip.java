package com.tripdazzle.daycation.models;

import com.tripdazzle.server.datamodels.ActivityData;
import com.tripdazzle.server.datamodels.TripData;

import java.util.List;

public class Trip {
    public final String title;
    public final int id;
    public final Creator creator;
    public final String description;
    public final String location;
    public final BitmapImage mainImage;
    public final Activity[] activities;
    public final Float reviewsAverage;
    public final List<Integer> reviews;

    public Trip(String title, int id, String description, String location, Activity[] activities, Float reviewsAverage, List<Integer> reviews, Creator creator, BitmapImage mainImage) {
        this.title = title;
        this.id = id;
        this.location = location;
        this.creator = creator;
        this.description = description;
        this.mainImage = mainImage;
        this.activities = activities;
        this.reviewsAverage = reviewsAverage;
        this.reviews = reviews;
    }

    public Trip(TripData data) {
        this.title = data.title;
        this.id = data.id;
        this.creator = new Creator(data.creator);
        this.description = data.description;
        this.location = data.location;
        this.mainImage = new BitmapImage(data.mainImage);
        this.activities = convertActivities(data.activities);
        this.reviewsAverage = data.reviewsAverage;
        this.reviews = data.reviews;
    }

    public TripData toData(){
        return new TripData(title, id, creator.toData(), description, location, mainImage.toData(), activitiesToData(), reviewsAverage, reviews);
    }

    public TripData toDataNewImage(int mainImageId){
        return new TripData(title, id, creator.toData(), description, location, mainImage.toData(mainImageId), activitiesToData(), reviewsAverage, reviews);
    }

    private static Activity[] convertActivities(ActivityData[] activityData){
        return new Activity[]{ new Activity(activityData[0]),
                new Activity(activityData[1]), new Activity(activityData[2]) };
    }

    private ActivityData[] activitiesToData(){
        return new ActivityData[] { activities[0].toData(), activities[1].toData(), activities[2].toData() };
    }

    /**
     * @param num 0, 1, or 2
     * @return activity 0, 1, or 2
     */
    public Activity getActivity(int num){
        return activities[num];
    }

    public Integer getNumReviews(){
        return reviews.size();
    }
}

