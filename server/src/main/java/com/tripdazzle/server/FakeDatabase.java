package com.tripdazzle.server;

import com.tripdazzle.server.datamodels.ActivityData;
import com.tripdazzle.server.datamodels.ActivityType;
import com.tripdazzle.server.datamodels.TripData;

import java.io.File;
import java.util.HashMap;

class FakeDatabase {
    private HashMap<Integer, String> fileNames;
    private HashMap<Integer, TripData> trips = new HashMap<>();

    private String dbFilePath;

    FakeDatabase(String dbFilePath) {
        this.dbFilePath = dbFilePath;
        this.fileNames = new HashMap<>();

        // Images
        fileNames.put(444, "mission_bay.png");

        // Trips
        ActivityData[] activities = {
                new ActivityData(ActivityType.HIKING, "Rose Canyon", "Hike Rose Canyon"),
                new ActivityData(ActivityType.ICE_CREAM, "Shake Shack", "Get Ice cream at Shake Shack"),
                new ActivityData(ActivityType.BEACH, "Mission Beach", "Go Swimming at Mission Beach")
        };
        trips.put(333, new TripData("SD Vacay", 333, "gassajor",
                "Fun Trip around the San Diego Bay.",   444, activities));
    }

    public void setDbFilePath(String dbFilePath) {
        this.dbFilePath = dbFilePath;
    }


    /** Retrieve a bitmap image by ID
     * @param imgId id of the image to fetch
     * @return Image file of the image with the matching id
     */
    File getImageById(int imgId){
        String fileName = fileNames.get(imgId);
        return new File(dbFilePath + "/" + fileName);
    }

    TripData getTripById(int tripId){
        return trips.get(tripId);
    }
}
