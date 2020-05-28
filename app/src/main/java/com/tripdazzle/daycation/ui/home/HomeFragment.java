package com.tripdazzle.daycation.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.ui.triplist.TripListViewModel;

import java.util.List;

public class HomeFragment extends Fragment  implements DataModel.TripsSubscriber {

    private HomeViewModel homeViewModel;
    private DataModel mModel;
    private TripListViewModel mRecommendedTripsModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.splash_text_logo);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mRecommendedTripsModel = ViewModelProviders.of(getChildFragmentManager().findFragmentById(R.id.homeRecommendedTrips)).get(TripListViewModel.class);
        mModel.getRecommendedTripsForUser(mModel.getCurrentUser().userId, new DataModel.OnRecommendedTripsListener() {
            @Override
            public void onRecommendedTrips(List<Trip> trips) {
                mRecommendedTripsModel.setTrips(trips);
            }
        });


        FloatingActionButton fab = root.findViewById(R.id.homeAddTripFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return root;
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
    public void onSuccess(String message) { }

    @Override
    public void onError(String message) { }

    @Override
    public void onGetTripsById(List<Trip> trips) {
//        mRecommendedTripsModel.setTrips(trips);
    }

    @Override
    public void onGetFavoritesByUserId(List<Trip> favorites) { }
}
