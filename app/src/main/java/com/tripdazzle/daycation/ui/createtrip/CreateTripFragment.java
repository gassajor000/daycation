package com.tripdazzle.daycation.ui.createtrip;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.tripdazzle.daycation.databinding.FragmentCreateTripBinding;
import com.tripdazzle.daycation.models.Activity;
import com.tripdazzle.daycation.models.Creator;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.ui.activityselector.ActivitySelectorFragment;
import com.tripdazzle.daycation.ui.activityselector.ActivitySelectorViewModel;

import java.util.ArrayList;

public class CreateTripFragment extends Fragment {

    private CreateTripViewModel mViewModel;
    private ActivitySelectorFragment[] activitySelectors =  new ActivitySelectorFragment[3];
    private ActivitySelectorViewModel[] activityModels = new ActivitySelectorViewModel[3];
    private DataModel mModel;

    public static CreateTripFragment newInstance() {
        return new CreateTripFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(CreateTripViewModel.class);

        FragmentCreateTripBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_trip, container, false);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();

        view.findViewById(R.id.createTripSelectImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CreateTripFragment", "select trip image clicked");
                makeTrip();
            }
        });

        activitySelectors[0] = (ActivitySelectorFragment) getChildFragmentManager().findFragmentById(R.id.createTripActivity0);
        activitySelectors[1] = (ActivitySelectorFragment) getChildFragmentManager().findFragmentById(R.id.createTripActivity1);
        activitySelectors[2] = (ActivitySelectorFragment) getChildFragmentManager().findFragmentById(R.id.createTripActivity2);

        activityModels[0] = ViewModelProviders.of(activitySelectors[0]).get(ActivitySelectorViewModel.class);
        activityModels[1] = ViewModelProviders.of(activitySelectors[1]).get(ActivitySelectorViewModel.class);
        activityModels[2] = ViewModelProviders.of(activitySelectors[2]).get(ActivitySelectorViewModel.class);

        activityModels[0].setActivityNumber(0);
        activityModels[1].setActivityNumber(1);
        activityModels[2].setActivityNumber(2);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataModel.DataManager) {
            mModel = ((DataModel.DataManager) context).getModel();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DataModel.DataManager");
        }
    }

    private Trip makeTrip(){
        Activity[] activities = { activitySelectors[0].toActivity(), activitySelectors[1].toActivity(), activitySelectors[2].toActivity() };
        Creator creator = mModel.getCurrentUser().toCreator();
        return new Trip(mViewModel.getTitle(), -1, mViewModel.getDescription(), mViewModel.getLocation(), activities,
                0f, new ArrayList<Integer>(), creator, mViewModel.getMainImage().getValue());
    }

}
