package com.tripdazzle.daycation.models;

import com.tripdazzle.server.datamodels.ReviewData;

import java.util.Date;

public class Review {
    public final Integer id;
    public final Reviewer reviewer;
    public final Float reviewRating;
    public final Date reviewDate;
    public final String reviewComment;

    public Review(Integer id, Reviewer reviewer, Float reviewRating, Date reviewDate, String reviewComment) {
        this.id = id;
        this.reviewer = reviewer;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
        this.reviewComment = reviewComment;
    }


    public Review(ReviewData data) {
        this.id = data.id;
        this.reviewer = new Reviewer(data.reviewer);
        this.reviewRating = data.reviewRating;
        this.reviewDate = data.reviewDate;
        this.reviewComment = data.reviewComment;
    }
}
