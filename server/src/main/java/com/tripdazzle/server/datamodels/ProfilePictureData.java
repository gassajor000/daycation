package com.tripdazzle.server.datamodels;

import java.io.InputStream;

public class ProfilePictureData extends BitmapData{
    public final String userId;

    public ProfilePictureData(String userId, Integer id, InputStream dataStream) {
        super(id, dataStream);
        this.userId = userId;
    }
}
