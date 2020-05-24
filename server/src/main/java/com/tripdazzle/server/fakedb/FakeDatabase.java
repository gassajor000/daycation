package com.tripdazzle.server.fakedb;

import com.tripdazzle.server.datamodels.ActivityData;
import com.tripdazzle.server.datamodels.ActivityType;
import com.tripdazzle.server.datamodels.ProfileData;
import com.tripdazzle.server.datamodels.ReviewData;
import com.tripdazzle.server.datamodels.TripData;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FakeDatabase {
    private HashMap<Integer, String> fileNames;
    private HashMap<Integer, TripData> trips = new HashMap<>();
    private HashMap<Integer, ReviewData> reviews = new HashMap<>();
    private HashMap<String, FakeUser> users = new HashMap<>();

    private String dbFilePath;

    public FakeDatabase(String dbFilePath) {
        this.dbFilePath = dbFilePath;
        this.fileNames = new HashMap<>();

        // Images
        fileNames.put(401, "mission_bay.png");
        fileNames.put(402, "balboa.png");
        fileNames.put(403, "lajolla.png");
        fileNames.put(404, "zoo.png");
        fileNames.put(405, "mscott.png");
        fileNames.put(406, "jhalpert.png");

        // Trips
        ActivityData[] activities = {
                new ActivityData(ActivityType.HIKING, "Rose Canyon", "Hike Rose Canyon"),
                new ActivityData(ActivityType.ICE_CREAM, "Shake Shack", "Get Ice cream at Shake Shack"),
                new ActivityData(ActivityType.BEACH, "Mission Beach", "Go Swimming at Mission Beach"),
                new ActivityData(ActivityType.SWIMMING, "Mission Bay", "Swim Across Mission Bay"),
                new ActivityData(ActivityType.ICE_CREAM, "In n Out", "Get an In n Out Shake"),
                new ActivityData(ActivityType.BEACH, "Black's Beach", "Go surfing at Black's Beach")
        };
        trips.put(301, new TripData("SD Vacay", 301, "mscott","Fun Trip around the San Diego Bay.",
                401, new ActivityData[]{activities[0], activities[1], activities[2]},
                (float) 3.7, new ArrayList<Integer>(Arrays.asList(501, 502, 503, 504, 505, 506, 507,
                508, 509, 510, 511, 512, 513, 514, 515, 516, 517, 518, 519, 520))));
        trips.put(302, new TripData("La Jolla Trip", 302, "mscott","Fun Trip in La Jolla.",
                403, new ActivityData[]{activities[2], activities[3], activities[4]},
                (float) 4.2, new ArrayList<Integer>(Arrays.asList(501, 502, 503, 504, 505, 506, 507))));
        trips.put(303, new TripData("Balboa Park", 303, "mscott","A day at Balboa Park.",
                402, new ActivityData[]{activities[2], activities[3], activities[4]},
                (float) 4.5, new ArrayList<Integer>(Arrays.asList(501, 502, 503, 504, 505, 506, 507))));
        trips.put(304, new TripData("Zoo Trip", 304, "mscott","Things to do around the Zoo",
                404, new ActivityData[]{activities[2], activities[3], activities[4]},
                (float) 2.3, new ArrayList<Integer>(Arrays.asList(501, 502, 503, 504, 505, 506, 507))));


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

        // Users
        users.put("mscott", new FakeUser("mscott", 405, "Michael", "Scott", "Scranton, PA", "password123", Arrays.asList(301, 302, 303, 304), Arrays.asList(501, 506, 507, 508, 509, 510), Arrays.asList(302, 301, 304)));
        users.put("jhalpert", new FakeUser("jhalpert", 406, "Jim", "Halpert", "Scranton, PA", "password123"));
    }

    public void setDbFilePath(String dbFilePath) {
        this.dbFilePath = dbFilePath;
    }


    /** Retrieve a bitmap image by ID. Not found images come back null.
     * @param imgIds id of the image to fetch
     * @return Image file of the image with the matching id
     */
    public List<File> getImagesById(List<Integer> imgIds){
        List<File> imageFiles = new ArrayList<>();
        for(Integer id: imgIds){
            String fileName = fileNames.get(id);
            if (fileName == null){
                imageFiles.add(null);
            } else {
                imageFiles.add(new File(dbFilePath + "/" + fileName));
            }
        }
        return imageFiles;
    }

    public List<TripData> getTripsById(List<Integer> tripIds) {
        List<TripData> ret = new ArrayList<>();
        for(Integer id: tripIds){
            ret.add(trips.get(id));
        }
        return ret;
    }

    public ProfileData getProfileById(String userId) { return users.get(userId).toProfile(); }

    public List<ReviewData> getReviewsById(List<Integer> reviewIds){
        List<ReviewData> ret =  new ArrayList<>();
        for(Integer r: reviewIds){
            ret.add(reviews.get(r));
        }
        return ret;
    }

    public List<TripData> getFavoritesByUserId(String userId){
        List<TripData> favorites = new ArrayList<>();
        for(Integer id: users.get(userId).favoriteTrips){
            favorites.add(trips.get(id));
        }
        return favorites;
    }
}
