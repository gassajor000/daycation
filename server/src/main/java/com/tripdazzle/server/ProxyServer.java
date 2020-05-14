package com.tripdazzle.server;

import java.io.File;

public class ProxyServer {
    private FakeDatabase db = new FakeDatabase();

    /** retrieve an image from the server
     * @param imageId id of the image to fetch
     */
    public File getImage(int imageId){
        return db.getImage(imageId);
    }
}
