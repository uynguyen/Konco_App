package com.example.turbo.konco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import models.PostItemModel;

public class PostItem extends AppCompatActivity {
    BluemixBus _blueBus = new BluemixBus(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);


        Integer index = getIntent().getIntExtra("data", 0);

        Log.d("=================", index + "");

        JSONObject item = _blueBus.getPostItem(index);




    }
}
