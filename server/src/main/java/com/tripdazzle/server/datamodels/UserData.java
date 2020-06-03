package com.tripdazzle.server.datamodels;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    public final String userId;
    public final String firstName;
    public final String lastName;
    public final String city;
    public final ProfilePictureData profilePicture;
    public final List<Integer> reviews;
    public final List<Integer> favoriteTrips;
    public final List<Integer> createdTrips;


    public UserData(String userId, ProfilePictureData profileImageId, String firstName, String lastName, String city) {
        // Shortcut when creating a new User
        this(userId, profileImageId, firstName, lastName, city, new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>());
    }

    public UserData(String userId, ProfilePictureData profilePicture, String firstName, String lastName, String city, List<Integer> createdTrips, List<Integer> reviews, List<Integer> favoriteTrips) {
        this.userId = userId;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.createdTrips = createdTrips;
        this.reviews = reviews;
        this.favoriteTrips = favoriteTrips;
    }
}
