package com.tripdazzle.daycation.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.tripdazzle.daycation.R;
import com.tripdazzle.server.datamodels.ActivityTypeData;

/**
 * ActivityType that is mapped to local resources
 */
public enum ActivityType {
    HIKING(R.drawable.ic_activity_hiking),
    SWIMMING(R.drawable.ic_activity_swimming),
    ICE_CREAM(R.drawable.ic_activity_ice_cream),
    BEACH(R.drawable.ic_activity_beach);

    private int iconId;
    ActivityType(int iconId){
        this.iconId = iconId;
    }

    public static ActivityType fromActivityType(ActivityTypeData type){
        switch (type){
            case HIKING: return ActivityType.HIKING;
            case SWIMMING: return ActivityType.SWIMMING;
            case ICE_CREAM: return ActivityType.ICE_CREAM;
            case BEACH: return ActivityType.BEACH;
            default: return ActivityType.SWIMMING;
        }
    }

    public Drawable getIcon(Context context){
        return ContextCompat.getDrawable(context, iconId);
    }
}