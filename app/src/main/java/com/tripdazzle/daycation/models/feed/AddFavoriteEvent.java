package com.tripdazzle.daycation.models.feed;

import com.tripdazzle.daycation.models.Creator;
import com.tripdazzle.daycation.models.Trip;

import java.util.Date;

public class AddFavoriteEvent extends FeedEvent {
    private static final String DESCRIPTION = "Added a Trip to Favorites";
    public final Trip trip;

    protected AddFavoriteEvent(Creator creator, Date date, Trip trip) {
        super(creator, date, DESCRIPTION);
        this.trip = trip;
    }
}
