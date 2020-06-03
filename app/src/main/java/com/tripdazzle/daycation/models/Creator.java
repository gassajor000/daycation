package com.tripdazzle.daycation.models;

import com.tripdazzle.server.datamodels.CreatorData;

public class Creator {
    public final String userId;
    public final ProfilePicture profilePicture;
    public final String firstName;
    public final String lastName;

    public Creator(String userId, ProfilePicture profilePicture, String firstName, String lastName) {
        this.userId = userId;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Creator(CreatorData data){
        userId = data.userId;
        profilePicture = new ProfilePicture(data.profilePicture);
        firstName = data.firstName;
        lastName = data.lastName;
    }
}
