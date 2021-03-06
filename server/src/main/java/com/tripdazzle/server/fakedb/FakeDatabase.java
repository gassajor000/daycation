package com.tripdazzle.server.fakedb;

import com.tripdazzle.server.DatabaseError;
import com.tripdazzle.server.ServerError;
import com.tripdazzle.server.datamodels.ActivityData;
import com.tripdazzle.server.datamodels.ActivityTypeData;
import com.tripdazzle.server.datamodels.BitmapData;
import com.tripdazzle.server.datamodels.CreatorData;
import com.tripdazzle.server.datamodels.ProfileData;
import com.tripdazzle.server.datamodels.ProfilePictureData;
import com.tripdazzle.server.datamodels.ReviewData;
import com.tripdazzle.server.datamodels.ReviewerData;
import com.tripdazzle.server.datamodels.TripData;
import com.tripdazzle.server.datamodels.UserData;
import com.tripdazzle.server.datamodels.feed.FeedEventData;
import com.tripdazzle.server.datamodels.location.PlaceLocationData;
import com.tripdazzle.server.fakedb.feed.FakeAddFavoriteEvent;
import com.tripdazzle.server.fakedb.feed.FakeCreatedTripEvent;
import com.tripdazzle.server.fakedb.feed.FakeFeedEvent;
import com.tripdazzle.server.fakedb.feed.FakeReviewEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FakeDatabase {
    private HashMap<Integer, FakeImage> images = new HashMap<>();
    private HashMap<Integer, FakeTrip> trips = new HashMap<>();
    private HashMap<Integer, FakeReview> reviews = new HashMap<>();
    private HashMap<String, FakeUser> users = new HashMap<>();
    private HashMap<String, List<Integer>> recommendations = new HashMap<>();
    private HashMap<String, List<FakeFeedEvent>> newsFeeds = new HashMap<>();

    private ImageFactory imageFactory = new ImageFactory();
    private UserFactory userFactory = new UserFactory();
    private TripFactory tripFactory = new TripFactory();
    private ReviewFactory reviewFactory = new ReviewFactory();

    private int nextTrip;
    private int nextReview;
    private int nextImage;

    private String dbFilePath;

    public FakeDatabase(String dbFilePath) {
        this.dbFilePath = dbFilePath;

        // Images
        nextImage = 401;

        // Trips
        ActivityData[] activities = {
                new ActivityData(ActivityTypeData.HIKING, new PlaceLocationData("ChIJIeqEu0gB3IARRdcuT4Ya5gI"), "Hike Rose Canyon"),
                new ActivityData(ActivityTypeData.ICE_CREAM, new PlaceLocationData("ChIJwT8jJCVV2YARJ40GRpZKVG8"), "Get Ice cream at Shake Shack"),
                new ActivityData(ActivityTypeData.BEACH, new PlaceLocationData("ChIJ9dZdfnyq3oARI_cFYz7QSKM"), "Go Swimming at Mission Beach"),
                new ActivityData(ActivityTypeData.SWIMMING, new PlaceLocationData("ChIJm4tNIXaq3oARZ_p_loyw1wc"), "Swim Across Mission Bay"),
                new ActivityData(ActivityTypeData.ICE_CREAM, new PlaceLocationData("ChIJWzruHEsA3IARf3hTyv_2gT8"), "Get an In n Out Shake"),
                new ActivityData(ActivityTypeData.BEACH, new PlaceLocationData("ChIJKw-CyI8G3IARj9ySO5sCoc4"), "Go surfing at Black's Beach")
        };
        trips.put(301, new FakeTrip("SD Vacay", 301, "mscott", new PlaceLocationData("ChIJSx6SrQ9T2YARed8V_f0hOg0"), "Fun Trip around the San Diego Bay.",
                401, new ActivityData[]{activities[0], activities[1], activities[2]},
                (float) 3.7, new ArrayList<Integer>(Arrays.asList(501, 502, 503, 504, 505, 506, 507,
                508, 509, 510, 511, 512, 513, 514, 515, 516, 517, 518, 519, 520))));
        trips.put(302, new FakeTrip("La Jolla Trip", 302, "jhalpert", new PlaceLocationData("ChIJzQ7MT3bQ24ARlDAdXPQe5fw"), "Fun Trip in La Jolla.",
                403, new ActivityData[]{activities[2], activities[3], activities[4]},
                (float) 4.2, new ArrayList<Integer>(Arrays.asList(506, 507, 508, 509, 510))));
        trips.put(303, new FakeTrip("Balboa Park", 303, "mscott", new PlaceLocationData("ChIJA8tw-pZU2YARxPYVsDwL8-0"), "A day at Balboa Park.",
                402, new ActivityData[]{activities[2], activities[3], activities[4]},
                (float) 4.5, new ArrayList<Integer>(Arrays.asList(511, 512, 513, 514, 515))));
        trips.put(304, new FakeTrip("Zoo Trip", 304, "mscott", new PlaceLocationData("ChIJyYB_SZVU2YARR-I1Jjf08F0"), "Things to do around the Zoo",
                404, new ActivityData[]{activities[2], activities[3], activities[4]},
                (float) 2.3, new ArrayList<Integer>(Arrays.asList(516, 517, 518, 519, 520))));
        nextTrip = 300 + trips.size() + 1;

        // Users
        users.put("mscott", new FakeUser("mscott", 405, "Michael", "Scott", "Scranton, PA", "password123",
                new ArrayList<>(Arrays.asList(301, 303, 304)), new ArrayList<>(Arrays.asList(501, 506, 507, 508, 509, 510)), new ArrayList<>(Arrays.asList(302, 301, 304))));
        users.put("jhalpert", new FakeUser("jhalpert", 406, "Jim", "Halpert", "Scranton, PA", "password123",
                new ArrayList<>(Arrays.asList(302)), new ArrayList<Integer>(), new ArrayList<>(Arrays.asList(304, 301, 303))));

        // Reviews
        reviews.putAll(new HashMap<Integer, FakeReview>(){{
            put(501,  new FakeReview(501, "mscott", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 501", 301));
            put(502,  new FakeReview(502, "jhalpert", (float) 1.0, new Date(), "Totally awesome experience but I lost my iPhone 502", 301));
            put(503,  new FakeReview(503, "jhalpert", (float) 5.0, new Date(), "Totally awesome experience but I lost my iPhone 503", 301));
            put(504,  new FakeReview(504, "jhalpert", (float) 3.0, new Date(), "Totally awesome experience but I lost my iPhone 504", 301));
            put(505,  new FakeReview(505, "jhalpert", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 505", 301));
            put(506,  new FakeReview(506, "mscott", (float) 1.0, new Date(), "Totally awesome experience but I lost my iPhone 506", 302));
            put(507,  new FakeReview(507, "mscott", (float) 2.0, new Date(), "Totally awesome experience but I lost my iPhone 507", 302));
            put(508,  new FakeReview(508, "mscott", (float) 3.0, new Date(), "Totally awesome experience but I lost my iPhone 508", 302));
            put(509,  new FakeReview(509, "mscott", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 509", 302));
            put(510,  new FakeReview(510, "mscott", (float) 5.0, new Date(), "Totally awesome experience but I lost my iPhone 510", 302));
            put(511,  new FakeReview(511, "mscott", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 511", 303));
            put(512,  new FakeReview(512, "jhalpert", (float) 1.0, new Date(), "Totally awesome experience but I lost my iPhone 512", 303));
            put(513,  new FakeReview(513, "jhalpert", (float) 5.0, new Date(), "Totally awesome experience but I lost my iPhone 513", 303));
            put(514,  new FakeReview(514, "jhalpert", (float) 3.0, new Date(), "Totally awesome experience but I lost my iPhone 514", 303));
            put(515,  new FakeReview(515, "jhalpert", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 515", 303));
            put(516,  new FakeReview(516, "mscott", (float) 1.0, new Date(), "Totally awesome experience but I lost my iPhone 516", 304));
            put(517,  new FakeReview(517, "mscott", (float) 2.0, new Date(), "Totally awesome experience but I lost my iPhone 517", 304));
            put(518,  new FakeReview(518, "mscott", (float) 3.0, new Date(), "Totally awesome experience but I lost my iPhone 518", 304));
            put(519,  new FakeReview(519, "mscott", (float) 4.0, new Date(), "Totally awesome experience but I lost my iPhone 519", 304));
            put(520,  new FakeReview(520, "mscott", (float) 5.0, new Date(), "Totally awesome experience but I lost my iPhone 520", 304));
        }});
        nextReview = 500 + reviews.size() + 1;



        // Recommendations
        recommendations.put("mscott", Arrays.asList(302, 304, 303));
        recommendations.put("jhalpert", Arrays.asList(301, 303, 304));

        // News Feeds
        newsFeeds.put("mscott", Arrays.<FakeFeedEvent>asList(
                new FakeReviewEvent("jhalpert", new Date(111, Calendar.SEPTEMBER, 5), 503),
                new FakeCreatedTripEvent("mscott", new Date(120, Calendar.MAY, 30), 302),
                new FakeAddFavoriteEvent("jhalpert", new Date(120, Calendar.APRIL, 28), 303)
        ));
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
            imageFiles.add(imageFactory.getImage(id));
        }
        return imageFiles;
    }

    /** Adds an image to the database
     * @param image Image to add to the database
     */
    public int addImage(BitmapData image) throws ServerError {
        if(image.id != -1){
            return image.id; // Image already has an id so it is already in the database
        }
        try {
            byte[] inputBytes = new byte[image.dataStream.available()];
            image.dataStream.read(inputBytes);
            images.put(nextImage, new FakeImage(nextImage, inputBytes));
            nextImage++;
            return nextImage -1;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerError(e);
        }
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
            imageFiles.add(imageFactory.getProfilePicture(id));
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

    public void createTrip(TripData trip) throws DatabaseError, ServerError {
        if(trips.get(nextTrip) != null){
            throw new DatabaseError(String.format("Trip with id %s already exists!", nextTrip));
        }
        if (trip.mainImage.id == -1){
            // Handle adding image
            int imgId = addImage(trip.mainImage);
            trips.put(nextTrip, new FakeTrip(trip.title, nextTrip, trip.creator.userId, trip.location, trip.description, imgId, trip.activities, 0.0f, new ArrayList<Integer>()));
        } else {
            trips.put(nextTrip, new FakeTrip(trip.title, nextTrip, trip.creator.userId, trip.location, trip.description, trip.mainImage.id, trip.activities, 0.0f, new ArrayList<Integer>()));
        }

        users.get(trip.creator.userId).createdTrips.add(nextTrip);
        nextTrip++;
    }

    public void createReview(ReviewData review){
        reviews.put(nextReview, new FakeReview(nextReview, review.reviewer.userId, review.reviewRating, review.reviewDate, review.reviewComment, review.tripId));
        users.get(review.reviewer.userId).reviews.add(nextReview);
        trips.get(review.tripId).reviews.add(nextReview);
        recomputeReviewAverage(review.tripId);
        nextReview++;
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
            return user.toUserData(imageFactory);
        }
        return null;
    }

    public List<FeedEventData> getNewsFeed(String userId){
        List<FeedEventData> feed = new ArrayList<>();
        for(FakeFeedEvent event: newsFeeds.get(userId)){
            feed.add(event.toFeedEventData(userFactory, reviewFactory, tripFactory));
        }
        return feed;
    }

    public List<TripData> searchTrips(String query){
        List<TripData> results = new ArrayList<>();
        for(int id: Arrays.asList(302, 301, 303)){
            results.add(trips.get(id).toTripData(imageFactory, userFactory));
        }
        return results;
    }

    public class ImageFactory {

        public BitmapData getImage(Integer imageId){
            FakeImage image = images.get(imageId);
            if(image == null){
                return null;
            }
            return image.toData();
        }

        public ProfilePictureData getProfilePicture(String userId){
            Integer imageId = users.get(userId).profileImageId;
            FakeImage image = images.get(imageId);
            if(image == null){
                return null;
            }
            return image.toProfilePictureData(userId);
        }
    }

    public class UserFactory {
        public UserData getUser(String userId){
            return users.get(userId).toUserData(imageFactory);
        }

        public CreatorData getCreator(String userId){
            return users.get(userId).toCreatorData(imageFactory);
        }

        public ProfileData getProfile(String userId){
            return users.get(userId).toProfile(imageFactory);
        }
        
        public ReviewerData getReviewer(String userId){ return users.get(userId).toReviewerData(imageFactory); }
    }

    public class TripFactory {
        public TripData getTrip(Integer tripId){
            return trips.get(tripId).toTripData(imageFactory, userFactory);
        }
    }

    public class ReviewFactory {
        public ReviewData getReview(Integer reviewId){
            return reviews.get(reviewId).toReviewData(userFactory);
        }
    }

    private void recomputeReviewAverage(int tripId){
        FakeTrip trip = trips.get(tripId);
        final List<FakeReview> tripReviews = new ArrayList<>();
        float reviewsSum = 0.0f;

        for(int id: trip.reviews){
            reviewsSum += reviews.get(id).reviewRating;
        }
        trip.reviewsAverage = reviewsSum / trip.reviews.size();
    }
}
