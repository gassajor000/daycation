package com.tripdazzle.daycation.models;

import android.graphics.Bitmap;

import com.tripdazzle.server.datamodels.BitmapData;
import com.tripdazzle.server.datamodels.ProfilePictureData;

public class ProfilePicture extends BitmapImage{
    public final String userId;

    public ProfilePicture(String userId, Integer id, Bitmap bitmap) {
        super(bitmap, id);
        this.userId = userId;
    }

    public ProfilePicture(ProfilePictureData data){
        super((BitmapData) data);
        this.userId = data.userId;
    }

    public ProfilePictureData toData(){
        BitmapData bitmapData = super.toData();
        return new ProfilePictureData(userId, bitmapData.id, bitmapData.dataStream);
    }
}
