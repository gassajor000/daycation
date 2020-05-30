package com.tripdazzle.server.datamodels.feed;


import com.tripdazzle.server.datamodels.CreatorData;
import com.tripdazzle.server.datamodels.TripData;

import java.util.Date;

public class AddFavoriteEventData extends FeedEventData {
    public final TripData trip;

    public AddFavoriteEventData(CreatorData creator, Date date, TripData trip) {
        super(creator, date);
        this.trip = trip;
    }
}
