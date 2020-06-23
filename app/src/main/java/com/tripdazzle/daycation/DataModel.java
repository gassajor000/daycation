package com.tripdazzle.daycation;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.tripdazzle.daycation.models.BitmapImage;
import com.tripdazzle.daycation.models.Profile;
import com.tripdazzle.daycation.models.ProfilePicture;
import com.tripdazzle.daycation.models.Review;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.daycation.models.User;
import com.tripdazzle.daycation.models.feed.AddFavoriteEvent;
import com.tripdazzle.daycation.models.feed.CreatedTripEvent;
import com.tripdazzle.daycation.models.feed.FeedEvent;
import com.tripdazzle.daycation.models.feed.ReviewEvent;
import com.tripdazzle.daycation.models.location.LocationBuilder;
import com.tripdazzle.server.ProxyServer;
import com.tripdazzle.server.ServerError;
import com.tripdazzle.server.datamodels.BitmapData;
import com.tripdazzle.server.datamodels.ProfilePictureData;
import com.tripdazzle.server.datamodels.ReviewData;
import com.tripdazzle.server.datamodels.TripData;
import com.tripdazzle.server.datamodels.feed.AddFavoriteEventData;
import com.tripdazzle.server.datamodels.feed.CreatedTripEventData;
import com.tripdazzle.server.datamodels.feed.FeedEventData;
import com.tripdazzle.server.datamodels.feed.ReviewEventData;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DataModel {
    private ProxyServer server = new ProxyServer();
    private User currentUser;
    public PlacesClient placesClient;
    public PlacesManager placesManager;
    public LocationBuilder locationBuilder;     // Makes blocking requests, only use in task context

    public void initialize(Context context) {
        String localFilesDir = context.getFilesDir().getAbsolutePath();
        server.setDbLocation(localFilesDir);

        // add demo images to db
        Integer[] images = {R.drawable.mission_bay, R.drawable.balboa, R.drawable.lajolla, R.drawable.zoo, R.drawable.mscott, R.drawable.jhalpert};
        for(Integer i: images){
            InputStream in = context.getResources().openRawResource(i);
            try {
                server.addImage(new BitmapData(-1, in));
            } catch (ServerError serverError) {
                serverError.printStackTrace();
                throw new RuntimeException(serverError);
            }
        }

        // Set current User
        try {
            currentUser = new User(server.login("mscott", "password123"));
        } catch (ServerError serverError) {
            serverError.printStackTrace();
        }

        // Initialize places SDK
        String apiKey = null;
        try {
            apiKey = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("com.google.android.geo.API_KEY");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Places.initialize(context, apiKey);
        placesClient = Places.createClient(context);
        placesManager = new PlacesManager(Arrays.asList(
//                "ChIJIeqEu0gB3IARRdcuT4Ya5gI", "ChIJwT8jJCVV2YARJ40GRpZKVG8", "ChIJ9dZdfnyq3oARI_cFYz7QSKM",
//                "ChIJm4tNIXaq3oARZ_p_loyw1wc", "ChIJWzruHEsA3IARf3hTyv_2gT8", "ChIJKw-CyI8G3IARj9ySO5sCoc4",
//                "ChIJSx6SrQ9T2YARed8V_f0hOg0", "ChIJzQ7MT3bQ24ARlDAdXPQe5fw", "ChIJA8tw-pZU2YARxPYVsDwL8-0",
                "ChIJyYB_SZVU2YARR-I1Jjf08F0"));
        locationBuilder = new LocationBuilder(placesManager);
    }

    // Call when the user's data changes
    private void refreshUser(){
        try {
            this.currentUser = new User(server.login("mscott", "password123"));
        } catch (ServerError serverError) {
            serverError.printStackTrace();
        }
    }

    /* Data getters/setters*/
    public void getImageById(int imageId, ImagesSubscriber callback) {
        new GetImagesByIdsTask(callback).execute(Collections.singletonList(imageId));
    }
    public void getImagesByIds(List<Integer> imageIds, ImagesSubscriber callback) {
        new GetImagesByIdsTask(callback).execute(imageIds);
    }
    public void getProfilePicturesByIds(List<String> userIds, ImagesSubscriber callback) {
        new GetProfilePicturesByUserIdsTask(callback).execute(userIds);
    }

    public void getTripById(int tripId, TripsSubscriber callback){
        new GetTripsByIdsTask(callback).execute(Collections.singletonList(tripId));
    }

    public void getTripsByIds(List<Integer> tripIds, TripsSubscriber callback){
        new GetTripsByIdsTask(callback).execute(tripIds);
    }

    public void getReviewsByIds(List<Integer> reviewIds, ReviewsSubscriber callback){
        new GetReviewsByIdsTask(callback).execute(reviewIds);
    }

    public void getProfileById(String userId, ProfilesSubscriber callback) {
        new GetProfileByIdTask(callback).execute(userId);
    }

    public void getNewsFeed(String userId, OnGetNewsFeedListener callback){
        new GetNewsFeedTask(callback).execute(userId);
    }

    public void searchTrips(String query, OnSearchTripsListener callback) {
        new SearchTripsTask(callback).execute(query);
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void getFavoritesByUserId(String userId, TripsSubscriber callback){
        new GetFavoritesByUserIdTask(callback).execute(userId);
    }

    public void toggleFavorite(String userId, Integer tripId, Boolean addFavorite, TripsSubscriber callback){
        new ToggleFavoriteTask(callback).execute(new ToggleFavoritesParams(userId, tripId, addFavorite));
    }

    public Boolean inCurrentUsersFavorites(Integer tripId){
        return currentUser.inFavorites(tripId);
    }

    public void getRecommendedTripsForUser(String userId, OnRecommendedTripsListener callback){
        new GetRecommendedTripsByUserIdTask(callback).execute(userId);
    }

    public void createTrip(Trip trip, TaskContext context){
        new CreateTripTask(context).execute(trip);
    }

    /* Places manager */
    public class PlacesManager {
        private Map<String, Place> places = new HashMap<>();
        List<Place.Field> standardFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        private OnSuccessListener<FetchPlaceResponse> addPlaceListener = new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                Place place = fetchPlaceResponse.getPlace();
                places.put(place.getId(), place);
            }
        };

        public PlacesManager(List<String> places) {
            addPlacesAsync(places);
        }

        public void addPlacesAsync(List<String> places){
            for(String placeId: places){
                FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, standardFields);
                placesClient.fetchPlace(request).addOnSuccessListener(addPlaceListener);
            }
        }

        /* Returns place matching id, fetching it from the places API if necessary. Blocking.*/
        public Place addAndGetPlaceById(String placeId){
            Place place;
            if(places.containsKey(placeId)){
                place = places.get(placeId);
            } else {
                FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, standardFields);
                Task<FetchPlaceResponse> task = placesClient.fetchPlace(request).addOnSuccessListener(addPlaceListener);
                try {
                    Tasks.await(task);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                place = task.getResult().getPlace();
            }

            if(place == null){
                throw new NullPointerException("Null place! Id " + placeId);
            }

            return place;
        }

        public Place getPlaceById(String id){       // may be null
            return places.get(id);
        }
    }

    /*
    * Asynchronous Tasks
    */
    // Interfaces
    public interface DataManager {
        /* Implemented by the main activity that serves as the keeper of the data model */
        public DataModel getModel();
    }

    public interface TaskContext{
        /** onSuccess: called on success of an async task
         * @param message Message to return*/
        void onSuccess(String message);
        /** onSuccess: called on error of an async task
         * @param message Message to return*/
        void onError(String message);
    }

    public interface TripsSubscriber extends TaskContext {
        /** called on fetch of a trip by id
         * @param trips Trip returned by query*/
        void onGetTripsById(List<Trip> trips);
        void onGetFavoritesByUserId(List<Trip> favorites);
    }

    public interface ImagesSubscriber extends TaskContext {
        /** called on fetch of an image by id
         * @param images BitmapImage returned by query
         * */
        void onGetImagesById(List<BitmapImage> images);
        void onGetProfilePicturesByUserIds(List<ProfilePicture> images);
    }

    public interface ProfilesSubscriber extends TaskContext {
        /** called on fetch of a profile by id
         * @param profile Profile fetched from server
         * */
        void onGetProfileById(Profile profile);
    }

    public interface ReviewsSubscriber extends TaskContext {
        /** called on fetch of a list of reviews by id
         * @param reviews Reviews returned by query
         * */
        void onGetReviewsByIds(List<Review> reviews);
    }

    public interface OnRecommendedTripsListener {
        void onRecommendedTrips(List<Trip> trips);
    }

    public interface OnSearchTripsListener {
        void onSearchTripsResults(List<Trip> trips);
    }

    public interface OnGetNewsFeedListener {
        void onGetNewsFeed(List<FeedEvent> feed);
    }

    // Tasks
    private class GetTripsByIdsTask extends AsyncTask<List<Integer>, Void, List<Trip>> {
        /** Application Context*/
        private TripsSubscriber context;

        private GetTripsByIdsTask(TripsSubscriber context) {
            this.context = context;
        }

        @Override
        protected List<Trip> doInBackground(List<Integer> ... tripIds) {
            if (tripIds.length > 1){
                return null;
            } else {
                List<TripData> tripsData = server.getTripsById(tripIds[0]);
                List<Trip> trips = new ArrayList<>();
                for (TripData t : tripsData) {
                    trips.add(new Trip(t, locationBuilder));
                }
                return trips;
            }
        }

        @Override
        protected void onPostExecute(List<Trip> result) {
            super.onPostExecute(result);
            if(result == null){
                context.onError("No trip found with corresponding id");
            }
            else {
                // onSuccess()?
                context.onGetTripsById(result);
            }
        }
    }

    private class CreateTripTask extends AsyncTask<Trip, Void, Boolean> {
        /** Application Context*/
        private TaskContext context;

        private CreateTripTask(TaskContext context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Trip ... trips) {
            if (trips.length > 1){
                return false;
            }
            try {
                if(trips[0].mainImage.id == -1){        // Add the image if it doesn't exist yet
                    Trip trip = trips[0];
                    int imgId = server.addImage(trip.mainImage.toData());
                    server.createTrip(trip.toDataNewImage(imgId));
                } else {
                    server.createTrip(trips[0].toData());
                }

                refreshUser();
            } catch (ServerError serverError) {
                serverError.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result == false){
                context.onError("No trip found with corresponding id");
            }
            else {
                // onSuccess()?
                context.onSuccess("Trip successfully created");
            }
        }
    }

    private class GetImagesByIdsTask extends AsyncTask<List<Integer>, Void, List<BitmapImage>> {
        /** Application Context*/
        private ImagesSubscriber context;

        private GetImagesByIdsTask(ImagesSubscriber context) {
            this.context = context;
        }

        @Override
        protected List<BitmapImage> doInBackground(List<Integer> ... imageIds) {
            if (imageIds.length > 1){
                return null;
            } else {
                try {
                    List<BitmapData> imageData = server.getImagesById(imageIds[0]);
                    List<BitmapImage> images = new ArrayList<>();

                    for(BitmapData bmp: imageData){
                        images.add(new BitmapImage(bmp));
                    }

                    return images;
                } catch (ServerError serverError) {
                    serverError.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<BitmapImage> result) {
            super.onPostExecute(result);
            if(result == null || result.size() == 0){
                context.onError("No images found");
            } else {
                // onSuccess()?
                context.onGetImagesById(result);
            }
        }
    }

    private class GetProfilePicturesByUserIdsTask extends AsyncTask<List<String>, Void, List<ProfilePicture>> {
        /**
         * Application Context
         */
        private ImagesSubscriber context;

        private GetProfilePicturesByUserIdsTask(ImagesSubscriber context) {
            this.context = context;
        }

        @Override
        protected List<ProfilePicture> doInBackground(List<String>... userIds) {
            if (userIds.length > 1){
                return null;
            } else {
                try {
                    List<ProfilePictureData> imageData = server.getProfilePicturesByUserIds(userIds[0]);
                    List<ProfilePicture> images = new ArrayList<>();

                    for(ProfilePictureData bmp: imageData){
                        images.add(new ProfilePicture(bmp));
                    }

                    return images;
                } catch (ServerError serverError) {
                    serverError.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<ProfilePicture> result) {
            super.onPostExecute(result);
            if(result == null || result.size() == 0) {
                context.onError("No images found");
            } else {
                // onSuccess()?
                context.onGetProfilePicturesByUserIds(result);
            }
        }
    }

    private class GetReviewsByIdsTask extends AsyncTask<List<Integer>, Void, List<Review>> {
        /** Application Context*/
        private ReviewsSubscriber context;

        private GetReviewsByIdsTask(ReviewsSubscriber context) {
            this.context = context;
        }

        @Override
        protected List<Review> doInBackground(List<Integer> ... reviewIds) {
            if (reviewIds.length > 1){
                return null;
            } else {
                List<ReviewData> reviewsData;
                try {
                    Thread.sleep(2000);
                    reviewsData = server.getReviewsById(reviewIds[0]);

                } catch (InterruptedException | ServerError serverError) {
                    serverError.printStackTrace();
                    return null;
                }

                List<Review> reviews = new ArrayList<>();
                for (ReviewData r : reviewsData) {
                    reviews.add(new Review(r));
                }
                return reviews;
            }
        }

        @Override
        protected void onPostExecute(List<Review> result) {
            super.onPostExecute(result);
            if(result == null){
                context.onError("Server Error");
            } else {
                // onSuccess()?
                context.onGetReviewsByIds(result);
            }
        }
    }

    private class GetProfileByIdTask extends AsyncTask<String, Void, Profile> {
        /** Application Context*/
        private ProfilesSubscriber context;

        private GetProfileByIdTask(ProfilesSubscriber context) {
            this.context = context;
        }

        @Override
        protected Profile doInBackground(String ... userIds) {
            if (userIds.length > 1){
                return null;
            } else {
                try {
                    return new Profile(server.getProfileById(userIds[0]));

                } catch (ServerError serverError) {
                    serverError.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(Profile result) {
            super.onPostExecute(result);
            if(result == null){
                context.onError("Server Error");
            } else {
                // onSuccess()?
                context.onGetProfileById(result);
            }
        }
    }

    private class GetFavoritesByUserIdTask extends AsyncTask<String, Void, List<Trip>> {
        /** Application Context*/
        private TripsSubscriber context;

        private GetFavoritesByUserIdTask(TripsSubscriber context) {
            this.context = context;
        }

        @Override
        protected List<Trip> doInBackground(String ... userIds) {
            if (userIds.length > 1){
                return null;
            } else {
                try {
                    List<TripData> favoritesData = server.getFavoritesByUserId(userIds[0]);
                    List<Trip> favorites = new ArrayList<>();

                    for(TripData t: favoritesData){
                        favorites.add(new Trip(t, locationBuilder));
                    }
                    return favorites;
                } catch (ServerError serverError) {
                    serverError.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<Trip> result) {
            super.onPostExecute(result);
            if(result == null){
                context.onError("No favorites found");
            } else {
                // onSuccess()?
                context.onGetFavoritesByUserId(result);
            }
        }
    }

    private class GetRecommendedTripsByUserIdTask extends AsyncTask<String, Void, List<Trip>> {
        /** Application Context*/
        private OnRecommendedTripsListener context;

        private GetRecommendedTripsByUserIdTask(OnRecommendedTripsListener context) {
            this.context = context;
        }

        @Override
        protected List<Trip> doInBackground(String ... userIds) {
            if (userIds.length > 1){
                return null;
            } else {
                try {
                    List<TripData> recommendationsData = server.getRecommendedTripsByUserId(userIds[0]);
                    List<Trip> recommendedTrips = new ArrayList<>();

                    for(TripData t: recommendationsData){
                        recommendedTrips.add(new Trip(t, locationBuilder));
                    }
                    return recommendedTrips;
                } catch (ServerError serverError) {
                    serverError.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<Trip> result) {
            super.onPostExecute(result);
            if(result == null){
//                context.onError("No favorites found");
            } else {
                // onSuccess()?
                context.onRecommendedTrips(result);
            }
        }
    }

    public class ToggleFavoritesParams{
        public final String userId;
        public final Integer tripId;
        public final Boolean addFavorite;

        public ToggleFavoritesParams(String userId, Integer tripId, Boolean addFavorite) {
            this.userId = userId;
            this.tripId = tripId;
            this.addFavorite = addFavorite;
        }
    }

    private class ToggleFavoriteTask extends AsyncTask<ToggleFavoritesParams, Void, Boolean> {
        /** Application Context*/
        private TripsSubscriber context;

        private ToggleFavoriteTask(TripsSubscriber context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(ToggleFavoritesParams ... params) {
            if (params.length > 1){
                return false;
            } else {
                try {
                    ToggleFavoritesParams params1 = params[0];
                    if(params1.userId.equals(currentUser.userId)){
                        currentUser.toggleFavorite(params1.tripId, params1.addFavorite);
                    }
                    server.toggleFavorite(params1.userId, params1.tripId, params1.addFavorite);
                } catch (ServerError err){
                    err.printStackTrace();
                    return false;
                }
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(!result){
                context.onError("No trip found with corresponding id");
            }
            else {
                 context.onSuccess("Favorite Toggled");
            }
        }
    }

    private class SearchTripsTask extends AsyncTask<String, Void, List<Trip>> {
        /** Application Context*/
        private OnSearchTripsListener context;

        private SearchTripsTask(OnSearchTripsListener context) {
            this.context = context;
        }

        @Override
        protected List<Trip> doInBackground(String ... params) {
            if (params.length > 1){
                return null;
            } else {
                try {
                    List<TripData> tripData = server.searchTrips(params[0]);
                    List<Trip> trips = new ArrayList<>();
                    for(TripData trip: tripData){
                        trips.add(new Trip(trip, locationBuilder));
                    }
                    return trips;
                } catch (ServerError err){
                    err.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<Trip> results) {
            super.onPostExecute(results);
            if(results == null){
                //                context.onError("Error occurred");
            }
            else {
                context.onSearchTripsResults(results);
            }
        }
    }

    private class GetNewsFeedTask extends AsyncTask<String, Void, List<FeedEvent>> {
        /** Application Context*/
        private OnGetNewsFeedListener context;

        private GetNewsFeedTask(OnGetNewsFeedListener context) {
            this.context = context;
        }

        @Override
        protected List<FeedEvent> doInBackground(String ... params) {
            if (params.length > 1){
                return null;
            } else {
                try {
                    List<FeedEventData> feedData = server.getNewsFeed(params[0]);
                    List<FeedEvent> feed = new ArrayList<>();
                    for(FeedEventData event: feedData){
                        if(event instanceof ReviewEventData){
                            feed.add(new ReviewEvent((ReviewEventData) event));
                        } else if(event instanceof AddFavoriteEventData){
                            feed.add(new AddFavoriteEvent((AddFavoriteEventData) event, locationBuilder));
                        } else if(event instanceof CreatedTripEventData){
                            feed.add(new CreatedTripEvent((CreatedTripEventData) event, locationBuilder));
                        }
                    }
                    return feed;
                } catch (ServerError serverError) {
                    serverError.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<FeedEvent> results) {
            super.onPostExecute(results);
            if(results == null){
                //                context.onError("Error occurred");
            }
            else {
                context.onGetNewsFeed(results);
            }
        }
    }
}
