package com.tripdazzle.daycation.ui.tripinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.Trip;

public class TripInfoViewModel extends ViewModel{
    private MutableLiveData<Trip> trip;
    private MutableLiveData<Boolean> inFavorites = new MutableLiveData<Boolean>();


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
}
