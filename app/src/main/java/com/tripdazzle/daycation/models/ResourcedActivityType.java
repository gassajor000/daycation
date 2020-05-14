package com.tripdazzle.daycation.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.tripdazzle.daycation.R;
import com.tripdazzle.server.datamodels.ActivityType;

/**
 * ActivityType that is mapped to local resources
 */
public enum ResourcedActivityType {
    HIKING(R.drawable.ic_activity_hiking),
    SWIMMING(R.drawable.ic_activity_swimming),
    ICE_CREAM(R.drawable.ic_activity_ice_cream),
    BEACH(R.drawable.ic_activity_beach);

    private int iconId;
    ResourcedActivityType(int iconId){
        this.iconId = iconId;
    }

    public static ResourcedActivityType fromActivityType(ActivityType type){
        switch (type){
            case HIKING: return ResourcedActivityType.HIKING;
            case SWIMMING: return ResourcedActivityType.SWIMMING;
            case ICE_CREAM: return ResourcedActivityType.ICE_CREAM;
            case BEACH: return ResourcedActivityType.BEACH;
            default: return ResourcedActivityType.SWIMMING;
        }
    }

    public Drawable getIcon(Context context){
        return ContextCompat.getDrawable(context, iconId);
    }
}