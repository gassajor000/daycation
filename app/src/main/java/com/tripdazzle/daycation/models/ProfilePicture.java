package com.tripdazzle.daycation.models;

import com.tripdazzle.server.datamodels.ProfilePictureData;

public class ProfilePicture {
    public final String userId;
    public final BitmapImage bitmap;

    public ProfilePicture(String userId, BitmapImage bitmap) {
        this.userId = userId;
        this.bitmap = bitmap;
    }

    public ProfilePicture(ProfilePictureData data){
        this.userId = data.userId;
        this.bitmap = new BitmapImage(data.bitmap);
    }
}
