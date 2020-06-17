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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.databinding.FragmentSearchBinding;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.ui.triplist.HorizontalLongTripListFragment;
import com.tripdazzle.daycation.ui.triplist.TripListFragment;
import com.tripdazzle.daycation.ui.triplist.TripListViewModel;

import java.util.List;

public class SearchFragment extends Fragment implements OnMapReadyCallback {

    private SearchViewModel mViewModel;
    private DataModel mModel;
    private TripListViewModel mResultsListViewModel;
    private DataModel.OnSearchTripsListener onSearchResults;
    private MapView mapView;
    private GoogleMap searchMap;

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
                Trip selectedTrip = mResultsListViewModel.getTrips().getValue().get(firstVisibleItemPosition);
                mViewModel.setSelectedTrip(selectedTrip);
                searchMap.animateCamera(CameraUpdateFactory.newLatLng(selectedTrip.getMarker().getPosition()));
            }
        });
        resultsListFragment.setSnapping(true);

        onSearchResults = new DataModel.OnSearchTripsListener() {
            @Override
            public void onSearchTripsResults(List<Trip> trips) {
                mResultsListViewModel.setTrips(trips);
                if (trips.size() > 0){
                    mViewModel.setSelectedTrip(trips.get(0));
                }

                clearMarkers();
                addMarkers(trips);
            }
        };

        mapView = view.findViewById(R.id.searchMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
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

    private void clearMarkers(){
        if(searchMap != null){
            searchMap.clear();
            mViewModel.markers.clear();
        }
    }

    private void addMarkers(List<Trip> trips){
        LatLngBounds.Builder cameraBounds = LatLngBounds.builder();
        for(Trip trip: trips){
            MarkerOptions marker = trip.getMarker();
            mViewModel.markers.add(marker);
            searchMap.addMarker(marker);
            cameraBounds.include(marker.getPosition());
        }

        searchMap.moveCamera(CameraUpdateFactory.newLatLngBounds(cameraBounds.build(), 150));
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        searchMap = googleMap;
    }
}
