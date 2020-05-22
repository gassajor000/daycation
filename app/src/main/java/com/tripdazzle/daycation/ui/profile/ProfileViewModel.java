package com.tripdazzle.daycation.ui.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.Profile;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<Profile> profile = new MutableLiveData<>();

    public Profile getProfile() {
        return profile.getValue();
    }

    public void setProfile(Profile profile) {
        this.profile.setValue(profile);
    }
}
