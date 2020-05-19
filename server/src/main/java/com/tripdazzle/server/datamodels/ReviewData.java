package com.tripdazzle.server.datamodels;

import java.util.Date;

public class ReviewData {
    public final Integer id;
    public final String reviewerName;       // TODO: Reviewer Class
    public final Float reviewRating;
    public final Date reviewDate;
    public final String reviewComment;

    public ReviewData(Integer id, String reviewerName, Float reviewRating, Date reviewDate, String reviewComment) {
        this.id = id;
        this.reviewerName = reviewerName;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
        this.reviewComment = reviewComment;
    }
}
