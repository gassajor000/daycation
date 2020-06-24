package com.tripdazzle.daycation.ui.activityselector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.databinding.FragmentActivitySelectorBinding;
import com.tripdazzle.daycation.models.Activity;
import com.tripdazzle.daycation.models.ActivityType;
import com.tripdazzle.daycation.ui.locationselector.LocationSelectorFragment;

public class ActivitySelectorFragment extends Fragment  implements AdapterView.OnItemSelectedListener {

    private ActivitySelectorViewModel mViewModel;
    private Spinner mSpinner;
    private LocationSelectorFragment activityLocation;

    public static ActivitySelectorFragment newInstance() {
        return new ActivitySelectorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ActivitySelectorViewModel.class);

        FragmentActivitySelectorBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity_selector, container, false);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();

        mSpinner = (Spinner) view.findViewById(R.id.activitySelectorActivityType);
        ArrayAdapter mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ActivityType.values());
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(this);

        activityLocation = (LocationSelectorFragment) getChildFragmentManager().findFragmentById(R.id.activitySelectorLocation);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public Activity toActivity(){
        return new Activity(mViewModel.getType(), activityLocation.getLocation(), mViewModel.getDescription());
    }

    public boolean locationIsSelected(){
        return activityLocation.isSelected();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mViewModel.setType((ActivityType) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mViewModel.setType(null);
    }
}
