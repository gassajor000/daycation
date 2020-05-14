package com.tripdazzle.server;

import java.io.File;
import java.util.HashMap;

class FakeDatabase {
    private HashMap<Integer, String> fileNames;

    private String dbFilePath;

    FakeDatabase(String dbFilePath) {
        this.dbFilePath = dbFilePath;
        this.fileNames = new HashMap<>();
        fileNames.put(444, "mission_bay.png");
    }

    public void setDbFilePath(String dbFilePath) {
        this.dbFilePath = dbFilePath;
    }


    /** Retrieve a bitmap image by ID
     * @param imgId id of the image to fetch
     * @return Image file of the image with the matching id
     */
    File getImage(int imgId){
        String fileName = fileNames.get(imgId);
        return new File(dbFilePath + "/" + fileName);
    }

}
