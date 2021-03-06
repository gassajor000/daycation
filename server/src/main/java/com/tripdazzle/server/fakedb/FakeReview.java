package com.tripdazzle.server.fakedb;

import com.tripdazzle.server.datamodels.ReviewData;
import com.tripdazzle.server.datamodels.ReviewerData;

import java.util.Date;

public class FakeReview {
    public final Integer id;
    public final String reviewerId;
    public final Float reviewRating;
    public final Date reviewDate;
    public final String reviewComment;
    public final Integer tripId;

    public FakeReview(Integer id, String reviewerId, Float reviewRating, Date reviewDate, String reviewComment, Integer tripId) {
        this.id = id;
        this.reviewerId = reviewerId;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
        this.reviewComment = reviewComment;
        this.tripId = tripId;
    }

    public ReviewData toReviewData(FakeDatabase.UserFactory userFactory){
        ReviewerData reviewer = userFactory.getReviewer(reviewerId);
        return new ReviewData(id, reviewer, reviewRating, reviewDate, reviewComment, tripId);
    }
}
