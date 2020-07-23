package com.tripdazzle.daycation.ui.tripinfo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.ToolbarManager;
import com.tripdazzle.daycation.databinding.FragmentTripInfoBinding;
import com.tripdazzle.daycation.models.Activity;
import com.tripdazzle.daycation.models.Review;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.ui.tripinfo.TripInfoFragmentDirections.ActionNavTripInfoToProfile;

import java.util.ArrayList;
import java.util.List;

public class TripInfoFragment extends Fragment implements DataModel.TripsSubscriber, DataModel.ReviewsSubscriber, ReviewsListAdapter.OnLoadMoreListener, OnMapReadyCallback {
    private TripInfoViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private ReviewsListAdapter mReviewsAdapter;

    private final int reviewsBatchSize = 5;     // Number of reviews to fetch at a time
    private DataModel mModel;
    private MapView mapView;
    private ToolbarManager toolbarManager;
    private CollapsingToolbarLayout cTooolbar;
    private View profilePicView;
    private MenuItem favoritesButton;
    private NavController navController;

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

        view.findViewById(R.id.tripInfoCreatorProfilePic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProfile();
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.tripIonfoReviewsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mReviewsAdapter = new ReviewsListAdapter(new ArrayList<Review>(), mRecyclerView, this);
        mRecyclerView.setAdapter(mReviewsAdapter);

        // Init Map
        mapView = view.findViewById(R.id.tripInfoActivitiesMap);
        mapView.onCreate(savedInstanceState);

        // Setup Collapsing Toolbar
        cTooolbar = view.findViewById(R.id.tripInfoCollapsingToolbar);
        Toolbar toolbar = view.findViewById(R.id.tripInfoToolbar);
        toolbarManager.initializeToolbar(toolbar);

        profilePicView = view.findViewById(R.id.tripInfoCreatorProfilePic);
        AppBarLayout mAppBarLayout = view.findViewById(R.id.tripInfoAppBar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            private State state;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {  // Expanded all the way
                    if (state != State.EXPANDED) {
                        // Show profile pic
                        profilePicView.setVisibility(View.VISIBLE);
                    }
                    state = State.EXPANDED;
                } else if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    if (state != State.COLLAPSED) {
                        // Hide profile pic
                        profilePicView.setVisibility(View.INVISIBLE);
                    }
                    state = State.COLLAPSED;
                } else {
                    // do nothing
                    state = State.IDLE;
                }
            }
        });

         // Setup menu options
        setHasOptionsMenu(true);

        return view;
    }

    private void toggleFavorite(){
        boolean addToFavorites = !mViewModel.getInFavorites().getValue();
        Log.i("Toggle favorite", (addToFavorites ? "Add" : "Remove") + " Favorite");
        mModel.toggleFavorite(mModel.getCurrentUser().userId, mViewModel.getTrip().getValue().id, addToFavorites, this);
        mViewModel.getInFavorites().setValue(addToFavorites);
    }

    private void setFavoritesButtonIcon(boolean inFavorites){
        favoritesButton.setIcon(inFavorites ? R.drawable.ic_star_yellow_24dp : R.drawable.ic_star_border_yellow_24dp);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int tripId = TripInfoFragmentArgs.fromBundle(getArguments()).getTripId();
        mModel.getTripById(tripId, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataModel.DataManager && context instanceof ToolbarManager) {
            mModel = ((DataModel.DataManager) context).getModel();
            toolbarManager = (ToolbarManager) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DataModel.DataManager and ToolbarManager");
        }
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tripinfo_fragment, menu);
        favoritesButton = menu.findItem(R.id.option_toggle_favorite);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection;,
        if (item.getItemId() == R.id.option_toggle_favorite) {
            toggleFavorite();
            setFavoritesButtonIcon(mViewModel.getInFavorites().getValue());
            return true;
        } else if (item.getItemId() == R.id.option_add_review){
            navigateToAddReview();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    /*load more reviews from the server*/
    @Override
    public void onLoadMore() {
        int numLoaded =  mViewModel.numReviewsLoaded();

        mViewModel.setLoadingReviews(true);
//        mRecyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//                mReviewsAdapter.notifyItemInserted(mViewModel.numReviewsLoaded() - 1);
//            }
//        });
        mReviewsAdapter.setLoading(true);

        Trip trip = mViewModel.getTrip().getValue();
        if(trip.reviews.size() > numLoaded + reviewsBatchSize){   // Load 10 more
            mModel.getReviewsByIds(trip.reviews.subList(numLoaded, numLoaded + reviewsBatchSize), this);
        } else { // load all remaining
            mModel.getReviewsByIds(trip.reviews.subList(numLoaded, trip.reviews.size()), this);
        }
    }

    private void navigateToProfile(){
        ActionNavTripInfoToProfile action = TripInfoFragmentDirections.actionNavTripInfoToProfile();
        action.setProfileId(mViewModel.getTrip().getValue().creator.userId);
        navController.navigate(action);
    }

    private void navigateToAddReview(){
        Trip trip = mViewModel.getTrip().getValue();
        TripInfoFragmentDirections.ActionNavTripInfoToAddReview action = TripInfoFragmentDirections.actionNavTripInfoToAddReview(trip.id, trip.title);
        navController.navigate(action);
    }

    @Override
    public void onGetReviewsByIds(List<Review> reviews) {
        mViewModel.setLoadingReviews(false);
        Integer origSize = mViewModel.numReviewsLoaded();
//        mReviewsAdapter.notifyItemRemoved(origSize);

        mViewModel.addReviews(reviews);
        mReviewsAdapter.notifyItemRangeInserted(origSize + 1, reviews.size());

        mReviewsAdapter.setLoading(false);

        if(reviews.size() < reviewsBatchSize){      // Set all loaded so no new loads are triggered
            mReviewsAdapter.setAllReviewsLoaded(true);
        }
    }

    @Override
    public void onSuccess(String message) {}

    @Override
    public void onError(String message) {}

    @Override
    public void onGetTripsById(List<Trip> trips) {
        if (trips.size() != 1){
            throw new RuntimeException("Unexpected number of trips returned" + trips.toString());
        }

        Trip trip = trips.get(0);
        mViewModel.setTrip(trip, mModel.inCurrentUsersFavorites(trip.id));
        cTooolbar.setTitle(trip.title);
        setFavoritesButtonIcon(mViewModel.getInFavorites().getValue());

        // setup the map
        mapView.getMapAsync(this);

        // get reviews
        if( mViewModel.getReviews().size() != 0){
            mViewModel.clearReviews();
        }
        mReviewsAdapter = new ReviewsListAdapter(mViewModel.getReviews(), mRecyclerView, this);
        mRecyclerView.setAdapter(mReviewsAdapter);
        onLoadMore();
    }

    @Override
    public void onGetFavoritesByUserId(List<Trip> favorites) { }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        // Add activities to map
        Activity[] activities = mViewModel.getTrip().getValue().activities;
        LatLngBounds.Builder cameraBounds = LatLngBounds.builder();

        for(int i=0; i < 3; i++){
            MarkerOptions marker = activities[i].getMarker();
            cameraBounds.include(marker.getPosition());
            googleMap.addMarker(marker);
        }

        // Center camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(cameraBounds.build(), 100));
    }


    /* Adds markers to the map. Trip must be loaded and map must be ready*/
    private void setupMap(){

    }

    private enum State {
        EXPANDED,
        COLLAPSED,
        COLLAPSING,
        IDLE
    }

}
