package com.tripdazzle.server.datamodels;

import java.io.InputStream;

public class BitmapData {
    public final InputStream dataStream;
    public final Integer id;

    public BitmapData(Integer id, InputStream dataStream) {
        this.dataStream = dataStream;
        this.id = id;
    }
}
