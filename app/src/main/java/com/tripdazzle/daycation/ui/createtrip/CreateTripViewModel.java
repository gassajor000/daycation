package com.tripdazzle.daycation.ui.createtrip;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.BitmapImage;

public class CreateTripViewModel extends ViewModel {
    private String title = "";
    private String description = "";
    private String location = "";
    private MutableLiveData<BitmapImage> mainImage = new MutableLiveData<>();


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public MutableLiveData<BitmapImage> getMainImage() {
        return mainImage;
    }
    public void setMainImage(MutableLiveData<BitmapImage> mainImage) {
        this.mainImage = mainImage;
    }
}
