package com.tripdazzle.server.fakedb.feed;

import com.tripdazzle.server.datamodels.feed.CreatedTripEventData;
import com.tripdazzle.server.datamodels.feed.FeedEventData;
import com.tripdazzle.server.fakedb.FakeDatabase;

import java.util.Date;

public class FakeCreatedTripEvent extends FakeFeedEvent {
    public final Integer tripId;

    public FakeCreatedTripEvent(String creator, Date date, Integer tripId) {
        super(creator, date);
        this.tripId = tripId;
    }

    @Override
    public FeedEventData toFeedEventData(FakeDatabase.UserFactory userFactory, FakeDatabase.ReviewFactory reviewFactory, FakeDatabase.TripFactory tripFactory) {
        return new CreatedTripEventData(userFactory.getCreator(creatorId), date, tripFactory.getTrip(tripId));
    }
}
