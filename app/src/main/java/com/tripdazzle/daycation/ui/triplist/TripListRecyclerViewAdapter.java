package com.tripdazzle.daycation.ui.triplist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.tripdazzle.daycation.BR;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.ui.triplist.TripListFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Trip} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class TripListRecyclerViewAdapter extends RecyclerView.Adapter<TripListRecyclerViewAdapter.ViewHolder> {

    private final List<Trip> mValues;
    private final OnListFragmentInteractionListener mListener;

    public TripListRecyclerViewAdapter(List<Trip> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding b = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.layout_trip_card, parent, false);

        return new ViewHolder(b);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(mValues.get(position));

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
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
