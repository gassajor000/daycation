package com.tripdazzle.daycation.ui.profile;

import android.content.Context;
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
import com.tripdazzle.daycation.databinding.FragmentProfileBinding;
import com.tripdazzle.daycation.models.BitmapImage;
import com.tripdazzle.daycation.models.Profile;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.ui.triplist.TripListViewModel;

import java.util.List;

public class ProfileFragment extends Fragment implements DataModel.ProfilesSubscriber, DataModel.ImagesSubscriber, DataModel.TripsSubscriber {

    private ProfileViewModel mViewModel;
    private TripListViewModel mCreatedTripsModel;
    private DataModel mModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        FragmentProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();

        mCreatedTripsModel = ViewModelProviders.of(getChildFragmentManager().findFragmentById(R.id.profileCreatedTripsList)).get(TripListViewModel.class);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userId = ProfileFragmentArgs.fromBundle(getArguments()).getProfileId();
        mModel.getProfileById(userId, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataModel.DataManager) {
            mModel = ((DataModel.DataManager) context).getModel();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DataModel.DataManager");
        }
    }

    @Override
    public void onSuccess(String message) {}

    @Override
    public void onError(String message) {}

    @Override
    public void onGetProfileById(Profile profile) {
        mViewModel.setProfile(profile);
        mModel.getTripsByIds(profile.createdTrips, this);
        mModel.getImageById(profile.profileImageId, this);
    }

    @Override
    public void onGetImagesById(List<BitmapImage> images) {
        Profile profile = mViewModel.getProfile().getValue();
        if (profile == null){ return; }

        for(BitmapImage img: images){
            if(img.id.equals(profile.profileImageId)){
                ImageView profileImage = (ImageView) this.getView().findViewById(R.id.profileImage);
                profileImage.setImageBitmap(img.image);
            }
        }
    }

    @Override
    public void onGetTripsById(List<Trip> trips) {
        mCreatedTripsModel.setTrips(trips);
    }
}
