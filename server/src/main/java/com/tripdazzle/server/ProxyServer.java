package com.tripdazzle.server;

import com.tripdazzle.server.datamodels.BitmapData;
import com.tripdazzle.server.datamodels.ProfileData;
import com.tripdazzle.server.datamodels.ProfilePictureData;
import com.tripdazzle.server.datamodels.ReviewData;
import com.tripdazzle.server.datamodels.TripData;
import com.tripdazzle.server.datamodels.UserData;
import com.tripdazzle.server.datamodels.feed.FeedEventData;
import com.tripdazzle.server.fakedb.FakeDatabase;

import java.util.List;

public class ProxyServer {
    private FakeDatabase db = new FakeDatabase("path/To/File");

    public void setDbLocation(String loc){
        db.setDbFilePath(loc);
    }

    /** retrieve an image from the server
     * @param imageIds id of the image to fetch
     */
    public List<BitmapData> getImagesById(List<Integer> imageIds) throws ServerError {
        return db.getImagesById(imageIds);
    }
    /*
    * Add an image to the database
    * */
    public int addImage(BitmapData image) throws ServerError{  return db.addImage(image); }

    /** retrieve profile pictures for users
     * @param userIds id of the image to fetch
     */
    public List<ProfilePictureData> getProfilePicturesByUserIds(List<String> userIds) throws ServerError {
        return db.getProfilePicturesByUserIds(userIds);
    }

    /** retrieve a trip from the server
     * @param tripIds ids of the trip to fetch
     */
    public List<TripData> getTripsById(List<Integer> tripIds){
        return db.getTripsById(tripIds);
    }

    /** retrieve a trip from the server
     * @param reviewIds ids of the reviews to fetch
     */
    public List<ReviewData> getReviewsById(List<Integer> reviewIds) throws ServerError{
        return db.getReviewsById(reviewIds);
    }

    /** retrieve a trip from the server
     * @param userId username of the profile to fetch
     */
    public ProfileData getProfileById(String userId) throws ServerError{
        return db.getProfileById(userId);
    }

    /** retrieve the favorite trips for a user
     * @param userId username of the profile to fetch favorites for
     */
    public List<TripData> getFavoritesByUserId(String userId) throws ServerError{
        return db.getFavoritesByUserId(userId);
    }

    /** retrieve the recommended trips for a user
     * @param userId username of the profile to fetch recommended for
     */
    public List<TripData> getRecommendedTripsByUserId(String userId) throws ServerError{
        return db.getRecommendedTripsForUser(userId);
    }

    /** retrieve the recommended trips for a user
     * @param trip data for the trip to create
     */
    public void createTrip(TripData trip) throws ServerError{
        db.createTrip(trip);
    }

    /** toggle whether or not a trip is in the user's favorites
     * @param userId username of the user to toggle favorite
     * @param tripId trip to toggle favorite
     * @param addFavorite add to favorites if true, remove if false
     */
    public void toggleFavorite(String userId, Integer tripId, Boolean addFavorite) throws ServerError{
        db.toggleFavorite(userId, tripId, addFavorite);
    }

    /** attempts to log in with the supplied credentials
     * @param userId username to log in as
     * @param password password for selected user
     */
    public UserData login(String userId, String password) throws ServerError{
        return db.login(userId, password);
    }

    /** retrieves the news feed for a use
     * @param userId user to fetch feed for
     */
    public List<FeedEventData> getNewsFeed(String userId) throws ServerError{
        return db.getNewsFeed(userId);
    }
}
