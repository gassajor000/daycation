package com.tripdazzle.daycation.models.location;

import com.google.android.libraries.places.api.model.Place;
import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.server.datamodels.location.CustomLocationData;
import com.tripdazzle.server.datamodels.location.LocationData;
import com.tripdazzle.server.datamodels.location.PlaceLocationData;

/* Location Builder*/
public class LocationBuilder {
    private DataModel.PlacesManager placesManager;

    public LocationBuilder(DataModel.PlacesManager placesManager) {
        this.placesManager = placesManager;
    }

    /* This is a blocking call and will block while Places are being loaded.*/
    public Location makeLocationBlocking(LocationData locationData){
        if(locationData instanceof CustomLocationData){
            return new CustomLocation((CustomLocationData) locationData);
        } else if(locationData instanceof PlaceLocationData){
            Place place =  placesManager.addAndGetPlaceById(((PlaceLocationData) locationData).placeId);
            return new PlaceLocation(place);
        } else {
            return new NullLocation();
        }
    }
}
