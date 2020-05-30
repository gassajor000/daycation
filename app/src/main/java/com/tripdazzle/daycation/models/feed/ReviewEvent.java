package com.tripdazzle.daycation.models.feed;

import com.tripdazzle.daycation.models.Creator;
import com.tripdazzle.daycation.models.Review;

import java.util.Date;

public class ReviewEvent extends FeedEvent {
    private static final String DESCRIPTION = "Reviewed a Trip";
    public final Review review;
    // TODO link to trip as well?

    protected ReviewEvent(Creator creator, Date date, Review review) {
        super(creator, date, DESCRIPTION);
        this.review = review;
    }
}
