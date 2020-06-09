package com.tripdazzle.server.fakedb;

import com.tripdazzle.server.datamodels.BitmapData;
import com.tripdazzle.server.datamodels.ProfilePictureData;

import java.io.ByteArrayInputStream;

public class FakeImage {
    public final byte[] data;
    public final Integer id;

    public FakeImage(Integer id, byte[] data) {
        this.data = data;
        this.id = id;
    }

    public BitmapData toData(){
        return new BitmapData(id, new ByteArrayInputStream(data));
    }

    public ProfilePictureData toProfilePictureData(String userid){
        return new ProfilePictureData(userid, id, new ByteArrayInputStream(data));
    }
}
