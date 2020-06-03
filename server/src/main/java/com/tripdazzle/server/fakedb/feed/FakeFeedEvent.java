package com.tripdazzle.server.fakedb.feed;

import com.tripdazzle.server.datamodels.feed.FeedEventData;
import com.tripdazzle.server.fakedb.FakeDatabase;

import java.util.Date;

public abstract class FakeFeedEvent {
    public final String creatorId;
    public final Date date;

    protected FakeFeedEvent(String creatorId, Date date) {
        this.creatorId = creatorId;
        this.date = date;
    }

    public abstract FeedEventData toFeedEventData(FakeDatabase.UserFactory userFactory, FakeDatabase.ReviewFactory reviewFactory, FakeDatabase.TripFactory tripFactory);

}
