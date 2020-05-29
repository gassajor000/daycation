package com.tripdazzle.daycation.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.tripdazzle.server.datamodels.BitmapData;

/* Wrapper class for bitmap images*/
public class BitmapImage {
    public final Bitmap image;
    public final Integer id;

    public BitmapImage(Bitmap image, Integer id) {
        this.image = image;
        this.id = id;
    }

    public BitmapImage(BitmapData data){
        this.id = data.id;
        this.image = BitmapFactory.decodeStream(data.dataStream);
    }

    public Drawable getDrawable(Context context) {
        return new BitmapDrawable(context.getResources(), image);
    }
}
