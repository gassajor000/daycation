package com.tripdazzle.daycation.ui.tripinfo;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.tripdazzle.daycation.R;

public enum ActivityType {
    HIKING(R.drawable.ic_activity_hiking),
    SWIMING(R.drawable.ic_activity_swimming),
    ICE_CREAM(R.drawable.ic_activity_ice_cream),
    BEACH(R.drawable.ic_activity_beach);

    private int iconId;
    ActivityType(int iconId){
        this.iconId = iconId;
    }

    public Drawable getIcon(Context context){
        return ContextCompat.getDrawable(context, iconId);
    }
}
