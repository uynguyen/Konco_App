package com.example.turbo.konco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import models.PostItemModel;

public class PostItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);






        PostItemModel item = (PostItemModel) getIntent().getSerializableExtra("data");

        Log.d("=================", item.get_content() );
    }
}
