package com.tripdazzle.daycation.ui.reviewslist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.models.Review;

import java.util.List;

public class ReviewsListFragment extends Fragment implements ReviewsListAdapter.OnBottomScrollListener {

    private ReviewsListViewModel mViewModel;
    private ReviewsListAdapter mReviewsAdapter;
    private RecyclerView mRecyclerView;
    private OnLoadMoreListener loadMoreCallback;

    // Call back after asynchronous load completes
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public static ReviewsListFragment newInstance() {
        return new ReviewsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(ReviewsListViewModel.class);
        mViewModel.subscribeReviews(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                updateReviews(sender);
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                updateReviews(sender);
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                updateReviews(sender);
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                updateReviews(sender);
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                updateReviews(sender);
            }
        });

        View view = inflater.inflate(R.layout.fragment_reviews_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.reviewsListRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mReviewsAdapter = new ReviewsListAdapter(mViewModel.getReviews(), mRecyclerView, this);
        mRecyclerView.setAdapter(mReviewsAdapter);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void updateReviews(List<Review> reviews){
        mViewModel.setLoadingReviews(false);
        Integer origSize = mViewModel.numReviewsLoaded();
        mReviewsAdapter.notifyDataSetChanged();

        mReviewsAdapter.notifyItemRangeInserted(origSize + 1, reviews.size());
    }

    @Override
    public void onBottomScroll() {
        if (!mViewModel.isLoading() && !mViewModel.allReviewsLoaded() && loadMoreCallback != null){
            // Begin loading more reviews
            loadMoreCallback.onLoadMore();
        }
    }

    public void setLoadMoreCallback(OnLoadMoreListener loadMoreCallback) {
        this.loadMoreCallback = loadMoreCallback;
    }
}
