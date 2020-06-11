package com.tripdazzle.daycation.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.databinding.FragmentSearchBinding;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.ui.triplist.HorizontalLongTripListFragment;
import com.tripdazzle.daycation.ui.triplist.TripListFragment;
import com.tripdazzle.daycation.ui.triplist.TripListViewModel;

import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    private DataModel mModel;
    private TripListViewModel mResultsListViewModel;
    private DataModel.OnSearchTripsListener onSearchResults;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        FragmentSearchBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();

        setupSearchView((SearchView) view.findViewById(R.id.searchSearchView));

        HorizontalLongTripListFragment resultsListFragment = (HorizontalLongTripListFragment) getChildFragmentManager().findFragmentById(R.id.searchResultsList);
        mResultsListViewModel = ViewModelProviders.of(resultsListFragment).get(TripListViewModel.class);
        resultsListFragment.setScrollListener(new TripListFragment.OnListScrollListener() {
            @Override
            public void onListScroll(int firstVisibleItemPosition) {
                mViewModel.setSelectedTrip(mResultsListViewModel.getTrips().getValue().get(firstVisibleItemPosition));
            }
        });

        onSearchResults = new DataModel.OnSearchTripsListener() {
            @Override
            public void onSearchTripsResults(List<Trip> trips) {
                mResultsListViewModel.setTrips(trips);
                if (trips.size() > 0){
                    mViewModel.setSelectedTrip(trips.get(0));
                }
            }
        };

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
                searchTrips();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.i("Search fragment", "change "+ newText);
                return false;
            }
        });
    }

    private void searchTrips(){
        mModel.searchTrips("stuff", onSearchResults);
    }

}
