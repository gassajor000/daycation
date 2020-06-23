package com.tripdazzle.daycation.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.MarkerOptions;
import com.tripdazzle.daycation.models.location.Location;
import com.tripdazzle.daycation.models.location.LocationBuilder;
import com.tripdazzle.server.datamodels.ActivityData;
import com.tripdazzle.server.datamodels.ActivityTypeData;

public class Activity {
    private ActivityType type;
    public final Location location;
    public final String description;

    public Activity(ActivityType type, Location location, String description) {
        this.type = type;
        this.location = location;
        this.description = description;
    }

    public Activity(ActivityTypeData type, Location location, String description) {
        this.location = location;
        this.description = description;
        this.type = ActivityType.fromActivityTypeData(type);
    }

    /* Blocking*/
    public Activity(ActivityData activityData, LocationBuilder locationBuilder){
        this.location = locationBuilder.makeLocationBlocking(activityData.location);
        this.description = activityData.description;
        type = ActivityType.fromActivityTypeData(activityData.type);
    }

    public Drawable getIcon(Context context){
        return type.getIcon(context);
    }

    public ActivityData toData(){
        return new ActivityData(type.toData(), location.toData(), description);
    }

    public MarkerOptions getMarker(){
        return new MarkerOptions().position(location.getLatLang()).title(location.getName());
    }
}
