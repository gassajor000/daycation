package com.tripdazzle.daycation.ui.feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripdazzle.daycation.models.feed.FeedEvent;

import java.util.ArrayList;
import java.util.List;

public class FeedViewModel extends ViewModel {
    private MutableLiveData<List<FeedEvent>> events = new MutableLiveData<>();

    public LiveData<List<FeedEvent>> getEvents() {
        if (events.getValue() ==  null){
            events.setValue(new ArrayList<FeedEvent>());
        }
        return events;
    }

    public void setEvents(List<FeedEvent> events) {
        this.events.setValue(events);
    }

}
