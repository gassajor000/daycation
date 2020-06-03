package com.tripdazzle.daycation.ui.createtrip;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.Activity;
import com.tripdazzle.daycation.models.BitmapImage;
import com.tripdazzle.daycation.models.Creator;
import com.tripdazzle.daycation.models.Trip;

import java.util.ArrayList;

public class CreateTripViewModel extends ViewModel {
    private TripModel trip = new TripModel();

    public Trip makeTrip(){
        return trip.makeTrip();
    }

    public TripModel getTrip() {
        return trip;
    }

    public class TripModel extends BaseObservable{
        @Bindable
        private String title = "";
        private MutableLiveData<Creator> creator = new MutableLiveData<>();
        @Bindable
        private String description = "";
        @Bindable
        private String location = "";
        private MutableLiveData<BitmapImage> mainImage = new MutableLiveData<>();
        private MutableLiveData<Activity> activities = new MutableLiveData<>();

        public MutableLiveData<Creator> getCreator() {
            return creator;
        }
        public void setCreator(MutableLiveData<Creator> creator) {
            this.creator = creator;
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

        public MutableLiveData<Activity> getActivities() {
            return activities;
        }
        public void setActivities(MutableLiveData<Activity> activities) {
            this.activities = activities;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
            notifyPropertyChanged(BR.title);
        }

        public Trip makeTrip(){
            Activity[] activities = {};
            return new Trip(title, -1, description, location, activities, 0f, new ArrayList<Integer>(), creator.getValue(), mainImage.getValue());
        }
    }
}
