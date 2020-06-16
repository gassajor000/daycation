package com.tripdazzle.server.datamodels.location;

public class CustomLocationData implements LocationData {
    public final String name;
    public final float latitude;
    public final float longitude;

    public CustomLocationData(String name, float latitude, float longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
