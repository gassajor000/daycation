package com.tripdazzle.daycation.ui.addreview;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.Reviewer;

public class AddReviewViewModel extends ViewModel {
    private MutableLiveData<Reviewer> reviewer = new MutableLiveData<>();
    private MutableLiveData<String> tripName = new MutableLiveData<>();
    private float rating = 0.0f;
    private String comment = "";


    public MutableLiveData<Reviewer> getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer.setValue(reviewer);
    }

    public MutableLiveData<String> getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName.setValue(tripName);
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
