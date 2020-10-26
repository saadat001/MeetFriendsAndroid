package com.websoftquality.meetfriends.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "OtpActivity";
    Intent intent;
    EditText edt_otp1,edt_otp2,edt_otp3,edt_otp4;
    TextView tv_verify_btn;
    MessageToast messageToast;
    String otp,user_name,first_name,last_name,gender,dob,password;
    Loading loading;
    Apierror_handle apierror_handle;
    JSONObject jsonObject;
    Handler handler;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        messageToast=new MessageToast(this);
        handler=new Handler();
        tv_verify_btn=findViewById(R.id.tv_verify_btn);
        edt_otp1=findViewById(R.id.edt_otp1);
        edt_otp2=findViewById(R.id.edt_otp2);
        edt_otp3=findViewById(R.id.edt_otp3);
        edt_otp4=findViewById(R.id.edt_otp4);
        img_back=findViewById(R.id.img_back);
        tv_verify_btn.setOnClickListener(this);
        img_back.setOnClickListener(this);
        messageToast=new MessageToast(this);
        loading=new Loading(this);
        apierror_handle=new Apierror_handle(this);
        edt_otp1.addTextChangedListener(new GenericTextWatcher(edt_otp1));
        edt_otp2.addTextChangedListener(new GenericTextWatcher(edt_otp2));
        edt_otp3.addTextChangedListener(new GenericTextWatcher(edt_otp3));
        edt_otp4.addTextChangedListener(new GenericTextWatcher(edt_otp4));
        intent=getIntent();
        otp=intent.getStringExtra("otp");
        user_name=intent.getStringExtra("user_name");
        first_name=intent.getStringExtra("first_name");
        last_name=intent.getStringExtra("last_name");
        password=intent.getStringExtra("password");
        gender=intent.getStringExtra("gender");
        dob=intent.getStringExtra("dob");
        sharedPreferences = getSharedPreferences(Myconstant.SharedPrefrence, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tv_verify_btn:
                validations();

                break;
        }
    }


    private void apisignup() {
        loading.showDialog();
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST, Myconstant.Root_url+Myconstant.SignupApi, response -> {
            try{
                loading.hideDialog();
                final JSONObject jsonObject = new JSONObject(response);
                Log.e("TAG", "onResponse: "+jsonObject);
                if (jsonObject.getString("status_code").equals("1"))
                {

                    messageToast.showDialog("User Signup Successfully");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Intent intent=new Intent(OtpActivity.this, HomeActivity.class);
                                editor.putString(Myconstant.UserId,jsonObject.getString("user_id"));
                                editor.putString(Myconstant.FirstName,jsonObject.getString("first_name"));
                                editor.putString(Myconstant.LastName,jsonObject.getString("last_name"));
                                editor.apply();
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        }, 2900);

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
                Log.e("TAG", "getParams: "+gender);
                Log.e("TAG", "getParams: "+dob);
                SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date;
                try {
                    date = originalFormat.parse(dob);
                    dob=targetFormat.format(date);
                } catch (Exception ex) {
                    Log.e(TAG, "onDateSet: "+ex.getMessage());
                }
                params.put("auth_type", "phone");
                params.put("user_name", user_name);
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("password", password);
                params.put("gender", gender);
                params.put("dob", dob);
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void validations() {
        if ((edt_otp1.getText().toString()+edt_otp2.getText().toString()+edt_otp3.getText().toString()+edt_otp4.getText().toString()).equalsIgnoreCase(otp)){
            apisignup();
        }
        else {
            messageToast.showDialog("Invalid OTP");
        }
    }


    public class GenericTextWatcher implements TextWatcher
    {
        private View view;
        private GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch(view.getId())
            {

                case R.id.edt_otp1:
                    if(text.length()==1)
                        edt_otp2.requestFocus();
                    break;
                case R.id.edt_otp2:
                    if(text.length()==1)
                        edt_otp3.requestFocus();
                    else if(text.length()==0)
                        edt_otp1.requestFocus();
                    break;
                case R.id.edt_otp3:
                    if(text.length()==1)
                        edt_otp4.requestFocus();
                    else if(text.length()==0)
                        edt_otp2.requestFocus();
                    break;
                case R.id.edt_otp4:
                    if(text.length()==0)
                        edt_otp3.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }
}