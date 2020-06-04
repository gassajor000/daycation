package com.tripdazzle.daycation.ui.createtrip;

import com.tripdazzle.daycation.models.Activity;
import com.tripdazzle.server.datamodels.ActivityType;

public class ActivitySelectorViewModel {
    private String description = "";
    private String location = "";
    private ActivityType type;

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public ActivityType getType() {
        return type;
    }
    public void setType(ActivityType type) {
        this.type = type;
    }

    public Activity toActivity(){
        return new Activity(type, location, description);
    }
}
