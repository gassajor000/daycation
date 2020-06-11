package com.tripdazzle.daycation.ui.triplist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripdazzle.daycation.models.Trip;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnTripListFragmentInteractionListener}
 * interface.
 */
public abstract class TripListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private TripListViewModel mViewModel;       // Data is loaded externally via the viewModel
    private TripListRecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnListScrollListener scrollListener;

    private OnTripListFragmentInteractionListener mListener = null;

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
        View view = inflater.inflate(getFragmentLayout(), container, false);
        mViewModel = ViewModelProviders.of(this).get(TripListViewModel.class);
        mViewModel.getTrips().observe(this, tripListUpdateObserver);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mLayoutManager = new LinearLayoutManager(context, getDirection(), false);
            } else {
                mLayoutManager = new GridLayoutManager(context, mColumnCount);
            }
            recyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new TripListRecyclerViewAdapter(mViewModel, mListener, getCardLayout());
            recyclerView.setAdapter(mAdapter);

            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (scrollListener != null){
                        scrollListener.onListScroll(((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition());
                    }
                }
            });

        }
        return view;
    }

    /* Implementing classes must implement depending on their direction*/
    abstract @RecyclerView.Orientation int getDirection();

    /* Implementing classes must implement depending on the card layout they use*/
    abstract @LayoutRes int getCardLayout();

    /* Implementing classes must implement depending on the fragment layout*/
    abstract @LayoutRes int getFragmentLayout();

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
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    public interface OnListScrollListener{
        void onListScroll(int firstVisibleItemPosition);
    }

    public void setScrollListener(OnListScrollListener listener){
        this.scrollListener = listener;
    }
}
