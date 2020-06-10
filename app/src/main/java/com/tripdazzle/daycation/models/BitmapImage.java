package com.tripdazzle.daycation.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.tripdazzle.server.datamodels.BitmapData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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

    public BitmapData toData(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return new BitmapData(id, new ByteArrayInputStream(outputStream.toByteArray()));
    }

    // Used when you want to update the imageId
    public BitmapData toData(int imageId){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return new BitmapData(imageId, new ByteArrayInputStream(outputStream.toByteArray()));
    }
}
