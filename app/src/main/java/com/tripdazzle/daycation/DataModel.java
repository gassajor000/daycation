package com.tripdazzle.daycation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.tripdazzle.daycation.models.Trip;
import com.tripdazzle.server.ProxyServer;
import com.tripdazzle.server.ServerError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataModel {
    private ProxyServer server = new ProxyServer();

    public void initialize(Context context){
        String localFilesDir = context.getFilesDir().getAbsolutePath();
        server.setDbLocation(localFilesDir);

        // copy demo images over
        Integer[] images = {R.drawable.mission_bay};
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
        new GetImageByIdTask(callback).execute(imageId);
    }

    public void getTripById(int tripId, TripsSubscriber callback){
        new GetTripByIdTask(callback).execute(tripId);
    }

    /* Asynchronous Tasks*/
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
         * @param trip Trip returned by query*/
        void onGetTripById(Trip trip);
    }

    public interface ImagesSubscriber extends TaskContext {
        /** called on fetch of an image by id
         * @param image Bitmap returned by query
         * @param imageId Id of the image fetched
         * */
        void onGetImageById(Bitmap image, Integer imageId);
    }

    private class GetTripByIdTask extends AsyncTask<Integer, Void, Trip> {
        /** Application Context*/
        private TripsSubscriber context;

        private GetTripByIdTask(TripsSubscriber context) {
            this.context = context;
        }

        @Override
        protected Trip doInBackground(Integer ... tripIds) {
            if (tripIds.length > 1){
                return null;
            } else {
                return new Trip(server.getTripById(tripIds[0]));
            }
        }

        @Override
        protected void onPostExecute(Trip result) {
            super.onPostExecute(result);
            if(result == null){
                context.onError("No trip found with corresponding id");
            }
            else {
                // onSuccess()?
                context.onGetTripById(result);
            }
        }
    }

    private class GetImageByIdTask extends AsyncTask<Integer, Void, Bitmap> {
        /** Application Context*/
        private ImagesSubscriber context;
        private Integer id;

        private GetImageByIdTask(ImagesSubscriber context) {
            this.context = context;
            this.id = -1;
        }

        @Override
        protected Bitmap doInBackground(Integer ... imageIds) {
            if (imageIds.length > 1){
                return null;
            } else {
                this.id = imageIds[0];
                try {
                    return BitmapFactory.decodeStream(server.getImageById(imageIds[0]));
                } catch (ServerError serverError) {
                    serverError.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if(id == -1){
                context.onError("Invalid image id");
            } else if(result == null){
                context.onError("No image found with id " + id);
            } else {
                // onSuccess()?
                context.onGetImageById(result, id);
            }
        }
    }
}
