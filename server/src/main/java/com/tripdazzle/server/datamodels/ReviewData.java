package com.tripdazzle.server.datamodels;

import java.util.Date;

public class ReviewData {
    public final Integer id;
    public final ReviewerData reviewer;
    public final Float reviewRating;
    public final Date reviewDate;
    public final String reviewComment;

    public ReviewData(Integer id, ReviewerData reviewer, Float reviewRating, Date reviewDate, String reviewComment) {
        this.id = id;
        this.reviewer = reviewer;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
        this.reviewComment = reviewComment;
    }
}
