package com.tripdazzle.daycation.models.location;

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

    public Location makeLocation(LocationData locationData){
        if(locationData instanceof CustomLocationData){
            return new CustomLocation((CustomLocationData) locationData);
        } else if(locationData instanceof PlaceLocationData){
            return new PlaceLocation(placesManager.getPlaceById(((PlaceLocationData) locationData).placeId));
        } else {
            return null;
        }
    }
}
