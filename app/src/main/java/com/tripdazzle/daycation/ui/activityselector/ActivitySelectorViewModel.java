package com.tripdazzle.daycation.ui.activityselector;

import androidx.lifecycle.ViewModel;

import com.tripdazzle.server.datamodels.ActivityTypeData;

public class ActivitySelectorViewModel extends ViewModel {
    private String description = "";
    private String location = "";
    private ActivityTypeData type;

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

    public ActivityTypeData getType() {
        return type;
    }
    public void setType(ActivityTypeData type) {
        this.type = type;
    }

}
