package com.tripdazzle.server;

import com.tripdazzle.server.datamodels.ProfileData;
import com.tripdazzle.server.datamodels.ReviewData;
import com.tripdazzle.server.datamodels.TripData;
import com.tripdazzle.server.datamodels.UserData;
import com.tripdazzle.server.fakedb.FakeDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProxyServer {
    private FakeDatabase db = new FakeDatabase("path/To/File");

    public void setDbLocation(String loc){
        db.setDbFilePath(loc);
    }

    /** retrieve an image from the server
     * @param imageIds id of the image to fetch
     */
    public List<InputStream> getImagesById(List<Integer> imageIds) throws ServerError {
        List<File> imgFiles = db.getImagesById(imageIds);
        List<InputStream> imageData = new ArrayList<>();
        try {
            for(File f: imgFiles){
                if (f == null){
                    imageData.add(null);
                } else {
                    imageData.add(new FileInputStream(f));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ServerError(e);
        }
        return imageData;
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
}
