package com.tripdazzle.server.datamodels.location;

public class CustomLocationData implements LocationData {
    public final String name;
    public final double latitude;
    public final double longitude;

    public CustomLocationData(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
