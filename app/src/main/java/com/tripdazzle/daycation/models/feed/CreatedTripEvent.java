package com.tripdazzle.daycation.models.feed;

import com.tripdazzle.daycation.models.Creator;
import com.tripdazzle.daycation.models.Trip;

import java.util.Date;

public class CreatedTripEvent extends FeedEvent {
    private static final String DESCRIPTION = "Created a Trip";
    public final Trip trip;

    protected CreatedTripEvent(Creator creator, Date date, Trip trip) {
        super(creator, date, DESCRIPTION);
        this.trip = trip;
    }
}
