package com.tripdazzle.server;

import com.tripdazzle.server.datamodels.ProfileData;
import com.tripdazzle.server.datamodels.ReviewData;
import com.tripdazzle.server.datamodels.TripData;
import com.tripdazzle.server.fakedb.FakeDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ProxyServer {
    private FakeDatabase db = new FakeDatabase("path/To/File");

    public void setDbLocation(String loc){
        db.setDbFilePath(loc);
    }

    /** retrieve an image from the server
     * @param imageId id of the image to fetch
     */
    public InputStream getImageById(int imageId) throws ServerError {
        File imgFile = db.getImageById(imageId);
        try {
            return new FileInputStream(imgFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ServerError(e);
        }
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
}
