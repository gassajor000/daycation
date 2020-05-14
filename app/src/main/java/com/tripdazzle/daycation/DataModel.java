package com.tripdazzle.daycation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    public Bitmap getImage(int imageId) throws ServerError {
        return BitmapFactory.decodeStream(server.getImage(imageId));
    }

}
