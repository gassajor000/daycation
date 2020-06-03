package com.tripdazzle.server.fakedb.feed;


import com.tripdazzle.server.datamodels.feed.AddFavoriteEventData;
import com.tripdazzle.server.datamodels.feed.FeedEventData;
import com.tripdazzle.server.fakedb.FakeDatabase;

import java.util.Date;

public class FakeAddFavoriteEvent extends FakeFeedEvent {
    public final Integer tripId;

    public FakeAddFavoriteEvent(String creator, Date date, Integer tripId) {
        super(creator, date);
        this.tripId = tripId;
    }

    @Override
    public FeedEventData toFeedEventData(FakeDatabase.UserFactory userFactory, FakeDatabase.ReviewFactory reviewFactory, FakeDatabase.TripFactory tripFactory) {
        return new AddFavoriteEventData(userFactory.getCreator(creatorId), date, tripFactory.getTrip(tripId));
    }
}
