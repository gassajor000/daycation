package com.tripdazzle.server.fakedb;

import com.tripdazzle.server.datamodels.ProfileData;

import java.util.ArrayList;
import java.util.List;

public class FakeUser {
    public  String userId;
    public final String password;
    public  String firstName;
    public  String lastName;
    public  String city;
    public  Integer profileImageId;
    public  List<Integer> reviews;
    public  List<Integer> favoriteTrips;
    public  List<Integer> createdTrips;


    public FakeUser(String userId, Integer profileImageId, String firstName, String lastName, String city, String password) {
        // Shortcut when creating a new User
        this(userId, profileImageId, firstName, lastName, city, password, new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>());
    }

    public FakeUser(String userId, Integer profileImageId, String firstName, String lastName, String city, String password, List<Integer> createdTrips, List<Integer> reviews, List<Integer> favoriteTrips) {
        this.userId = userId;
        this.profileImageId = profileImageId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.createdTrips = createdTrips;
        this.password = password;
        this.reviews = reviews;
        this.favoriteTrips = favoriteTrips;
    }

    public ProfileData toProfile(){
        return new ProfileData(userId, profileImageId, firstName, lastName, city, createdTrips);
    }
}
