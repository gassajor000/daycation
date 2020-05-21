package com.tripdazzle.daycation.ui.triplist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.models.Activity;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.server.datamodels.ActivityType;

import java.util.ArrayList;
import java.util.Arrays;
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

    private OnTripListFragmentInteractionListener mListener = null;
    Activity[] activities = {
            new Activity(ActivityType.HIKING, "Rose Canyon", "Hike Rose Canyon"),
            new Activity(ActivityType.ICE_CREAM, "Shake Shack", "Get Ice cream at Shake Shack"),
            new Activity(ActivityType.BEACH, "Mission Beach", "Go Swimming at Mission Beach")
    };
    private List<Trip> trips = new ArrayList<>(Arrays.asList(
            new Trip("SD Vacay", 333, "gassajor",
                    "Fun Trip around the San Diego Bay.",   444, activities,
                    (float) 3.7, new ArrayList<Integer>(Arrays.asList(501, 502, 503, 504, 505, 506, 507,
                    508, 509, 510, 511, 512, 513, 514, 515, 516, 517, 518, 519, 520)))
    ));

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

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new TripListRecyclerViewAdapter(trips, mListener));
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
}
