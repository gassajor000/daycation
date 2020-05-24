package com.tripdazzle.daycation.ui.tripinfo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.databinding.FragmentTripInfoBinding;
import com.tripdazzle.daycation.models.BitmapImage;
import com.tripdazzle.daycation.models.Review;
import com.tripdazzle.daycation.models.Trip;

import java.util.List;

public class TripInfoFragment extends Fragment implements DataModel.TripsSubscriber, DataModel.ImagesSubscriber, DataModel.ReviewsSubscriber, ReviewsListAdapter.OnLoadMoreListener {
    private TripInfoViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private ReviewsListAdapter mReviewsAdapter;

    private final int reviewsBatchSize = 5;     // Number of reviews to fetch at a time
    private DataModel mModel;

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

        view.findViewById(R.id.tripInfoToggleFavorite).setOnClickListener(new ToggleFavoriteListener());

        mViewModel.getInFavorites().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean inFavorites) {
                if(mViewModel.getTrip().getValue() != null){    // Make sure trip has been loaded
                    Boolean alreadyInFavorites = mModel.inCurrentUsersFavorites(mViewModel.getTrip().getValue().id);
                    if(inFavorites != alreadyInFavorites){
                        toggleFavorite(inFavorites);
                    }   // Don't do anything if trip is already added to/removed from favorites

                }
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.tripIonfoReviewsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mReviewsAdapter = new ReviewsListAdapter(mViewModel.getReviews(), mRecyclerView, this);
        mRecyclerView.setAdapter(mReviewsAdapter);

        return view;
    }

    private void toggleFavorite(Boolean inFavorites){
        mModel.toggleFavorite(mModel.getCurrentUser().userId, mViewModel.getTrip().getValue().id, inFavorites, this);
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
        if (context instanceof DataModel.DataManager) {
            mModel = ((DataModel.DataManager) context).getModel();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DataModel.DataManager");
        }
    }

    /*load more reviews from the server*/
    @Override
    public void onLoadMore() {
        int numLoaded =  mViewModel.numReviewsLoaded();

        mViewModel.setLoadingReviews(true);
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mReviewsAdapter.notifyItemInserted(mViewModel.numReviewsLoaded() - 1);
            }
        });
        mReviewsAdapter.setLoading(true);

        Trip trip = mViewModel.getTrip().getValue();
        if(trip.reviews.size() > numLoaded + reviewsBatchSize){   // Load 10 more
            mModel.getReviewsByIds(trip.reviews.subList(numLoaded, numLoaded + reviewsBatchSize), this);
        } else { // load all remaining
            mModel.getReviewsByIds(trip.reviews.subList(numLoaded, trip.reviews.size()), this);
        }
    }

    @Override
    public void onGetReviewsByIds(List<Review> reviews) {
        mViewModel.setLoadingReviews(false);
        Integer origSize = mViewModel.numReviewsLoaded();
        mReviewsAdapter.notifyItemRemoved(origSize);

        mViewModel.addReviews(reviews);
        mReviewsAdapter.notifyItemRangeInserted(origSize + 1, reviews.size());

        mReviewsAdapter.setLoading(false);

        if(reviews.size() < reviewsBatchSize){      // Set all loaded so no new loads are triggered
            mReviewsAdapter.setAllReviewsLoaded();
        }
    }

    private class ToggleFavoriteListener implements View.OnClickListener{
        public void onClick(View view){
            Log.i("TripInfoFragment", "Favorite " + mViewModel.getInFavorites().getValue());
        }
    };

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

        // get main image
        mModel.getImageById(trip.mainImageId, this);

        // get reviews
        onLoadMore();
    }

    @Override
    public void onGetFavoritesByUserId(List<Trip> favorites) { }

    @Override
    public void onGetImagesById(List<BitmapImage> images) {
        Trip trip = mViewModel.getTrip().getValue();
        if (trip == null){ return; }

        for(BitmapImage img: images){
            if(img.id == trip.mainImageId){
                ImageView mainImageView = (ImageView) this.getView().findViewById(R.id.tripInfoMainImageView);
                mainImageView.setImageBitmap(img.image);
            }
        }
    }
}
