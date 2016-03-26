package com.example.turbo.konco;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Request;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Response;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.ResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import models.PostItemModel;

/**
 * Created by LeeSan on 3/26/2016.
 */
public class GetPostAsyncTask extends AsyncTask<Void, CustomNewItems, Void> {
    Activity context;
    ListView view;

    public GetPostAsyncTask(Activity ctx, ListView root) {
        context = ctx;
        view = root;
    }

    // Hàm chạy đầu tiên của tiểu trình
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        Request request = new Request("/api/posts", Request.GET);

        request.send(context, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                try {
                    JSONObject responseObj = new JSONObject(response.getResponseText());
                    JSONArray post_array = responseObj.getJSONArray("posts");
                    ArrayList<String> tempNames = new ArrayList<String>();
                    ArrayList<String> tempAvatars = new ArrayList<String>();
                    ArrayList<String> tempContents = new ArrayList<String>();
                    ArrayList<String> tempVotes = new ArrayList<String>();
                    ArrayList<ArrayList<String>> tempComments = new ArrayList<>();
                    ArrayList<String> tempUsername = new ArrayList<String>();
                    ArrayList<String> tempDatePost = new ArrayList<String>();

                    for (int i = 0; i < post_array.length(); i++) {
                        tempNames.add(post_array.getJSONObject(i).getString("title"));
                        tempContents.add(post_array.getJSONObject(i).getString("content"));
                        tempVotes.add(post_array.getJSONObject(i).getString("num_vote"));
                        tempDatePost.add(post_array.getJSONObject(i).getString("datepost"));
                        ArrayList<String> cmts = new ArrayList<String>();
                        for(int j = 0 ; j <post_array.getJSONObject(i).getJSONArray("Comments").length(); j++ ){
                            cmts.add(post_array.getJSONObject(i).getJSONArray("Comments").getJSONObject(j).getString("content"));
                        }

                        tempComments.add(cmts);
                        tempAvatars.add(post_array.getJSONObject(i).getJSONObject("User").getString("avatarUrl"));
                        tempUsername.add(post_array.getJSONObject(i).getJSONObject("User").getString("fullname"));

                    }

                    CustomNewItems aa = new CustomNewItems(context,
                            tempNames,
                            tempAvatars,
                            tempContents,
                            tempVotes,
                            tempComments,
                            tempUsername,
                            tempDatePost
                    );
                    publishProgress(aa);


                    Log.d("Myapp", "======================================onSuccess :: " + response.getResponseText());
                } catch (Exception e) {
                    Log.d("Myapp", "======================================Exception with :: " + e.getMessage());
                }

            }

            @Override
            public void onFailure(Response response, Throwable t, JSONObject extendedInfo) {
                if (null != t) {
                    Log.d("Myapp", "=============================================onFailure :: " + t.getMessage());
                } else if (null != extendedInfo) {
                    Log.d("Myapp", "==============================onFailure :: " + extendedInfo.toString());
                } else {
                    Log.d("Myapp", "==============================onFailure :: " + response.getResponseText());
                }
            }
        });

        return null;
    }

    @Override
    protected void onProgressUpdate(CustomNewItems... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);

        view.setAdapter(values[0]);


    }

    // Hàm chạy cuối cùng của tiểu trình
    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

    }
}
