package com.tripdazzle.daycation.ui.tripinfo;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.databinding.FragmentTripInfoBinding;
import com.tripdazzle.daycation.models.Activity;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.server.datamodels.ActivityType;

public class TripInfoFragment extends Fragment {
    private Activity[] activities = {
            new Activity(ActivityType.HIKING, "Rose Canyon", "Hike Rose Canyon"),
            new Activity(ActivityType.ICE_CREAM, "Shake Shack", "Get Ice cream at Shake Shack"),
            new Activity(ActivityType.BEACH, "Mission Beach", "Go Swimming at Mission Beach")
    };
    Trip defaultTrip = new Trip("SD Vacay", 123, "gassajor",
            "Fun Trip around the San Diego Bay.",   1233, activities);

    private TripInfoViewModel mViewModel;

    public static TripInfoFragment newInstance() {
        return new TripInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentTripInfoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip_info, container, false);
        binding.setTrip(defaultTrip);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TripInfoViewModel.class);
        // TODO mViewModel.loadTrip(defaultTrip);
    }

}
