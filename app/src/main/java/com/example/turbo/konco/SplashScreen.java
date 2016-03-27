package com.example.turbo.konco;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.concurrent.atomic.AtomicBoolean;
public class SplashScreen extends AppCompatActivity implements View.OnClickListener{
    private LoginButton btn_loginFB;
    private ProgressBar progress;
    private CallbackManager mCallBackManager;
    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

//            if (profile != null) {
//                Toast.makeText(getApplicationContext(), "Hello " + profile.getName() + ", you has logged in.", Toast.LENGTH_SHORT).show();
//                Log.d("Facebook ====", profile.getName());
//                PostUserPostAsyncTask asyn = new PostUserPostAsyncTask();
//                asyn.execute();

                Intent t = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(t);
                finish();
            //}

        }

        @Override
        public void onCancel() {
            Log.d("Facebook ====", "Cancel");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("Facebook ====", error.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_splash_screen);

        mCallBackManager = CallbackManager.Factory.create();


        btn_loginFB = (LoginButton) findViewById(R.id.btn_login_fb);

        /////////
        btn_loginFB.setReadPermissions("user_friends");
        btn_loginFB.registerCallback(mCallBackManager, mCallBack);
        ////

        btn_loginFB.setOnClickListener(this);
        btn_loginFB.setVisibility(View.INVISIBLE);


        progress = (ProgressBar) findViewById(R.id.progress);

        showSplashGreen();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_fb:
                break;
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    AtomicBoolean ab = new AtomicBoolean(false);
    // khai báo Handler để quản lý Main Thread
    Handler hangido = new Handler();

    private void showSplashGreen() {

        ab.set(false);
        // tạo tiến trình con
        Thread thCon = new Thread(new Runnable() {

            @Override
            public void run() {
                SystemClock.sleep(2000);
                // gọi phương thức post để gửi message ra main Thread

                hangido.post(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        progress.setVisibility(View.INVISIBLE);
                        btn_loginFB.setVisibility(View.VISIBLE);

                    }
                });

                hangido.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // gọi hàm hiển thị kết thúc ở Main Thread


                    }
                });
            }
        });
        ab.set(true);

        thCon.start();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
