package com.tripdazzle.daycation.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.ui.triplist.TripListViewModel;

import java.util.List;

public class SearchFragment extends Fragment implements DataModel.TripsSubscriber {

    private SearchViewModel mViewModel;
    private DataModel mModel;
    private TripListViewModel mResultsListViewModel;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setupSearchView((SearchView) view.findViewById(R.id.searchSearchView));

        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        mResultsListViewModel = ViewModelProviders.of(getChildFragmentManager().findFragmentById(R.id.searchResultsList)).get(TripListViewModel.class);

        mModel.getFavoritesByUserId("mscott", this);

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
            throw new RuntimeException(context.toString()
                    + " must implement DataModel.DataManager");
        }
    }

    private void setupSearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Log.i("Search fragment", "submit "+ query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.i("Search fragment", "change "+ newText);
                return false;
            }
        });
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onGetTripsById(List<Trip> trips) {

    }

    @Override
    public void onGetFavoritesByUserId(List<Trip> favorites) {
        mResultsListViewModel.setTrips(favorites);
    }
}