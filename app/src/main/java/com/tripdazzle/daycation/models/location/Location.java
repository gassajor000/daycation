package com.tripdazzle.daycation.models.location;

import com.google.android.gms.maps.model.LatLng;
import com.tripdazzle.server.datamodels.location.LocationData;

public interface Location {
    public String getName();
    public LatLng getLatLang();
    public LocationData toData();
}
