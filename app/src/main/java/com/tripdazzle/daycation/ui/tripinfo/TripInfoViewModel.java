package com.tripdazzle.daycation.ui.tripinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.Review;
import com.tripdazzle.daycation.models.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripInfoViewModel extends ViewModel{
    private MutableLiveData<Trip> trip;
    private MutableLiveData<Boolean> inFavorites = new MutableLiveData<Boolean>();
    private List<Review> reviews =  new ArrayList<>();


    public LiveData<Trip> getTrip() {
        if (trip == null) {
            trip = new MutableLiveData<Trip>();
        }
        return trip;
    }

    public void setTrip(Trip trip, boolean isFavorite) {
        if (this.trip == null) {
            this.trip = new MutableLiveData<Trip>();
        }
        this.trip.setValue(trip);
        this.inFavorites.setValue(isFavorite);
    }

    public MutableLiveData<Boolean> getInFavorites() {
        return inFavorites;
    }

    public void setInFavorites(MutableLiveData<Boolean> inFavorites) {
        this.inFavorites = inFavorites;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setLoadingReviews(Boolean isLoading){
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
}
