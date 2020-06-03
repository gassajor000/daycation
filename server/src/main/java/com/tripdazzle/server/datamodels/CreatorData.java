package com.tripdazzle.server.datamodels;

/* Represents the creator of a trip*/
public class CreatorData {
    public final String userId;
    public final ProfilePictureData profilePicture;
    public final String firstName;
    public final String lastName;

    public CreatorData(String userId, ProfilePictureData profilePicture, String firstName, String lastName) {
        this.userId = userId;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
