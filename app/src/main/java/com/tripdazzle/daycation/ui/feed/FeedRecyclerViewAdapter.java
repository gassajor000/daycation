package com.tripdazzle.daycation.ui.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.tripdazzle.daycation.BR;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.models.feed.AddFavoriteEvent;
import com.tripdazzle.daycation.models.feed.CreatedTripEvent;
import com.tripdazzle.daycation.models.feed.FeedEvent;
import com.tripdazzle.daycation.models.feed.ReviewEvent;
import com.tripdazzle.daycation.ui.feed.FeedFragment.OnFeedEventInteractionListener;

import java.text.DateFormat;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Trip} and makes a call to the
 * specified {@link OnFeedEventInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.ViewHolder> {

    private final OnFeedEventInteractionListener mListener;
    private FeedViewModel mViewModel;
    private static DateFormat dateFormat =  DateFormat.getDateInstance();

    public FeedRecyclerViewAdapter(OnFeedEventInteractionListener listener, FeedViewModel mViewModel) {
        mListener = listener;
        this.mViewModel = mViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding b = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.layout_feed_item, parent, false);

        ViewStub contentView = (ViewStub) b.getRoot().findViewById(R.id.feedContent);
        switch (viewType) {
            case 1:
            case 2: {
                contentView.setLayoutResource(R.layout.layout_trip_card_long);
                break;
            }
            case 3: {
                contentView.setLayoutResource(R.layout.layout_review_card);
                break;
            }
        }

        View inflatedContentView = contentView.inflate();
        return new ViewHolder(b, DataBindingUtil.getBinding(inflatedContentView));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        FeedEvent event = mViewModel.getEvents().getValue().get(position);
        holder.bind(event);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onFeedEventInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        FeedEvent event = mViewModel.getEvents().getValue().get(position);
        if(event instanceof AddFavoriteEvent){
            return 1;
        } else if(event instanceof CreatedTripEvent){
            return 2;
        } else if (event instanceof ReviewEvent){
            return 3;
        }

        return -1;
    }

    @Override
    public int getItemCount() {
        return mViewModel.getEvents().getValue().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ViewDataBinding binding;
        public final View mView;
        public FeedEvent mItem;
        public final ViewDataBinding mContentBinding;


        public ViewHolder(ViewDataBinding binding, ViewDataBinding contentBinding) {
            super(binding.getRoot());
            mView = binding.getRoot();
            this.binding = binding;
            this.mContentBinding = contentBinding;
        }

        public void bind(FeedEvent event){
            mItem = event;
            binding.setVariable(BR.event, event);
            binding.executePendingBindings();

            if(event instanceof AddFavoriteEvent){
                // Inflate trip card
                mContentBinding.setVariable(BR.trip, ((AddFavoriteEvent) event).trip);
            } else if (event instanceof CreatedTripEvent){
                // Inflate trip card
                mContentBinding.setVariable(BR.trip, ((CreatedTripEvent) event).trip);
            } else if (event instanceof ReviewEvent){
                // Inflate review card
                mContentBinding.setVariable(BR.review, ((ReviewEvent) event).review);
                mContentBinding.setVariable(BR.dateFormat, dateFormat);
            }
            mContentBinding.executePendingBindings();

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.description + "'";
        }
    }
}
