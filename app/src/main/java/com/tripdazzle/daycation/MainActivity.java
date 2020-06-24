package com.tripdazzle.daycation;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.models.feed.AddFavoriteEvent;
import com.tripdazzle.daycation.models.feed.CreatedTripEvent;
import com.tripdazzle.daycation.models.feed.FeedEvent;
import com.tripdazzle.daycation.models.feed.ReviewEvent;
import com.tripdazzle.daycation.ui.accountinfo.AccountInfoFragmentDirections;
import com.tripdazzle.daycation.ui.favorites.FavoritesFragmentDirections;
import com.tripdazzle.daycation.ui.feed.FeedFragment;
import com.tripdazzle.daycation.ui.home.HomeFragmentDirections;
import com.tripdazzle.daycation.ui.profile.ProfileFragmentDirections;
import com.tripdazzle.daycation.ui.search.SearchFragmentDirections;
import com.tripdazzle.daycation.ui.triplist.TripListFragment;

public class MainActivity extends AppCompatActivity  implements TripListFragment.OnTripListFragmentInteractionListener, DataModel.DataManager, FeedFragment.OnFeedEventInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DataModel mModel = new DataModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_accountInfo, R.id.nav_favorites)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        mModel.initialize(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onTripInteraction(Trip item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavDirections action;
        switch (navController.getCurrentDestination().getId()){
            case R.id.nav_profile: {
                action = ProfileFragmentDirections.actionNavProfileToTripInfo(item.id);
                break;
            }
            case R.id.nav_accountInfo: {
                action = AccountInfoFragmentDirections.actionNavAccountInfoToTripInfo(item.id);
                break;
            }
            case R.id.nav_favorites: {
                action = FavoritesFragmentDirections.actionNavFavoritesToTripInfo(item.id);
                break;
            }
            case R.id.nav_home: {
                action = HomeFragmentDirections.actionNavHomeToTripInfo(item.id);
                break;
            }
            case R.id.nav_search: {
                action = SearchFragmentDirections.actionNavSearchToNavTripInfo(item.id);
                break;
            }
            default: {
                throw new RuntimeException("Unknown destination: " + navController.getCurrentDestination().getLabel());
            }
        }

        navController.navigate(action);
    }

    @Override
    public DataModel getModel() {
        return mModel;
    }

    @Override
    public void onFeedEventInteraction(FeedEvent event) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavDirections action;
        if (event instanceof AddFavoriteEvent){
            action = HomeFragmentDirections.actionNavHomeToTripInfo(((AddFavoriteEvent) event).trip.id);
        } else if(event instanceof CreatedTripEvent) {
            action = HomeFragmentDirections.actionNavHomeToTripInfo(((CreatedTripEvent) event).trip.id);
        }  else if(event instanceof ReviewEvent) {
            action = HomeFragmentDirections.actionNavHomeToTripInfo(((ReviewEvent) event).review.tripId);
        } else {
            throw new RuntimeException("Unknown feed event type: " + event.toString());
        }
        navController.navigate(action);
    }
}
