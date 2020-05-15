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
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.server.ServerError;

public class TripInfoFragment extends Fragment implements DataModel.TripsSubscriber {
    private TripInfoViewModel mViewModel;
    private DataModel model = new DataModel();

    public static TripInfoFragment newInstance() {
        return new TripInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(TripInfoViewModel.class);

        FragmentTripInfoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip_info, container, false);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();

        model.initialize(getContext());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO mViewModel.loadTrip(defaultTrip);
        model.getTripById(333, this);
    }

    @Override
    public void onSuccess(String message) {}

    @Override
    public void onError(String message) {}

    @Override
    public void onSync(String message, DataModel model) {}

    @Override
    public void onGetTripById(Trip trip) {
        mViewModel.setTrip(trip);

        // get main image
        try {
            Bitmap mainImage = model.getImageById(trip.mainImageId);
            ImageView mainImageView = (ImageView) this.getView().findViewById(R.id.tripInfoMainImageView);
            mainImageView.setImageBitmap(mainImage);
        } catch (ServerError serverError) {
            serverError.printStackTrace();
        }
    }
}
