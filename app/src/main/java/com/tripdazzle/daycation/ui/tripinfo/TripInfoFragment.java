package com.tripdazzle.daycation.ui.tripinfo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.databinding.FragmentTripInfoBinding;
import com.tripdazzle.daycation.models.Activity;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.server.ServerError;
import com.tripdazzle.server.datamodels.ActivityType;

public class TripInfoFragment extends Fragment {
    private Activity[] activities = {
            new Activity(ActivityType.HIKING, "Rose Canyon", "Hike Rose Canyon"),
            new Activity(ActivityType.ICE_CREAM, "Shake Shack", "Get Ice cream at Shake Shack"),
            new Activity(ActivityType.BEACH, "Mission Beach", "Go Swimming at Mission Beach")
    };
    Trip defaultTrip = new Trip("SD Vacay", 123, "gassajor",
            "Fun Trip around the San Diego Bay.",   444, activities);

    private TripInfoViewModel mViewModel;
    private DataModel model = new DataModel();

    public static TripInfoFragment newInstance() {
        return new TripInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentTripInfoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip_info, container, false);
        binding.setTrip(defaultTrip);
        View view = binding.getRoot();

        // get main image
        model.initialize(getContext());
        try {
            Bitmap mainImage = model.getImage(defaultTrip.mainImageId);
            ImageView mainImageView = (ImageView) view.findViewById(R.id.tripInfoMainImageView);
            mainImageView.setImageBitmap(mainImage);
        } catch (ServerError serverError) {
            serverError.printStackTrace();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TripInfoViewModel.class);
        // TODO mViewModel.loadTrip(defaultTrip);
    }

}
