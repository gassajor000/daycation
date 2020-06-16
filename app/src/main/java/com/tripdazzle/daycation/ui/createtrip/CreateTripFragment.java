package com.tripdazzle.daycation.ui.createtrip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.model.LatLng;
import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;
import com.tripdazzle.daycation.databinding.FragmentCreateTripBinding;
import com.tripdazzle.daycation.models.Activity;
import com.tripdazzle.daycation.models.BitmapImage;
import com.tripdazzle.daycation.models.Creator;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.models.location.CustomLocation;
import com.tripdazzle.daycation.ui.activityselector.ActivitySelectorFragment;
import com.tripdazzle.daycation.ui.activityselector.ActivitySelectorViewModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CreateTripFragment extends Fragment implements DataModel.TaskContext {

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
        setHasOptionsMenu(true);

        mViewModel = ViewModelProviders.of(this).get(CreateTripViewModel.class);

        FragmentCreateTripBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_trip, container, false);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();

        view.findViewById(R.id.createTripSelectImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        // Activity Selectors
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_createtrip_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection;,
        if (item.getItemId() == R.id.option_create_trip) {
            onCreateTrip();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onCreateTrip(){
        if(mViewModel.getMainImage().getValue() == null){
            Toast chooseImgMsg = Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT);
            chooseImgMsg.show();
            return;
        }

        // Send request to server
        mModel.createTrip(makeTrip(), this);

        // navigate back to previous screen
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }

    public void selectImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == android.app.Activity.RESULT_OK) {
                Uri selectedImage = data.getData();

                try {
                    Bitmap image = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(selectedImage));
                    mViewModel.setMainImage(new BitmapImage(image, -1));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException("File not found " + selectedImage);
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
        return new Trip(mViewModel.getTitle(), -1, mViewModel.getDescription(), new CustomLocation(mViewModel.getLocation(), new LatLng(0, 0)), activities,
                0f, new ArrayList<Integer>(), creator, mViewModel.getMainImage().getValue());
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onError(String message) {

    }
}
