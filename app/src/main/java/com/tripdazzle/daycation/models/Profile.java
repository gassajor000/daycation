package com.tripdazzle.daycation.models;

import com.tripdazzle.server.datamodels.ProfileData;

import java.util.List;

public class Profile extends ProfileData {
    public Profile(String userId, Integer profileImageId, String firstName, String lastName, String city, List<Integer> createdTrips) {
        super(userId, profileImageId, firstName, lastName, city, createdTrips);
    }

    public Profile(ProfileData data){
        super(data.userId, data.profileImageId, data.firstName, data.lastName, data.city, data.createdTrips);

    }
}
