package com.websoftquality.meetfriends.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.websoftquality.meetfriends.R;
import com.websoftquality.meetfriends.Utils.Apierror_handle;
import com.websoftquality.meetfriends.Utils.AppController;
import com.websoftquality.meetfriends.Utils.Loading;
import com.websoftquality.meetfriends.Utils.MessageToast;
import com.websoftquality.meetfriends.Utils.Myconstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_login_btn,tv_signUp;
    EditText et_email,et_paswrd;
    MessageToast messageToast;
    Loading loading;
    Apierror_handle apierror_handle;
    LinearLayout ll_forgot_password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        img_back=findViewById(R.id.img_back);
        et_email=findViewById(R.id.et_email);
        et_paswrd=findViewById(R.id.et_paswrd);
        tv_login_btn=findViewById(R.id.tv_login_btn);
        ll_forgot_password=findViewById(R.id.ll_forgot_password);
        tv_signUp=findViewById(R.id.tv_signUp);
        messageToast=new MessageToast(this);
        loading=new Loading(this);
        apierror_handle=new Apierror_handle(this);
        tv_login_btn.setOnClickListener(this);
        ll_forgot_password.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);
        img_back.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(Myconstant.SharedPrefrence, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.ll_forgot_password:
                Intent intentPassword=new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                startActivity(intentPassword);
                break;
            case R.id.tv_signUp:
                Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_login_btn:
                validations();
                break;
        }
    }

    private void validations() {
        if (et_email.getText().toString().isEmpty()){
            messageToast.showDialog("Please Enter Email");
        }
        else if (et_paswrd.getText().toString().isEmpty()){
            messageToast.showDialog("Please Enter Password");
        }else if (et_paswrd.getText().toString().length()<8){
            messageToast.showDialog("Password should be atleast 8 digits");
        }
        else {
            api_login();
        }
    }


    private void api_login() {
        loading.showDialog();
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST, Myconstant.Root_url+Myconstant.LoginApi, response -> {
            try{
                loading.hideDialog();
                final JSONObject jsonObject = new JSONObject(response);
                Log.e("TAG", "onResponse: "+jsonObject);
                if (jsonObject.getString("status_code").equals("1"))
                {

                    messageToast.showDialog("Login Successful");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                                editor.putString(Myconstant.UserId,jsonObject.getJSONObject("user_details").getString("user_id"));
                                editor.putString(Myconstant.FirstName,jsonObject.getJSONObject("user_details").getString("first_name"));
                                editor.putString(Myconstant.LastName,jsonObject.getJSONObject("user_details").getString("last_name"));
                                editor.putString(Myconstant.AccessToken,jsonObject.getString("access_token"));
                                editor.apply();
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            finish();
                        }
                    },2900);
                }
                else
                {
                    loading.hideDialog();
                    messageToast.showDialog(jsonObject.getString("status_message"));

                }
            }
            catch(Exception e) {
                Log.e("TAG", "onResponse: "+e.getMessage());
                loading.hideDialog();
                e.printStackTrace();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e("TAG", "onResponse: "+error.getMessage());
                loading.hideDialog();

                try
                {
                    apierror_handle.get_error(error);
                }catch (Exception e)
                {
                    Log.e("TAG", "onErrorResponse: " + e.getMessage());
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("user_name", et_email.getText().toString());
                params.put("password", et_paswrd.getText().toString());
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}