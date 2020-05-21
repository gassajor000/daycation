package com.tripdazzle.server;

import com.tripdazzle.server.datamodels.ActivityData;
import com.tripdazzle.server.datamodels.ActivityType;
import com.tripdazzle.server.datamodels.ReviewData;
import com.tripdazzle.server.datamodels.TripData;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

class FakeDatabase {
    private HashMap<Integer, String> fileNames;
    private HashMap<Integer, TripData> trips = new HashMap<>();
    private HashMap<Integer, ReviewData> reviews = new HashMap<>();

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
                "Fun Trip around the San Diego Bay.",   444, activities,
                (float) 3.7, new ArrayList<Integer>(Arrays.asList(501, 502, 503, 504, 505, 506, 507,
                508, 509, 510, 511, 512, 513, 514, 515, 516, 517, 518, 519, 520))));

        // Reviews
        reviews.putAll(new HashMap<Integer, ReviewData>(){{
            put(501,  new ReviewData(501, "Michael Scott", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 501"));
            put(502,  new ReviewData(502, "Kevin Malone", (float) 1.0, new Date(), "Totally awesome experience but I lost my iPhone 502"));
            put(503,  new ReviewData(503, "Jim Halpert", (float) 5.0, new Date(), "Totally awesome experience but I lost my iPhone 503"));
            put(504,  new ReviewData(504, "Pam Beesley", (float) 3.0, new Date(), "Totally awesome experience but I lost my iPhone 504"));
            put(505,  new ReviewData(505, "Angela Martin", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 505"));
            put(506,  new ReviewData(506, "Michael Scott", (float) 1.0, new Date(), "Totally awesome experience but I lost my iPhone 506"));
            put(507,  new ReviewData(507, "Michael Scott", (float) 2.0, new Date(), "Totally awesome experience but I lost my iPhone 507"));
            put(508,  new ReviewData(508, "Michael Scott", (float) 3.0, new Date(), "Totally awesome experience but I lost my iPhone 508"));
            put(509,  new ReviewData(509, "Michael Scott", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 509"));
            put(510,  new ReviewData(510, "Michael Scott", (float) 5.0, new Date(), "Totally awesome experience but I lost my iPhone 510"));
            put(511,  new ReviewData(511, "Michael Scott", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 511"));
            put(512,  new ReviewData(512, "Kevin Malone", (float) 1.0, new Date(), "Totally awesome experience but I lost my iPhone 512"));
            put(513,  new ReviewData(513, "Jim Halpert", (float) 5.0, new Date(), "Totally awesome experience but I lost my iPhone 513"));
            put(514,  new ReviewData(514, "Pam Beesley", (float) 3.0, new Date(), "Totally awesome experience but I lost my iPhone 514"));
            put(515,  new ReviewData(515, "Angela Martin", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 515"));
            put(516,  new ReviewData(516, "Michael Scott", (float) 1.0, new Date(), "Totally awesome experience but I lost my iPhone 516"));
            put(517,  new ReviewData(517, "Michael Scott", (float) 2.0, new Date(), "Totally awesome experience but I lost my iPhone 517"));
            put(518,  new ReviewData(518, "Michael Scott", (float) 3.0, new Date(), "Totally awesome experience but I lost my iPhone 518"));
            put(519,  new ReviewData(519, "Michael Scott", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 519"));
            put(520,  new ReviewData(520, "Michael Scott", (float) 5.0, new Date(), "Totally awesome experience but I lost my iPhone 520"));
        }});
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

    List<ReviewData> getReviewsById(List<Integer> reviewIds){
        List<ReviewData> ret =  new ArrayList<>();
        for(Integer r: reviewIds){
            ret.add(reviews.get(r));
        }
        return ret;
    }
}
