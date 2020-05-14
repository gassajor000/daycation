package com.tripdazzle.server;

import java.io.File;
import java.util.HashMap;

class FakeDatabase {
    private HashMap<Integer, String> fileNames;

    FakeDatabase() {
        this.fileNames = new HashMap<>();
        fileNames.put(123, "missionBay.png");
    }

    /** Retrieve a bitmap image by ID
     * @param imgId id of the image to fetch
     * @return Image file of the image with the matching id
     */
    File getImage(int imgId){
        String fileName = fileNames.get(imgId);
        return new File(fileName);
    }

}
