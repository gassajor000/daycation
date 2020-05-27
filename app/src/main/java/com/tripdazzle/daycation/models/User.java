package com.tripdazzle.daycation.models;

import com.tripdazzle.server.datamodels.UserData;

import java.util.List;

public class User extends UserData {

    public User(String userId, Integer profileImageId, String firstName, String lastName, String city, List<Integer> createdTrips, List<Integer> reviews, List<Integer> favoriteTrips) {
        super(userId, profileImageId, firstName, lastName, city, createdTrips, reviews, favoriteTrips);
    }

    public User(UserData data) {
        super(data.userId, data.profileImageId, data.firstName, data.lastName, data.city, data.createdTrips, data.reviews, data.favoriteTrips);
    }

    public Profile toProfile(){
        return new Profile(userId, profileImageId, firstName, lastName, city, createdTrips);
    }

    public Boolean inFavorites(Integer tripId){
        return favoriteTrips.contains(tripId);
    }

    public void toggleFavorite(Integer tripId, Boolean addFavorite){
        Boolean alreadyInFavorites = inFavorites(tripId);
        if (addFavorite && !alreadyInFavorites){
            favoriteTrips.add(tripId);
        } else if (!addFavorite && alreadyInFavorites){
            favoriteTrips.remove(tripId);
        }
    }
}
