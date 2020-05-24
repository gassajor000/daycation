package com.tripdazzle.daycation.ui.favorites;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.ui.triplist.TripListViewModel;

import java.util.List;

public class FavoritesFragment extends Fragment  implements DataModel.TripsSubscriber {

    private TripListViewModel mFavoriteTripsModel;
    private DataModel mModel;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        mFavoriteTripsModel = ViewModelProviders.of(getChildFragmentManager().findFragmentById(R.id.favoritesFavoriteTripsList)).get(TripListViewModel.class);
        mModel.getFavoritesByUserId(mModel.getCurrentUser().userId, this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataModel.DataManager) {
            mModel = ((DataModel.DataManager) context).getModel();
        } else {
            throw new RuntimeException(context.toString() + " must implement DataModel.DataManager");
        }
    }
    @Override
    public void onSuccess(String message) {}

    @Override
    public void onError(String message) {}

    @Override
    public void onGetTripsById(List<Trip> trips) { }

    @Override
    public void onGetFavoritesByUserId(List<Trip> favorites) {
        mFavoriteTripsModel.setTrips(favorites);
    }

}
