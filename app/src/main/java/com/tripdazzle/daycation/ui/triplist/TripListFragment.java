package com.tripdazzle.daycation.ui.triplist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.models.Trip;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnTripListFragmentInteractionListener}
 * interface.
 */
public class TripListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private TripListViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private OnTripListFragmentInteractionListener mListener = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TripListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TripListFragment newInstance(int columnCount) {
        TripListFragment fragment = new TripListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);
        mViewModel = ViewModelProviders.of(this).get(TripListViewModel.class);
        mViewModel.getTrips().observe(this, tripListUpdateObserver);


        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mLayoutManager = new LinearLayoutManager(context);
            } else {
                mLayoutManager = new GridLayoutManager(context, mColumnCount);
            }
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(new TripListRecyclerViewAdapter(mViewModel.getTrips().getValue(), mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTripListFragmentInteractionListener) {
            mListener = (OnTripListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTripListFragmentInteractionListener {
        void onTripInteraction(Trip item);
    }

    Observer<List<Trip>> tripListUpdateObserver = new Observer<List<Trip>>() {
        @Override
        public void onChanged(List<Trip> trips) {
            if(recyclerView != null){
                recyclerView.setAdapter(new TripListRecyclerViewAdapter(trips, mListener));
            }
            // TODO fetch all the images
        }
    };
}
