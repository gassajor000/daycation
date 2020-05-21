package com.tripdazzle.daycation.ui.tripinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripdazzle.daycation.BR;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.models.Review;

import java.text.DateFormat;
import java.util.List;

public class ReviewsListAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<Review> reviewList;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private boolean allReviewsLoaded = false;
    private OnLoadMoreListener onLoadMoreListener;

    // Call back after asynchronous load completes
    public interface OnLoadMoreListener {
        void onLoadMore();
    }


    public ReviewsListAdapter(List<Review> reviews, RecyclerView recyclerView, OnLoadMoreListener callback) {
        reviewList = reviews;
        this.onLoadMoreListener = callback;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading && !allReviewsLoaded
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    public void setAllReviewsLoaded(){
        allReviewsLoaded = true;
    }

    public void setLoading(Boolean loading){
        this.loading = loading;
    }

    @Override
    public int getItemViewType(int position) {
        return reviewList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            ViewDataBinding b = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.layout_review_card, parent, false);

            vh = new ReviewViewHolder(b);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_progressbar, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReviewViewHolder) {

            Review singleReview = (Review) reviewList.get(position);

            ((ReviewViewHolder) holder).bind(singleReview);

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        public final ViewDataBinding binding;
        private DateFormat dateFormat =  DateFormat.getDateInstance();


        public ReviewViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Review review){
            binding.setVariable(BR.review, review);
            binding.setVariable(BR.dateFormat, dateFormat);
            binding.executePendingBindings();
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }
}