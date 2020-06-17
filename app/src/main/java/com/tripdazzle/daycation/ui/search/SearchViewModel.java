package com.tripdazzle.daycation.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.MarkerOptions;
import com.tripdazzle.daycation.models.Trip;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<Trip> selectedTrip = new MutableLiveData<>();
    public final List<MarkerOptions> markers = new ArrayList<>();

    public MutableLiveData<Trip> getSelectedTrip() {
        return selectedTrip;
    }

    public void setSelectedTrip(MutableLiveData<Trip> selectedTrip) {
        this.selectedTrip = selectedTrip;
    }

    public void setSelectedTrip(Trip trip){
        this.selectedTrip.setValue(trip);
    }
}
