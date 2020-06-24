package com.tripdazzle.daycation.ui.locationselector;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.location.Location;

public class LocationSelectorViewModel extends ViewModel {
    private MutableLiveData<Location> location = new MutableLiveData<>();
    private MutableLiveData<String> selectorText = new MutableLiveData<>("Select Location");

    public MutableLiveData<Location> getLocation() {
        return location;
    }

    public void setLocation(MutableLiveData<Location> location) {
        this.location = location;
    }

    public void setLocation(Location location){
        this.location.setValue(location);
        this.selectorText.setValue(location.getName());
    }

    public MutableLiveData<String> getSelectorText() {
        return selectorText;
    }

    public void setSelectorText(MutableLiveData<String> selectorText) {
        this.selectorText = selectorText;
    }
}
