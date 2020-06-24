package com.tripdazzle.daycation.models.location;

import com.google.android.gms.maps.model.LatLng;
import com.tripdazzle.server.datamodels.location.CustomLocationData;
import com.tripdazzle.server.datamodels.location.LocationData;

public class NullLocation implements Location {
    public final String name;
    public final LatLng latLng;

    public NullLocation() {
        this.name = "null location";
        this.latLng = new LatLng(0,0);
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
