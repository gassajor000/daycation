package com.tripdazzle.daycation.models.location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.tripdazzle.server.datamodels.location.LocationData;
import com.tripdazzle.server.datamodels.location.PlaceLocationData;

public class PlaceLocation implements Location {
    public final Place place;

    public PlaceLocation(Place place) {
        this.place = place;
    }

    @Override
    public String getName() {
        return place.getName();
    }

    @Override
    public LatLng getLatLang() {
        return place.getLatLng();
    }

    @Override
    public LocationData toData() {
        return new PlaceLocationData(place.getId());
    }
}
