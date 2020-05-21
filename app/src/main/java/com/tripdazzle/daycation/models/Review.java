package com.tripdazzle.daycation.models;

import com.tripdazzle.server.datamodels.ReviewData;

import java.util.Date;

public class Review extends ReviewData {
    public Review(Integer id, String reviewerName, Float reviewRating, Date reviewDate, String reviewComment) {
        super(id, reviewerName, reviewRating, reviewDate, reviewComment);
    }

    public Review(ReviewData data) {
        super(data.id, data.reviewerName, data.reviewRating, data.reviewDate, data.reviewComment);
    }
}
