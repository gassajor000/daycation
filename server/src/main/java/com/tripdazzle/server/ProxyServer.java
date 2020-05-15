package com.tripdazzle.server;

import com.tripdazzle.server.datamodels.TripData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
     * @param tripId id of the trip to fetch
     */
    public TripData getTripById(int tripId){
        return db.getTripById(tripId);
    }
}
