package com.tripdazzle.daycation.ui.addreview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.ToolbarManager;
import com.tripdazzle.daycation.databinding.FragmentAddReviewBinding;
import com.tripdazzle.daycation.models.Review;

import java.util.Date;

public class AddReviewFragment extends Fragment implements DataModel.TaskContext {

    private AddReviewViewModel mViewModel;
    private int tripId;
    private DataModel mModel;
    private ToolbarManager toolbarManager;
    private NavController navController;
    private Toolbar toolbar;

    public static AddReviewFragment newInstance() {
        return new AddReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mViewModel = ViewModelProviders.of(this).get(AddReviewViewModel.class);

        FragmentAddReviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_review, container, false);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();

        toolbar = view.findViewById(R.id.mainToolbar);
        toolbarManager.initializeToolbar(toolbar);

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
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_addreview_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection;,
        if (item.getItemId() == R.id.option_add_review) {
            submit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submit(){
        // create review and return to requesting fragment
        Review review = makeReview();
        mModel.createReview(review, this);
    }

    private Review makeReview(){
        if(mViewModel.getComment().equals("")){
            return null;
        }
        return new Review(-1, mViewModel.getReviewer().getValue(), mViewModel.getRating(), new Date(), mViewModel.getComment(), tripId);
    }

    @Override
    public void onSuccess(String message) {
        // Navigate up
        navController.navigateUp();
    }

    @Override
    public void onError(String message) {
        // Show toast
        Toast chooseImgMsg = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        chooseImgMsg.show();
    }
}
