package com.tripdazzle.daycation.ui.reviewslist;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.Review;

import java.util.List;

public class ReviewsListViewModel extends ViewModel {
    private ObservableArrayList<Review> reviews =  new ObservableArrayList<>();
    private int totalNumReviews = 0;
    private boolean loading = false;


    public List<Review> getReviews() {
        return reviews;
    }

    public void setLoadingReviews(Boolean isLoading){
        loading = isLoading;
        if(isLoading){
            // Add a null element for progress bar
//            reviews.add(null);
        } else if(reviews.size() > 0){
            // Remove null element
//            reviews.remove(reviews.size() - 1);
        }
    }

    public void addReviews(List<Review> newReviews) {
        reviews.addAll(newReviews);
    }

    public void clearReviews(){
        reviews.clear();
    }

    public Integer numReviewsLoaded(){
        return reviews.size();
    }

    public void subscribeReviews(ObservableList.OnListChangedCallback observer){
        this.reviews.addOnListChangedCallback(observer);
    }

    public void setTotalNumReviews(int totalNumReviews) {
        this.totalNumReviews = totalNumReviews;
    }

    public boolean allReviewsLoaded(){
        return reviews.size() == totalNumReviews;
    }

    public boolean isLoading(){
        return loading;
    }

}
