package com.websoftquality.meetfriends.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.websoftquality.meetfriends.R;
import com.websoftquality.meetfriends.Utils.Myconstant;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences(Myconstant.SharedPrefrence, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        if (sharedPreferences.getString(Myconstant.UserId,"").isEmpty()){

                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                }, 3000);
    }
}