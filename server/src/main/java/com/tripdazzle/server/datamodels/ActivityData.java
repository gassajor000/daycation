package com.tripdazzle.server.datamodels;

public class ActivityData {
    public final ActivityType type;
    public final String location;
    public final String description;

    public ActivityData(ActivityType type, String location, String description) {
        this.type = type;
        this.location = location;
        this.description = description;
    }
}
