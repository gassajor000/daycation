package com.tripdazzle.daycation.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.tripdazzle.server.datamodels.ActivityData;
import com.tripdazzle.server.datamodels.ActivityType;

public class Activity extends com.tripdazzle.server.datamodels.ActivityData {
    private ResourcedActivityType resourcedType;

    public Activity(ActivityType type, String location, String description) {
        super(type, location, description);
        resourcedType = ResourcedActivityType.fromActivityType(type);
    }

    public Activity(ActivityData activityData){
        super(activityData.type, activityData.description, activityData.location);
        resourcedType = ResourcedActivityType.fromActivityType(activityData.type);
    }

    public Drawable getIcon(Context context){
        return resourcedType.getIcon(context);
    }
}
