package com.tripdazzle.daycation.models.feed;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;

import androidx.core.content.ContextCompat;

import com.tripdazzle.daycation.models.Creator;

import java.util.Date;

public abstract class FeedEvent {
    public final Creator creator;
    public final Date date;
    public final String description;
    public final int iconId;

    protected FeedEvent(Creator creator, Date date, String description, int iconId) {
        this.creator = creator;
        this.date = date;
        this.description = description;
        this.iconId = iconId;
    }

    public Drawable getIcon(Context context){
        return ContextCompat.getDrawable(context, iconId);
    }

    public CharSequence formatDate(Context context){
        return DateUtils.getRelativeDateTimeString(context, date.getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.YEAR_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
    }
}
