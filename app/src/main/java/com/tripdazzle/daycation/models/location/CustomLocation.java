package com.tripdazzle.daycation.models.location;

import com.google.android.gms.maps.model.LatLng;
import com.tripdazzle.server.datamodels.location.CustomLocationData;
import com.tripdazzle.server.datamodels.location.LocationData;

public class CustomLocation implements Location {
    public final String name;
    public final LatLng latLng;

    public CustomLocation(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    public CustomLocation(CustomLocationData data){
        this.name = data.name;
        this.latLng = new LatLng(data.latitude, data.longitude);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LatLng getLatLang() {
        return latLng;
    }

    @Override
    public LocationData toData() {
        return new CustomLocationData(name, latLng.latitude, latLng.longitude);
    }
}
