package com.tripdazzle.server.datamodels.feed;

import com.tripdazzle.server.datamodels.CreatorData;
import com.tripdazzle.server.datamodels.ReviewData;

import java.util.Date;

public class ReviewEventData extends FeedEventData {
    public final ReviewData review;
    // TODO link to trip as well?

    public ReviewEventData(CreatorData creator, Date date, ReviewData review) {
        super(creator, date);
        this.review = review;
    }
}
