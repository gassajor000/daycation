package com.tripdazzle.daycation.ui.triplist;

import androidx.recyclerview.widget.RecyclerView;

import com.tripdazzle.daycation.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnTripListFragmentInteractionListener}
 * interface.
 */
public class VerticalLongTripListFragment extends TripListFragment {

    @Override
    int getDirection() {
        return RecyclerView.VERTICAL;
    }

    @Override
    int getCardLayout() {
        return R.layout.layout_trip_card_long;
    }

    @Override
    int getFragmentLayout() {
        return R.layout.fragment_vertical_long_trip_list;
    }
}
