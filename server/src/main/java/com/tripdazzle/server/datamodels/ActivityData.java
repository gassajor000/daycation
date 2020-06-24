package com.tripdazzle.server.datamodels;

import com.tripdazzle.server.datamodels.location.LocationData;

public class ActivityData {
    public final ActivityTypeData type;
    public final LocationData location;
    public final String description;

    public ActivityData(ActivityTypeData type, LocationData location, String description) {
        this.type = type;
        this.location = location;
        this.description = description;
    }
}
