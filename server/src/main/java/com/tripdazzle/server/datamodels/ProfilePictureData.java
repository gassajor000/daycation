package com.tripdazzle.server.datamodels;

public class ProfilePictureData {
    public final String userId;
    public final BitmapData bitmap;

    public ProfilePictureData(String userId, BitmapData bitmap) {
        this.userId = userId;
        this.bitmap = bitmap;
    }
}
