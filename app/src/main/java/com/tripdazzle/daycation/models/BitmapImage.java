package com.tripdazzle.daycation.models;

import android.graphics.Bitmap;

/* Wrapper class for bitmap images*/
public class BitmapImage {
    public final Bitmap image;
    public final Integer id;

    public BitmapImage(Bitmap image, Integer id) {
        this.image = image;
        this.id = id;
    }
}
