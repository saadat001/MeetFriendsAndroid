package com.websoftquality.meetfriends.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
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

import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_phone;
    TextView tv_submit_btn;
    MessageToast messageToast;
    Loading loading;
    Apierror_handle apierror_handle;
    JSONObject jsonObject;
    String code;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        et_phone=findViewById(R.id.et_phone);
        tv_submit_btn=findViewById(R.id.tv_submit_btn);
        img_back=findViewById(R.id.img_back);
        messageToast=new MessageToast(this);
        loading=new Loading(this);
        apierror_handle=new Apierror_handle(this);
        tv_submit_btn.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.tv_submit_btn){
            validations();
        }
        else if (view.getId()==R.id.img_back){
            onBackPressed();
        }
    }

    private void validations() {
        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager.getNetworkCountryIso() != null
                && !telephonyManager.getNetworkCountryIso().equals(""))
        {
            code = telephonyManager.getNetworkCountryIso().toUpperCase();
            code= String.valueOf(PhoneNumberUtil.createInstance(this).getCountryCodeForRegion(code));
        }
        if (et_phone.getText().toString().isEmpty()){
            messageToast.showDialog("Please Enter Email/Phone");
        } else {
            apiForgotPassword();
        }
    }

    private void apiForgotPassword() {
        loading.showDialog();
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST, Myconstant.Root_url+Myconstant.ResestPasswordApi, response -> {
            try{
                loading.hideDialog();
                jsonObject = new JSONObject(response);
                Log.e("TAG", "onResponse: "+jsonObject);
                if (jsonObject.getString("status_code").equals("1"))
                {

                    messageToast.showDialog("OTP Sent Successfully");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Intent intent=new Intent(ForgotPasswordActivity.this,OtpResetPasswordActivity.class);
                                intent.putExtra("otp",jsonObject.getString("otp"));
                                intent.putExtra("user_name", et_phone.getText().toString());
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
        }, error -> {
            Log.e("TAG", "onResponse: "+error.getMessage());
            loading.hideDialog();

            try {
                apierror_handle.get_error(error);
            } catch (Exception e) {
                Log.e("TAG", "onErrorResponse: " + e.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("user_name", et_phone.getText().toString());
                params.put("country_code", code);
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}