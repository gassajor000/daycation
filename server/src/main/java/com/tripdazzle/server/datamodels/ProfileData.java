package com.tripdazzle.server.datamodels;

import java.util.List;

public class ProfileData {
    public final String userId;
    public final Integer profileImageId;
    public final String firstName;
    public final String lastName;
    public final String city;
    public final List<Integer> createdTrips;

    public ProfileData(String userId, Integer profileImageId, String firstName, String lastName, String city, List<Integer> createdTrips) {
        this.userId = userId;
        this.profileImageId = profileImageId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.createdTrips = createdTrips;
    }
}
