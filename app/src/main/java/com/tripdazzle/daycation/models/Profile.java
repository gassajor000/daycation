package com.tripdazzle.daycation.models;

import com.tripdazzle.server.datamodels.ProfileData;

import java.util.List;

public class Profile {
    public final String userId;
    public final ProfilePicture profilePicture;
    public final String firstName;
    public final String lastName;
    public final String city;
    public final List<Integer> createdTrips;

    public Profile(String userId, ProfilePicture profilePicture, String firstName, String lastName, String city, List<Integer> createdTrips) {
        this.userId = userId;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.createdTrips = createdTrips;
    }

    public Profile(ProfileData data){
        this.userId = data.userId;
        this.profilePicture = new ProfilePicture(data.profilePicture);
        this.firstName = data.firstName;
        this.lastName = data.lastName;
        this.city = data.city;
        this.createdTrips = data.createdTrips;
    }
}
