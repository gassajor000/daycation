package com.tripdazzle.daycation.models.feed;

import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.models.Creator;
import com.tripdazzle.daycation.models.Review;
import com.tripdazzle.server.datamodels.feed.ReviewEventData;

import java.util.Date;

public class ReviewEvent extends FeedEvent {
    private static final String DESCRIPTION = "Reviewed a Trip";
    private static final int ICON = R.drawable.ic_pencil_gray_24dp;
    public final Review review;
    // TODO link to trip as well?

    public ReviewEvent(Creator creator, Date date, Review review) {
        super(creator, date, DESCRIPTION, ICON);
        this.review = review;
    }

    public ReviewEvent(ReviewEventData data){
        super(new Creator(data.creator), data.date, DESCRIPTION, ICON);
        this.review = new Review(data.review);
    }
}
