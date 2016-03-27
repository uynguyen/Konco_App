package com.example.turbo.konco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import models.PostItemModel;

/**
 * Created by LeeSan on 3/26/2016.
 */
public class CustomNewItems extends ArrayAdapter {

    private Context _context;
    private ArrayList<String> _names;
    private ArrayList<String> _imagesURL;
    private ArrayList<String> _contents;
    private ArrayList<Bitmap> _bitmaps = new ArrayList<>();
    private ArrayList<String> _votes = new ArrayList<>();
    private ArrayList<ArrayList<String>> _comments = new ArrayList<>();
    private ArrayList<String> _usernames = new ArrayList<>();
    private ArrayList<String> _datePost = new ArrayList<>();
    private ArrayList<Integer> _index = new ArrayList<>();

    public CustomNewItems(Context context,ArrayList<Integer> index, ArrayList<String> names, ArrayList<String> avatars,
                          ArrayList<String> contents, ArrayList<String> votes, ArrayList<ArrayList<String>> comments,
                          ArrayList<String> usernames, ArrayList<String> datePost) {
        super(context, R.layout.new_row, names);
        this._index = index;
        this._names = names;
        this._imagesURL = avatars;
        this._contents = contents;
        this._context = context;
        this._votes = votes;
        this._comments = comments;
        this._usernames = usernames;
        this._datePost = datePost;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) _context).getLayoutInflater();
        View rowView = inflater.inflate(R.layout.new_row, null, true);

        if(position >= _bitmaps.size() || _bitmaps.get(position) == null){
            GetImageAsyncTask asyn = new GetImageAsyncTask((Activity) _context, rowView, _bitmaps);
            asyn.execute(_imagesURL.get(position));
        }
        else
        {
            ImageView avatar = (ImageView) rowView.findViewById(R.id.img_avatarUser);
            avatar.setImageBitmap(_bitmaps.get(position));
        }


        TextView name = (TextView) rowView.findViewById(R.id.txt_nameFriend);
        name.setText(_names.get(position));

        TextView content = (TextView) rowView.findViewById(R.id.txt_PostContent);
        content.setText(limitContent(_contents.get(position), 250));

        TextView fullname = (TextView) rowView.findViewById(R.id.txt_nameUser);
        fullname.setText("Được đăng bởi " +_usernames.get(position));

        try{
            DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
            Date tmp = m_ISO8601Local.parse(_datePost.get(position));
            TextView date = (TextView) rowView.findViewById(R.id.txt_datePost);
            date.setText("Lúc " + (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(tmp)));
        }catch (Exception e){
            Log.d("===F ERR == ", e.toString());
        }


        Button voteButton = (Button) rowView.findViewById(R.id.btn_vote);
        voteButton.setText(_votes.get(position));

        Button commentButton = (Button) rowView.findViewById(R.id.btn_comment);
        commentButton.setText(_comments.get(position).size() + "");

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(_context, PostItem.class);

                i.putExtra("data", _index.get(position));

                _context.startActivity(i);

            }
        });

        return rowView;
    }

    private String limitContent(String text, int limit){
        String result = "";
        if (text.length() <= limit) result = text;
        else
        {
          result =  text.substring(0, text.indexOf(" ", limit)) + "...";
        }
        return result;
    }

}
