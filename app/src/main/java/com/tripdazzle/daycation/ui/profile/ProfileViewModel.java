package com.tripdazzle.daycation.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.Profile;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<Profile> profile = new MutableLiveData<>();

    public LiveData<Profile> getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile.setValue(profile);
    }
}
