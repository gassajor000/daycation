package com.tripdazzle.daycation.ui.activityselector;

import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.ActivityType;

public class ActivitySelectorViewModel extends ViewModel {
    private String description = "";
    private String location = "";
    private ActivityType type;
    private Integer activityNumber;

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

    public Integer getActivityNumber() {
        return activityNumber;
    }
    public void setActivityNumber(Integer activityNumber) {
        this.activityNumber = activityNumber;
    }
}
