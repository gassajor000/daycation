package com.tripdazzle.daycation.ui.tripinfo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.databinding.FragmentTripInfoBinding;
import com.tripdazzle.daycation.models.Review;
import com.tripdazzle.daycation.models.Trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TripInfoFragment extends Fragment implements DataModel.TripsSubscriber, DataModel.ImagesSubscriber, DataModel.ReviewsSubscriber, ReviewsListAdapter.OnLoadMoreListener {
    private TripInfoViewModel mViewModel;
    private DataModel model = new DataModel();
    private RecyclerView mRecyclerView;
    private ReviewsListAdapter mReviewsAdapter;
    protected Handler handler;

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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.tripIonfoReviewsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // TODO Remove
        Date date = new Date();
        mViewModel.addReviews(new ArrayList<Review>(Arrays.asList(
                new Review(301, "person1", (float) 1.0, date, "This is my comment"),
                new Review(302, "person2", (float) 2.0, date, "This is my comment"),
                new Review(303, "person3", (float) 3.0, date, "This is my comment"),
                new Review(304, "person4", (float) 4.0, date, "This is my comment")
        )));

        mReviewsAdapter = new ReviewsListAdapter(mViewModel.getReviews(), mRecyclerView, this);
        mRecyclerView.setAdapter(mReviewsAdapter);

        model.initialize(getContext());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model.getTripById(333, this);
    }

    /*load more reviews from the server*/
    @Override
    public void onLoadMore() {
        mViewModel.setLoadingReviews(true);
        mReviewsAdapter.notifyItemInserted(mViewModel.getReviews().size() - 1);

        model.getReviewsByIds(new ArrayList<Integer>(), this);
    }

    @Override
    public void onGetReviewsByIds(List<Review> reviews) {
        mViewModel.setLoadingReviews(false);
        Integer origSize = mViewModel.getReviews().size();
        mReviewsAdapter.notifyItemRemoved(origSize);

        mViewModel.addReviews(reviews);
        mReviewsAdapter.notifyItemRangeInserted(origSize + 1, reviews.size());

        mReviewsAdapter.setLoaded();
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
    public void onGetTripById(Trip trip) {
        mViewModel.setTrip(trip);

        // get main image
        model.getImageById(trip.mainImageId, this);
    }

    @Override
    public void onGetImageById(Bitmap image, Integer imageId) {
        Trip trip = mViewModel.getTrip().getValue();
        if(trip != null && imageId == trip.mainImageId){
            ImageView mainImageView = (ImageView) this.getView().findViewById(R.id.tripInfoMainImageView);
            mainImageView.setImageBitmap(image);
        }
    }
}
