package com.tripdazzle.server.fakedb.feed;

import com.tripdazzle.server.datamodels.feed.FeedEventData;
import com.tripdazzle.server.datamodels.feed.ReviewEventData;
import com.tripdazzle.server.fakedb.FakeDatabase;

import java.util.Date;

public class FakeReviewEvent extends FakeFeedEvent {
    public final Integer reviewId;
    // TODO link to trip as well?

    public FakeReviewEvent(String creator, Date date, Integer reviewId) {
        super(creator, date);
        this.reviewId = reviewId;
    }

    @Override
    public FeedEventData toFeedEventData(FakeDatabase.UserFactory userFactory, FakeDatabase.ReviewFactory reviewFactory, FakeDatabase.TripFactory tripFactory) {
        return new ReviewEventData(userFactory.getCreator(creatorId), date, reviewFactory.getReview(reviewId));
    }
}
