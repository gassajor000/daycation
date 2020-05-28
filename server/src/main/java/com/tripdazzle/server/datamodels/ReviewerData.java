package com.tripdazzle.server.datamodels;

/* Represents a reviewer of a trip*/
public class ReviewerData {
    public final String userId;
    public final ProfilePictureData profilePicture;
    public final String firstName;
    public final String lastName;

    public ReviewerData(String userId, ProfilePictureData profilePicture, String firstName, String lastName) {
        this.userId = userId;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
