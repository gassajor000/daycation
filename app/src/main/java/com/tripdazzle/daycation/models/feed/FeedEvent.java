package com.tripdazzle.daycation.models.feed;

import com.tripdazzle.daycation.models.Creator;

import java.util.Date;

public abstract class FeedEvent {
    public final Creator creator;
    public final Date date;
    public final String description;

    protected FeedEvent(Creator creator, Date date, String description) {
        this.creator = creator;
        this.date = date;
        this.description = description;
    }
}
