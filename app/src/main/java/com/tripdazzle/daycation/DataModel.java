package com.tripdazzle.daycation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.tripdazzle.daycation.models.Profile;
import com.tripdazzle.daycation.models.Review;
import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.server.ProxyServer;
import com.tripdazzle.server.ServerError;
import com.tripdazzle.server.datamodels.ReviewData;
import com.tripdazzle.server.datamodels.TripData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataModel {
    private ProxyServer server = new ProxyServer();

    public void initialize(Context context){
        String localFilesDir = context.getFilesDir().getAbsolutePath();
        server.setDbLocation(localFilesDir);

        // copy demo images over
        Integer[] images = {R.drawable.mission_bay, R.drawable.mscott, R.drawable.jhalpert};
        for(Integer i: images){
            copyResources(context, localFilesDir, i);
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
    }

    public interface ImagesSubscriber extends TaskContext {
        /** called on fetch of an image by id
         * @param images Bitmap returned by query
         * @param imageIds Id of the image fetched */
        void onGetImagesById(List<Bitmap> images, List<Integer> imageIds);
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

    private class GetImagesByIdsTask extends AsyncTask<List<Integer>, Void, List<Bitmap>> {
        /** Application Context*/
        private ImagesSubscriber context;
        private List<Integer> ids;

        private GetImagesByIdsTask(ImagesSubscriber context) {
            this.context = context;
            this.ids = new ArrayList<>();
        }

        @Override
        protected List<Bitmap> doInBackground(List<Integer> ... imageIds) {
            if (imageIds.length > 1){
                return null;
            } else {
                this.ids = imageIds[0];
                try {
                    List<InputStream> imageData = server.getImagesById(imageIds[0]);
                    List<Bitmap> images = new ArrayList<>();
                    for (InputStream s : imageData) {
                        images.add(BitmapFactory.decodeStream(s));
                    }
                    return images;
                } catch (ServerError serverError) {
                    serverError.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<Bitmap> result) {
            super.onPostExecute(result);
            if(ids.size() == 0){
                context.onError("No ids provided");
            } else if(result == null){
                context.onError("No image found with id " + ids);
            } else if (result.size() != ids.size()){
                context.onError(String.format("Incorrect number of images returned! Expecting %d, got %d", ids.size(), result.size()));
            } else {
                // onSuccess()?
                context.onGetImagesById(result, ids);
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
}
