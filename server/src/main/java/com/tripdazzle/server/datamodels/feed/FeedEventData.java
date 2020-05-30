package com.tripdazzle.server.datamodels.feed;

import com.tripdazzle.server.datamodels.CreatorData;

import java.util.Date;

public abstract class FeedEventData {
    public final CreatorData creator;
    public final Date date;

    protected FeedEventData(CreatorData creator, Date date) {
        this.creator = creator;
        this.date = date;
    }
}
