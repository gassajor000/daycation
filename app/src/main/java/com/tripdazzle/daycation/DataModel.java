package com.tripdazzle.daycation;

import android.content.Context;
import android.os.AsyncTask;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataModel {
    private ProxyServer server = new ProxyServer();
    private User currentUser;

    public void initialize(Context context){
        String localFilesDir = context.getFilesDir().getAbsolutePath();
        server.setDbLocation(localFilesDir);

        // copy demo images over
        Integer[] images = {R.drawable.mission_bay, R.drawable.balboa, R.drawable.lajolla, R.drawable.zoo, R.drawable.mscott, R.drawable.jhalpert};
        for(Integer i: images){
            copyResources(context, localFilesDir, i);
        }

        // Set current User
        try {
            currentUser = new User(server.login("mscott", "password123"));
        } catch (ServerError serverError) {
            serverError.printStackTrace();
        }
    }

    private void copyResources(Context context, String localStorageDir, int resId){
        InputStream in = context.getResources().openRawResource(resId);
        String filename = context.getResources().getResourceEntryName(resId) + ".png";

        File f = new File(localStorageDir, filename);

        if(!f.exists()){
            try {
                FileOutputStream out = new FileOutputStream(new File(localStorageDir, filename));
                byte[] buffer = new byte[1024];
                int len;
                while((len = in.read(buffer, 0, buffer.length)) != -1){
                    out.write(buffer, 0, len);
                }
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public List<FeedEvent> getNewsFeed(String userId){
        try {
            List<FeedEventData> feedData = server.getNewsFeed(userId);
            List<FeedEvent> feed = new ArrayList<>();
            for(FeedEventData event: feedData){
                if(event instanceof ReviewEventData){
                    feed.add(new ReviewEvent((ReviewEventData) event));
                } else if(event instanceof AddFavoriteEventData){
                    feed.add(new AddFavoriteEvent((AddFavoriteEventData) event));
                } else if(event instanceof CreatedTripEventData){
                    feed.add(new CreatedTripEvent((CreatedTripEventData) event));
                }
            }
            return feed;
        } catch (ServerError serverError) {
            serverError.printStackTrace();
            return null;
        }
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
                    trips.add(new Trip(t));
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
                        favorites.add(new Trip(t));
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
                        recommendedTrips.add(new Trip(t));
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
}
