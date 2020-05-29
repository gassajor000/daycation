package com.tripdazzle.server.fakedb;

import com.tripdazzle.server.DatabaseError;
import com.tripdazzle.server.datamodels.ActivityData;
import com.tripdazzle.server.datamodels.ActivityType;
import com.tripdazzle.server.datamodels.BitmapData;
import com.tripdazzle.server.datamodels.CreatorData;
import com.tripdazzle.server.datamodels.ProfileData;
import com.tripdazzle.server.datamodels.ProfilePictureData;
import com.tripdazzle.server.datamodels.ReviewData;
import com.tripdazzle.server.datamodels.ReviewerData;
import com.tripdazzle.server.datamodels.TripData;
import com.tripdazzle.server.datamodels.UserData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FakeDatabase {
    private HashMap<Integer, String> fileNames;
    private HashMap<Integer, FakeTrip> trips = new HashMap<>();
    private HashMap<Integer, FakeReview> reviews = new HashMap<>();
    private HashMap<String, FakeUser> users = new HashMap<>();
    private HashMap<String, List<Integer>> recommendations = new HashMap<>();

    private ImageFactory imageFactory = new ImageFactory();
    private UserFactory userFactory = new UserFactory();


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
        trips.put(301, new FakeTrip("SD Vacay", 301, "mscott","Fun Trip around the San Diego Bay.",
                401, new ActivityData[]{activities[0], activities[1], activities[2]},
                (float) 3.7, new ArrayList<Integer>(Arrays.asList(501, 502, 503, 504, 505, 506, 507,
                508, 509, 510, 511, 512, 513, 514, 515, 516, 517, 518, 519, 520))));
        trips.put(302, new FakeTrip("La Jolla Trip", 302, "jhalpert","Fun Trip in La Jolla.",
                403, new ActivityData[]{activities[2], activities[3], activities[4]},
                (float) 4.2, new ArrayList<Integer>(Arrays.asList(501, 502, 503, 504, 505, 506, 507))));
        trips.put(303, new FakeTrip("Balboa Park", 303, "mscott","A day at Balboa Park.",
                402, new ActivityData[]{activities[2], activities[3], activities[4]},
                (float) 4.5, new ArrayList<Integer>(Arrays.asList(501, 502, 503, 504, 505, 506, 507))));
        trips.put(304, new FakeTrip("Zoo Trip", 304, "mscott","Things to do around the Zoo",
                404, new ActivityData[]{activities[2], activities[3], activities[4]},
                (float) 2.3, new ArrayList<Integer>(Arrays.asList(501, 502, 503, 504, 505, 506, 507))));

        // Users
        users.put("mscott", new FakeUser("mscott", 405, "Michael", "Scott", "Scranton, PA", "password123",
                new ArrayList<>(Arrays.asList(301, 303, 304)), new ArrayList<>(Arrays.asList(501, 506, 507, 508, 509, 510)), new ArrayList<>(Arrays.asList(302, 301, 304))));
        users.put("jhalpert", new FakeUser("jhalpert", 406, "Jim", "Halpert", "Scranton, PA", "password123",
                new ArrayList<>(Arrays.asList(302)), new ArrayList<Integer>(), new ArrayList<>(Arrays.asList(304, 301, 303))));

        // Reviews
        reviews.putAll(new HashMap<Integer, FakeReview>(){{
            put(501,  new FakeReview(501, "mscott", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 501"));
            put(502,  new FakeReview(502, "jhalpert", (float) 1.0, new Date(), "Totally awesome experience but I lost my iPhone 502"));
            put(503,  new FakeReview(503, "jhalpert", (float) 5.0, new Date(), "Totally awesome experience but I lost my iPhone 503"));
            put(504,  new FakeReview(504, "jhalpert", (float) 3.0, new Date(), "Totally awesome experience but I lost my iPhone 504"));
            put(505,  new FakeReview(505, "jhalpert", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 505"));
            put(506,  new FakeReview(506, "mscott", (float) 1.0, new Date(), "Totally awesome experience but I lost my iPhone 506"));
            put(507,  new FakeReview(507, "mscott", (float) 2.0, new Date(), "Totally awesome experience but I lost my iPhone 507"));
            put(508,  new FakeReview(508, "mscott", (float) 3.0, new Date(), "Totally awesome experience but I lost my iPhone 508"));
            put(509,  new FakeReview(509, "mscott", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 509"));
            put(510,  new FakeReview(510, "mscott", (float) 5.0, new Date(), "Totally awesome experience but I lost my iPhone 510"));
            put(511,  new FakeReview(511, "mscott", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 511"));
            put(512,  new FakeReview(512, "jhalpert", (float) 1.0, new Date(), "Totally awesome experience but I lost my iPhone 512"));
            put(513,  new FakeReview(513, "jhalpert", (float) 5.0, new Date(), "Totally awesome experience but I lost my iPhone 513"));
            put(514,  new FakeReview(514, "jhalpert", (float) 3.0, new Date(), "Totally awesome experience but I lost my iPhone 514"));
            put(515,  new FakeReview(515, "jhalpert", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 515"));
            put(516,  new FakeReview(516, "mscott", (float) 1.0, new Date(), "Totally awesome experience but I lost my iPhone 516"));
            put(517,  new FakeReview(517, "mscott", (float) 2.0, new Date(), "Totally awesome experience but I lost my iPhone 517"));
            put(518,  new FakeReview(518, "mscott", (float) 3.0, new Date(), "Totally awesome experience but I lost my iPhone 518"));
            put(519,  new FakeReview(519, "mscott", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 519"));
            put(520,  new FakeReview(520, "mscott", (float) 5.0, new Date(), "Totally awesome experience but I lost my iPhone 520"));
        }});



        // Recommendations
        recommendations.put("mscott", Arrays.asList(302, 304, 303));
        recommendations.put("jhalpert", Arrays.asList(301, 303, 304));
    }

    public void setDbFilePath(String dbFilePath) {
        this.dbFilePath = dbFilePath;
    }


    /** Retrieve a bitmap image by ID. Not found images come back null.
     * @param imgIds id of the image to fetch
     * @return Image file of the image with the matching id
     */
    public List<BitmapData> getImagesById(List<Integer> imgIds) throws DatabaseError {
        List<BitmapData> imageFiles = new ArrayList<>();
        for(Integer id: imgIds){
            String fileName = fileNames.get(id);
            if (fileName == null){
                imageFiles.add(null);
            } else {
                try {
                    imageFiles.add(new BitmapData(id, new FileInputStream(dbFilePath + "/" + fileName)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    throw new DatabaseError(e);
                }
            }
        }
        return imageFiles;
    }

    /** Retrieve a profile image by user id. Not found images come back null.
     * @param userIds usernames of the profile pictures to fetch
     * @return List of Image files of the image with the matching ids
     */
    public List<ProfilePictureData> getProfilePicturesByUserIds(List<String> userIds) throws DatabaseError {
        List<ProfilePictureData> imageFiles = new ArrayList<>();
        for(String id: userIds){
            FakeUser user = users.get(id);

            if (user == null){
                imageFiles.add(null);
                continue;
            }

            String fileName = fileNames.get(user.profileImageId);
            if (fileName == null){
                imageFiles.add(null);
            } else {
                try {
                    imageFiles.add(new ProfilePictureData(id, user.profileImageId, new FileInputStream(dbFilePath + "/" + fileName)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    throw new DatabaseError(e);
                }
            }
        }
        return imageFiles;
    }

    public List<TripData> getTripsById(List<Integer> tripIds) {
        List<TripData> ret = new ArrayList<>();
        for(Integer id: tripIds){
            ret.add(trips.get(id).toTripData(imageFactory, userFactory));
        }
        return ret;
    }

    public ProfileData getProfileById(String userId) { return users.get(userId).toProfile(imageFactory); }

    public List<ReviewData> getReviewsById(List<Integer> reviewIds){
        List<ReviewData> ret =  new ArrayList<>();
        for(Integer r: reviewIds){
            ret.add(reviews.get(r).toReviewData(userFactory));
        }
        return ret;
    }

    public List<TripData> getFavoritesByUserId(String userId){
        List<TripData> favorites = new ArrayList<>();
        for(Integer id: users.get(userId).favoriteTrips){
            favorites.add(trips.get(id).toTripData(imageFactory, userFactory));
        }
        return favorites;
    }

    public List<TripData> getRecommendedTripsForUser(String userId){
        List<TripData> recTrips = new ArrayList<>();
        for(Integer tripId: recommendations.get(userId)){
            recTrips.add(trips.get(tripId).toTripData(imageFactory, userFactory));
        }
        return recTrips;
    }

    public void toggleFavorite(String userId, Integer tripId, Boolean addFavorite){
        if(addFavorite){
            users.get(userId).favoriteTrips.add(tripId);
        } else {
            users.get(userId).favoriteTrips.remove(tripId);
        }
    }

    public UserData login(String userId, String password){
        FakeUser user = users.get(userId);
        if (user != null && user.password.equals(password)){
            return user.toUserData();
        }
        return null;
    }

    public class ImageFactory {

        public BitmapData getImage(Integer imageId){
            String fileName = fileNames.get(imageId);
            if(fileName == null){
                return null;
            }
            try {
                return new BitmapData(imageId, new FileInputStream(dbFilePath + "/" + fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        public ProfilePictureData getProfilePicture(String userId){
            Integer imageId = users.get(userId).profileImageId;
            String fileName = fileNames.get(imageId);
            if(fileName == null){
                return null;
            }
            try {
                return new ProfilePictureData(userId, imageId, new FileInputStream(dbFilePath + "/" + fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public class UserFactory {
        public UserData getUser(String userId){
            return users.get(userId).toUserData();
        }

        public CreatorData getCreator(String userId){
            return users.get(userId).toCreatorData(imageFactory);
        }

        public ProfileData getProfile(String userId){
            return users.get(userId).toProfile(imageFactory);
        }
        
        public ReviewerData getReviewer(String userId){ return users.get(userId).toReviewerData(imageFactory); }
    }
}
