package com.tripdazzle.daycation.ui.triplist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.tripdazzle.daycation.BR;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.ui.triplist.TripListFragment.OnTripListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Trip} and makes a call to the
 * specified {@link OnTripListFragmentInteractionListener}.
 */
public class TripListRecyclerViewAdapter extends RecyclerView.Adapter<TripListRecyclerViewAdapter.ViewHolder> {

    private final OnTripListFragmentInteractionListener mListener;
    private TripListViewModel mViewModel;
    private @LayoutRes int cardType;

    public TripListRecyclerViewAdapter(TripListViewModel viewModel, OnTripListFragmentInteractionListener listener, @LayoutRes int cardType) {
        mListener = listener;
        mViewModel = viewModel;
        this.cardType = cardType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding b;
        b = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                cardType, parent, false);

        return new ViewHolder(b);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Trip trip = mViewModel.getTrips().getValue().get(position);

        holder.bind(trip);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onTripInteraction(holder.mItem);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return mViewModel.getTrips().getValue().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ViewDataBinding binding;
        public Trip mItem;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Trip trip){
            mItem = trip;
            binding.setVariable(BR.trip, trip);

            binding.executePendingBindings();
        }

    }
}
