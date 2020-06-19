package com.tripdazzle.daycation.ui.locationselector;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.databinding.FragmentLocationSelectorBinding;
import com.tripdazzle.daycation.models.location.Location;
import com.tripdazzle.daycation.models.location.NullLocation;
import com.tripdazzle.daycation.models.location.PlaceLocation;

import java.util.Arrays;
import java.util.List;

public class LocationSelectorFragment extends Fragment {

    private LocationSelectorViewModel mViewModel;
    private List<Place.Field> mFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
    private static final int AUTOCOMPLETE_REQUEST_CODE = 2;

    public static LocationSelectorFragment newInstance() {
        return new LocationSelectorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(LocationSelectorViewModel.class);

        FragmentLocationSelectorBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location_selector, container, false);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();

        view.findViewById(R.id.locationSelectorLocationName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLocation();
            }
        });

        return view;
    }

    private void selectLocation() {
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, mFields)
                .build(getContext());
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public Location getLocation(){
        if(mViewModel.getLocation().getValue() != null){
            return mViewModel.getLocation().getValue();
        }

        return new NullLocation();
    }

    public boolean isSelected(){
        return mViewModel.getLocation().getValue() != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AUTOCOMPLETE_REQUEST_CODE){
            if (resultCode == android.app.Activity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                mViewModel.setLocation(new PlaceLocation(place));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
