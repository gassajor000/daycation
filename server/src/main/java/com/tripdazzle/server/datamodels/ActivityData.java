package com.tripdazzle.server.datamodels;

public class ActivityData {
    public final ActivityTypeData type;
    public final String location;
    public final String description;

    public ActivityData(ActivityTypeData type, String location, String description) {
        this.type = type;
        this.location = location;
        this.description = description;
    }
}
