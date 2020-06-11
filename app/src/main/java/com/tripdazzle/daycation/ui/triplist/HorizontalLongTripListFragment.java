package com.tripdazzle.daycation.ui.triplist;

import androidx.recyclerview.widget.RecyclerView;

import com.tripdazzle.daycation.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnTripListFragmentInteractionListener}
 * interface.
 */
public class HorizontalLongTripListFragment extends TripListFragment {

    @Override
    int getDirection() {
        return RecyclerView.HORIZONTAL;
    }

    @Override
    int getCardLayout() {
        return R.layout.layout_trip_card_long;
    }

    @Override
    int getFragmentLayout() {
        return R.layout.fragment_horizontal_long_trip_list;
    }
}
