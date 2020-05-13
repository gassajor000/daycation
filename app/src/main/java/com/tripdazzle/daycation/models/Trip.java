package com.tripdazzle.daycation.models;

import com.tripdazzle.server.datamodels.ActivityData;
import com.tripdazzle.server.datamodels.TripData;

public class Trip extends com.tripdazzle.server.datamodels.TripData {
    public Trip(String title, int id, String creatorId, String description, String mainImageId, Activity[] activities) {
        super(title, id, creatorId, description, mainImageId, activities);
    }

    public Trip(TripData tripData) {
        super(tripData.title, tripData.id, tripData.creatorId, tripData.description,
                tripData.mainImageId, Trip.convertActivities(tripData.activities));
    }

    private static Activity[] convertActivities(ActivityData[] activityData){
        return new Activity[]{ new Activity(activityData[0]),
                new Activity(activityData[1]), new Activity(activityData[2]) };
    }

    /**
     * @param num 0, 1, or 2
     * @return activity 0, 1, or 2
     */
    public Activity getActivity(int num){
        return (Activity) this.activities[num];
    }
}

