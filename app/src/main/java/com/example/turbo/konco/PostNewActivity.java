package com.example.turbo.konco;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

public class PostNewActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_UserPost;
    private EditText txt_UserPostContent, txt_UserPostTitle;
    private BluemixBus _bluBluemixBus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_new);

        txt_UserPostContent = (EditText) findViewById(R.id.txt_UserPostContent);

        txt_UserPostTitle = (EditText) findViewById(R.id.txt_UserPostTitle);
        btn_UserPost = (Button) findViewById(R.id.btn_UserPost);
        btn_UserPost.setOnClickListener(this);

        _bluBluemixBus = new BluemixBus(this);
    }
    @Override
    public void onClick(View v) {
        try
        {
            int id = v.getId();
            Intent data = new Intent();
            switch (id)
            {
                case R.id.btn_UserPost:
                    String title = txt_UserPostTitle.getText().toString();
                    String content = txt_UserPostContent.getText().toString();
                    JSONObject post = new JSONObject();
                    JSONObject tmp = new JSONObject();
                    tmp.put("title", title);
                    tmp.put("content", content);
                    tmp.put("UserId", "2");
                    post.put("post", tmp);
                    _bluBluemixBus.postUserPost(post);
                    finish();

                    break;

                default:break;
            }
        }catch (Exception e){

        }
    }
}
