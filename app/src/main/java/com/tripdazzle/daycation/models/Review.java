package com.tripdazzle.daycation.models;

import com.tripdazzle.server.datamodels.ReviewData;

import java.util.Date;

public class Review {
    public final Integer id;
    public final Reviewer reviewer;
    public final Float reviewRating;
    public final Date reviewDate;
    public final String reviewComment;
    public final Integer tripId;

    public Review(Integer id, Reviewer reviewer, Float reviewRating, Date reviewDate, String reviewComment, Integer tripId) {
        this.id = id;
        this.reviewer = reviewer;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
        this.reviewComment = reviewComment;
        this.tripId = tripId;
    }


    public Review(ReviewData data) {
        this.id = data.id;
        this.reviewer = new Reviewer(data.reviewer);
        this.reviewRating = data.reviewRating;
        this.reviewDate = data.reviewDate;
        this.reviewComment = data.reviewComment;
        this.tripId = data.tripId;
    }

    public ReviewData toData(){
        return new ReviewData(id, reviewer.toData(), reviewRating, reviewDate, reviewComment, tripId);
    }
}
