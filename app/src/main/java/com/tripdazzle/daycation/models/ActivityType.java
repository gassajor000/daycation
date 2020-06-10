package com.tripdazzle.daycation.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.tripdazzle.daycation.R;
import com.tripdazzle.server.datamodels.ActivityTypeData;

/**
 * ActivityType that is mapped to local resources
 */
public enum ActivityType {
    HIKING(R.drawable.ic_activity_hiking, "Hiking"),
    SWIMMING(R.drawable.ic_activity_swimming, "Swimming"),
    ICE_CREAM(R.drawable.ic_activity_ice_cream, "Ice Cream"),
    BEACH(R.drawable.ic_activity_beach, "Beach");

    private int iconId;
    private String friendlyName;

    ActivityType(int iconId, String friendlyName){
        this.iconId = iconId;
        this.friendlyName = friendlyName;
    }

    public static ActivityType fromActivityTypeData(ActivityTypeData type){
        switch (type){
            case HIKING: return ActivityType.HIKING;
            case SWIMMING: return ActivityType.SWIMMING;
            case ICE_CREAM: return ActivityType.ICE_CREAM;
            case BEACH: return ActivityType.BEACH;
            default: return null;
        }
    }

    public ActivityTypeData toData(){
        switch (this){
            case BEACH: return ActivityTypeData.BEACH;
            case SWIMMING: return ActivityTypeData.SWIMMING;
            case ICE_CREAM: return ActivityTypeData.ICE_CREAM;
            case HIKING: return ActivityTypeData.HIKING;
            default: return null;
        }
    }

    public Drawable getIcon(Context context){
        return ContextCompat.getDrawable(context, iconId);
    }

    @NonNull
    @Override
    public String toString() {
        return friendlyName;
    }
}