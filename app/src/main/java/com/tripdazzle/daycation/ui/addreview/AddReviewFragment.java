package com.tripdazzle.daycation.ui.addreview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.ToolbarManager;
import com.tripdazzle.daycation.databinding.FragmentAddReviewBinding;
import com.tripdazzle.daycation.models.Review;

import java.util.Date;

public class AddReviewFragment extends Fragment {

    private AddReviewViewModel mViewModel;
    private int tripId;
    private DataModel mModel;
    private ToolbarManager toolbarManager;

    public static AddReviewFragment newInstance() {
        return new AddReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(AddReviewViewModel.class);

        FragmentAddReviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_review, container, false);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AddReviewFragmentArgs args = AddReviewFragmentArgs.fromBundle(getArguments());
        tripId = args.getTripId();
        mViewModel.setTripName(args.getTripName());

        mViewModel.setReviewer(mModel.getCurrentUser().toReviewer());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataModel.DataManager && context instanceof ToolbarManager) {
            mModel = ((DataModel.DataManager) context).getModel();
            toolbarManager = (ToolbarManager) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DataModel.DataManager and ToolbarManager");
        }
    }

    public void submit(){
        // create review and return to requesting fragment
        Review review = makeReview();
        // TODO call addReview on model
        // TODO return somehow?
    }

    private Review makeReview(){
        if(mViewModel.getComment().equals("")){
            return null;
        }
        return new Review(-1, mViewModel.getReviewer().getValue(), mViewModel.getRating(), new Date(), mViewModel.getComment(), tripId);
    }
}
