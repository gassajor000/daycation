package com.tripdazzle.daycation.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.tripdazzle.server.datamodels.ActivityData;
import com.tripdazzle.server.datamodels.ActivityTypeData;

public class Activity {
    private ActivityType type;
    public final String location;
    public final String description;

    public Activity(ActivityType type, String location, String description) {
        this.type = type;
        this.location = location;
        this.description = description;
    }

    public Activity(ActivityTypeData type, String location, String description) {
        this.location = location;
        this.description = description;
        this.type = ActivityType.fromActivityTypeData(type);
    }

    public Activity(ActivityData activityData){
        this.location = activityData.location;
        this.description = activityData.description;
        type = ActivityType.fromActivityTypeData(activityData.type);
    }

    public Drawable getIcon(Context context){
        return type.getIcon(context);
    }

    public ActivityData toData(){
        return new ActivityData(type.toData(), location, description);
    }
}
