package com.websoftquality.meetfriends.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignupActivity";
    EditText et_first,et_last,et_phone,et_password,et_confirm_password;
    TextView tv_signup_btn,tv_birthday;
    MessageToast messageToast;
    Loading loading;
    Apierror_handle apierror_handle;
    String code;
    DatePickerDialog picker;
    JSONObject jsonObject;
    RadioButton rb_male,rb_female,rb_other;
    String dob;
    CheckBox check_agree;
    ImageView img_back;
    LinearLayout ll_birthday;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ll_birthday=findViewById(R.id.ll_birthday);
        et_first=findViewById(R.id.et_first);
        et_last=findViewById(R.id.et_last);
        img_back=findViewById(R.id.img_back);
        et_phone=findViewById(R.id.et_phone);
        et_password=findViewById(R.id.et_password);
        et_confirm_password=findViewById(R.id.et_confirm_password);
        tv_signup_btn=findViewById(R.id.tv_signup_btn);
        tv_birthday=findViewById(R.id.tv_birthday);
        rb_male=findViewById(R.id.rb_male);
        rb_female=findViewById(R.id.rb_female);
        rb_other=findViewById(R.id.rb_other);
        check_agree=findViewById(R.id.check_agree);
        messageToast=new MessageToast(this);
        loading=new Loading(this);
        apierror_handle=new Apierror_handle(this);
        tv_signup_btn.setOnClickListener(this);
        ll_birthday.setOnClickListener(this);
        img_back.setOnClickListener(this);
        rb_male.setChecked(true);
        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager.getNetworkCountryIso() != null
                && !telephonyManager.getNetworkCountryIso().equals(""))
        {
            code = telephonyManager.getNetworkCountryIso().toUpperCase();
            code= String.valueOf(PhoneNumberUtil.createInstance(this).getCountryCodeForRegion(code));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.ll_birthday:

                datepicker();
                break;
            case R.id.tv_signup_btn:
                Log.e(TAG, "onClick: "+code);
                validations();
                break;
        }
    }

    public void datepicker(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                SignupActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

//                 String date = month + "/" + day + "/" + year;
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat newtargetFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date;
            try {
                date = originalFormat.parse(day + "/" + (month + 1) + "/" + year);
                dob=targetFormat.format(date);

                tv_birthday.setText(newtargetFormat.format(date));
            } catch (Exception ex) {
                Log.e(TAG, "onDateSet: "+ex.getMessage());
                // Handle Exception.
            }
        }
        };
        }


//        picker = new DatePickerDialog(SignupActivity.this, AlertDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
//            @Override public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//
//            }
//            }, year, month, day);
//            picker.show();
//    }

    private void validations() {

        if (et_first.getText().toString().isEmpty()){
            et_first.setError("Please Enter First Name");
        }else if (et_last.getText().toString().isEmpty()){
            et_last.setError("Please Enter Last Name");
        }else if (et_phone.getText().toString().isEmpty()){
            et_phone.setError("Please Enter Email/Phone");
        } else if (et_password.getText().toString().isEmpty()){
            et_password.setError("Please Enter Password");
        } else if (tv_birthday.getText().toString().equalsIgnoreCase("MM/DD/YYYY")){
            messageToast.showDialog("Please Enter Password");
        }else if (!rb_male.isChecked() && !rb_female.isChecked() && !rb_other.isChecked()){
            messageToast.showDialog("Please Choose Gender");
        }else if (et_password.getText().toString().trim().length()<8){
            et_password.setError("Password Should be atleast 8 digits");
        }else if (!isValidPassword(et_password.getText().toString().trim())){
            et_password.setError("Password Must Contain a number,capital letter and a special character");
        }else if (!et_password.getText().toString().trim().equalsIgnoreCase(et_confirm_password.getText().toString().trim())){
            et_password.setError("Your password and confirmation password do not match.");
            et_confirm_password.setError("Your password and confirmation password do not match.");
        }else if (!check_agree.isChecked()){
            messageToast.showDialog("Please Agree Terms & Conditions to continue.");
        }else {
            apivalidation();
        }
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private void apivalidation() {
        loading.showDialog();
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST, Myconstant.Root_url+Myconstant.UsernameApi, response -> {
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
                                Intent intent=new Intent(SignupActivity.this, LoginActivity.class);
                                intent.putExtra("otp",jsonObject.getString("otp"));
                                intent.putExtra("user_name",et_phone.getText().toString());
                                intent.putExtra("first_name",et_first.getText().toString());
                                intent.putExtra("last_name",et_last.getText().toString());
                                intent.putExtra("password",et_password.getText().toString());
                                if (rb_male.isChecked()){
                                    intent.putExtra("gender", "men");
                                }else if (rb_female.isChecked()){
                                    intent.putExtra("gender", "women");
                                }else {
                                    intent.putExtra("gender", "other");
                                }
                                    intent.putExtra("dob",dob);
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
                params.put("user_name", et_phone.getText().toString());
                params.put("country_code", code);
                params.put("first_name", et_first.getText().toString());
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}