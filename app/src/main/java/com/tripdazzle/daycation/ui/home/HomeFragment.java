package com.tripdazzle.daycation.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.ui.feed.FeedViewModel;
import com.tripdazzle.daycation.ui.triplist.TripListViewModel;

import java.util.List;

public class HomeFragment extends Fragment  implements DataModel.TripsSubscriber {

    private HomeViewModel homeViewModel;
    private DataModel mModel;
    private TripListViewModel mRecommendedTripsModel;
    private FeedViewModel mNewsFeedViewModel;

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
        mNewsFeedViewModel = ViewModelProviders.of(getChildFragmentManager().findFragmentById(R.id.homeNewsFeed)).get(FeedViewModel.class);
        mModel.getRecommendedTripsForUser(mModel.getCurrentUser().userId, new DataModel.OnRecommendedTripsListener() {
            @Override
            public void onRecommendedTrips(List<Trip> trips) {
                mRecommendedTripsModel.setTrips(trips);
            }
        });

        mNewsFeedViewModel.setEvents(mModel.getNewsFeed("mscott"));


        FloatingActionButton fab = root.findViewById(R.id.homeAddTripFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToCreateTrip();
            }
        });

        setupSearchView((SearchView) root.findViewById(R.id.homeSearchBar));

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

    private void setupSearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                navigateToSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void navigateToCreateTrip(){
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        NavDirections action = HomeFragmentDirections.actionNavHomeToCreateTrip();
        navController.navigate(action);
    }

    private void navigateToSearch(String query){
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        HomeFragmentDirections.ActionNavHomeToSearch action = HomeFragmentDirections.actionNavHomeToSearch();
        action.setSearchQuery(query);
        navController.navigate(action);
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
