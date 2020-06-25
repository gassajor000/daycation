package com.tripdazzle.daycation.ui.accountinfo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.ToolbarManager;
import com.tripdazzle.daycation.databinding.FragmentAccountInfoBinding;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.ui.triplist.TripListViewModel;

import java.util.List;

public class AccountInfoFragment extends Fragment implements DataModel.TripsSubscriber {

    private AccountInfoViewModel mViewModel;
    private TripListViewModel mCreatedTripsModel;
    private DataModel mModel;
    private ToolbarManager toolbarManager;


    public static AccountInfoFragment newInstance() {
        return new AccountInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(AccountInfoViewModel.class);
        FragmentAccountInfoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_info, container, false);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();

        mCreatedTripsModel = ViewModelProviders.of(getChildFragmentManager().findFragmentById(R.id.profileCreatedTripsList)).get(TripListViewModel.class);

        Toolbar toolbar = view.findViewById(R.id.mainToolbar);
        toolbarManager.initializeToolbar(toolbar);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.setUser(mModel.getCurrentUser());
        mModel.getTripsByIds(mViewModel.getUser().getValue().createdTrips, this);
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

    @Override
    public void onSuccess(String message) {}

    @Override
    public void onError(String message) {}

    @Override
    public void onGetTripsById(List<Trip> trips) {
        mCreatedTripsModel.setTrips(trips);
    }

    @Override
    public void onGetFavoritesByUserId(List<Trip> favorites) { }
}
