package com.tripdazzle.daycation.ui.triplist;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.BitmapImage;
import com.tripdazzle.daycation.models.Trip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripListViewModel extends ViewModel {
    private MutableLiveData<List<Trip>> trips = new MutableLiveData<>();
    private MutableLiveData<Map<Integer, Bitmap>> images = new MutableLiveData<>();

    public LiveData<List<Trip>> getTrips() {
        if (trips.getValue() ==  null){
            trips.setValue(new ArrayList<Trip>());
        }
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips.setValue(trips);
    }

    public LiveData<Map<Integer, Bitmap>> getImages(){
        if (images.getValue() ==  null){
            images.setValue(new HashMap<Integer, Bitmap>());
        }
        return images;
    }

    public void setImages(Map<Integer, Bitmap> images) {
        this.images.setValue(images);
    }

    public void setImages(List<BitmapImage> images) {
        Map<Integer, Bitmap> imagesMap = new HashMap<>();
        for(BitmapImage img: images){
            imagesMap.put(img.id, img.image);
        }
        this.images.setValue(imagesMap);
    }
}
