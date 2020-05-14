package com.tripdazzle.server;

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
    public InputStream getImage(int imageId) throws ServerError {
        File imgFile = db.getImage(imageId);
        try {
            return new FileInputStream(imgFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ServerError(e);
        }
    }
}
