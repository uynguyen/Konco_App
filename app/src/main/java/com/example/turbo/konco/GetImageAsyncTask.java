package com.example.turbo.konco;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by LeeSan on 3/26/2016.
 */
public class GetImageAsyncTask extends AsyncTask<String, Bitmap, Void> {
    Activity context;
    View rowView;
    ArrayList<Bitmap> _bitmaps;

    public GetImageAsyncTask(Activity ctx, View root, ArrayList<Bitmap> bitmaps) {
        context = ctx;
        rowView = root;
        _bitmaps = bitmaps;
    }

    // Hàm chạy đầu tiên của tiểu trình
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(String... arg0) {


        Bitmap bm = null;
        try {
            URL url = new URL(arg0[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.connect();
            InputStream is = conn.getInputStream();

            BufferedInputStream bis = new BufferedInputStream(is);
            try {
                bm = BitmapFactory.decodeStream(bis);
                _bitmaps.add(bm);
                publishProgress(bm);
            } catch (OutOfMemoryError ex) {
                bm = null;
            }
            bis.close();
            is.close();

        } catch (Exception e) {
            Log.d("====ROW===ERR", e.toString());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Bitmap... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        try {
            ImageView avatar = (ImageView) rowView.findViewById(R.id.img_avatarUser);
            avatar.setImageBitmap(values[0]);
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
