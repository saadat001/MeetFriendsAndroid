package com.websoftquality.meetfriends.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.websoftquality.meetfriends.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_login,tv_create_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_login=findViewById(R.id.tv_login);
        tv_create_account=findViewById(R.id.tv_create_account);
        tv_login.setOnClickListener(this);
        tv_create_account.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login:
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_create_account:
                Intent intent1=new Intent(this,SignupActivity.class);
                startActivity(intent1);
                break;
        }
    }
}