package com.tripdazzle.daycation.models;

import com.tripdazzle.server.datamodels.ReviewerData;

public class Reviewer {
    public final String userId;
    public final ProfilePicture profilePicture;
    public final String firstName;
    public final String lastName;

    public Reviewer(String userId, ProfilePicture profilePicture, String firstName, String lastName) {
        this.userId = userId;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Reviewer(ReviewerData data){
        userId = data.userId;
        if (data.profilePicture != null){
            profilePicture = new ProfilePicture(data.profilePicture);
        } else{
            profilePicture = null;
        }

        firstName = data.firstName;
        lastName = data.lastName;
    }
}
