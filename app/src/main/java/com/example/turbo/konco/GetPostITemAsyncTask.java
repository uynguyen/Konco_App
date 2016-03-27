package com.example.turbo.konco;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Request;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Response;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.ResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.PostItemModel;

/**
 * Created by LeeSan on 3/27/2016.
 */
public class GetPostITemAsyncTask extends AsyncTask<Integer, PostItemModel, Void> {
    Context _context;

    public GetPostITemAsyncTask(Context cx) {
        this._context = cx;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        Request request = new Request("/api/post/" + integers[0], Request.GET);

        request.send(_context, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                try {
                    JSONObject responseObj = new JSONObject(response.getResponseText()).getJSONObject("post");
                    PostItemModel model = new PostItemModel();


                    model.set_title(responseObj.getString("title"));
                    model.set_content(responseObj.getString("content"));
                    model.set_votes(responseObj.getString("num_vote"));

                    DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
                    Date tmp = m_ISO8601Local.parse(responseObj.getString("datepost"));
                    model.set_postDate(tmp);
                    ArrayList<String> cmts = new ArrayList<String>();
                    for (int j = 0; j < responseObj.getJSONArray("Comments").length(); j++) {
                        cmts.add(responseObj.getJSONArray("Comments").getJSONObject(j).getString("content"));
                    }

                    model.set_comments(cmts);
                    model.set_urlAvatar(responseObj.getJSONObject("User").getString("avatarUrl"));
                    model.set_userName(responseObj.getJSONObject("User").getString("fullname"));



                    publishProgress(model);


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
    protected void onProgressUpdate(PostItemModel... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        PostItemModel model = values[0];
        try {

            TextView title = (TextView) ((Activity) _context).findViewById(R.id.row_post).findViewById(R.id.txt_nameFriend);
            title.setText(model.get_title());


            TextView content = (TextView) ((Activity) _context).findViewById(R.id.row_post).findViewById(R.id.txt_PostContent);
            content.setText(model.get_content());

            TextView fullname = (TextView) ((Activity) _context).findViewById(R.id.row_post).findViewById(R.id.txt_nameUser);
            fullname.setText("Được đăng bởi " +model.get_userName());

            try{

                TextView date = (TextView) ((Activity) _context).findViewById(R.id.row_post).findViewById(R.id.txt_datePost);
                date.setText("Lúc " + (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(model.get_postDate())));
            }catch (Exception e){
                Log.d("===F ERR == ", e.toString());
            }


            Button voteButton = (Button) ((Activity) _context).findViewById(R.id.row_post).findViewById(R.id.btn_vote);
            voteButton.setText(model.get_votes());

            Button commentButton = (Button) ((Activity) _context).findViewById(R.id.row_post).findViewById(R.id.btn_comment);
            commentButton.setText(model.get_comments().size() + "");

            ListView lst_cmts = (ListView) ((Activity) _context).findViewById(R.id.lst_comments);

            ArrayAdapter adapter = new ArrayAdapter((Activity) _context,
                    android.R.layout.simple_list_item_1, model.get_comments());
            lst_cmts.setAdapter(adapter);

            GetImageAsyncTask asyn = new GetImageAsyncTask((Activity)_context,((Activity) _context).findViewById(R.id.row_post).findViewById(R.id.img_avatarUser), new ArrayList<Bitmap>() );
            asyn.execute(model.get_urlAvatar());


        } catch (Exception e) {
            Log.d("Row ==== err", e.toString());
        }

    }

    // Hàm chạy cuối cùng của tiểu trình
    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

    }
}
