package com.tripdazzle.daycation.ui.triplist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripListViewModel extends ViewModel {
    private MutableLiveData<List<Trip>> trips = new MutableLiveData<>();

    public LiveData<List<Trip>> getTrips() {
        if (trips.getValue() ==  null){
            trips.setValue(new ArrayList<Trip>());
        }
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips.setValue(trips);
    }
}
