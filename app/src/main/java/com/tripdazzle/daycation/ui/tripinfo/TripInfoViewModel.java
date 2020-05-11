package com.tripdazzle.daycation.ui.tripinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TripInfoViewModel extends ViewModel {
    private MutableLiveData<Trip> trip;
    public LiveData<Trip> getTrip() {
        if (trip == null) {
            trip = new MutableLiveData<Trip>();
        }
        return trip;
    }

    public void loadTrip(Trip trip) {
        if (this.trip == null) {
            this.trip = new MutableLiveData<Trip>();
        }
        this.trip.setValue(trip);
    }
}
