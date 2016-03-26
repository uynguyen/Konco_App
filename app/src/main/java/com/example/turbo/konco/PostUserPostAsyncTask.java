package com.example.turbo.konco;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LeeSan on 3/26/2016.
 */
public class PostUserPostAsyncTask extends AsyncTask<JSONObject, Void, Void> {
    @Override
    protected Void doInBackground(JSONObject... jsonObjects) {

        try {
            String feedbackAPIURL = "http://api-konco.mybluemix.net/api/post";
            Log.d("========URL ", feedbackAPIURL);
            URL url = new URL(feedbackAPIURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(jsonObjects[0].toString().getBytes());
            os.flush();

            StringBuilder sb = new StringBuilder();
            int HttpResult = conn.getResponseCode();

            if (HttpResult == HttpURLConnection.HTTP_OK) {

                Log.d("******", "Success");

            } else {
                Log.d("*******", conn.getResponseMessage());
            }

        } catch (Exception e) {
            Log.d("===========MyApp", e.toString());
        }
        return null;
    }
}
