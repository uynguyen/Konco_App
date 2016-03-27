package com.example.turbo.konco;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Request;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Response;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.ResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by LeeSan on 3/26/2016.
 */
public class BluemixBus {
    Context _context;

    public BluemixBus(Context context) {
        _context = context;
    }

    public boolean postUserPost(JSONObject postObj) {

        PostUserPostAsyncTask asyn = new PostUserPostAsyncTask();
        asyn.execute(postObj);

        return true;
    }

    public JSONObject getPostItem(Integer index) {
        JSONObject result = new JSONObject();

        GetPostITemAsyncTask asyn = new GetPostITemAsyncTask(_context);
        asyn.execute(index);


        return result;
    }
}
