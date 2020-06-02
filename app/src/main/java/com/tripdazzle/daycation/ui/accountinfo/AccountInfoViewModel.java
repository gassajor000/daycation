package com.tripdazzle.daycation.ui.accountinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.User;

public class AccountInfoViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }
}
