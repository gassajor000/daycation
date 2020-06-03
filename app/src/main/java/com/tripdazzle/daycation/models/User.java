package com.tripdazzle.daycation.models;

import com.tripdazzle.server.datamodels.UserData;

import java.util.List;

public class User {
    public final String userId;
    public final String firstName;
    public final String lastName;
    public final String city;
    public final ProfilePicture profilePicture;
    public final List<Integer> reviews;
    public final List<Integer> favoriteTrips;
    public final List<Integer> createdTrips;

    public User(String userId, ProfilePicture profilePicture, String firstName, String lastName, String city, List<Integer> createdTrips, List<Integer> reviews, List<Integer> favoriteTrips) {
        this.userId = userId;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.createdTrips = createdTrips;
        this.reviews = reviews;
        this.favoriteTrips = favoriteTrips;
    }

    public User(UserData data) {
        this.userId = data.userId;
        this.profilePicture = new ProfilePicture(data.profilePicture);
        this.firstName = data.firstName;
        this.lastName = data.lastName;
        this.city = data.city;
        this.createdTrips = data.createdTrips;
        this.reviews = data.reviews;
        this.favoriteTrips = data.favoriteTrips;
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
