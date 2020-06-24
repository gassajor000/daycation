package com.tripdazzle.daycation.models.feed;

import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.models.Creator;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.models.location.LocationBuilder;
import com.tripdazzle.server.datamodels.feed.AddFavoriteEventData;

import java.util.Date;

public class AddFavoriteEvent extends FeedEvent {
    private static final String DESCRIPTION = "Added a Trip to Favorites";
    private static final int ICON = R.drawable.ic_star_yellow_24dp;
    public final Trip trip;

    protected AddFavoriteEvent(Creator creator, Date date, Trip trip) {
        super(creator, date, DESCRIPTION, ICON);
        this.trip = trip;
    }

    public AddFavoriteEvent(AddFavoriteEventData data, LocationBuilder locationBuilder){
        super(new Creator(data.creator), data.date, DESCRIPTION, ICON);
        this.trip = new Trip(data.trip, locationBuilder);
    }
}
