package com.websoftquality.meetfriends.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.websoftquality.meetfriends.R;
import com.websoftquality.meetfriends.Utils.Apierror_handle;
import com.websoftquality.meetfriends.Utils.AppController;
import com.websoftquality.meetfriends.Utils.Loading;
import com.websoftquality.meetfriends.Utils.MessageToast;
import com.websoftquality.meetfriends.Utils.Myconstant;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    TextView tv_submit;
    MessageToast messageToast;
    String otp;
    Loading loading;
    Apierror_handle apierror_handle;
    JSONObject jsonObject;
    String user_name;
    EditText et_password,et_confirm_password;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        messageToast=new MessageToast(this);
        loading=new Loading(this);
        apierror_handle=new Apierror_handle(this);
        intent=getIntent();
        user_name=intent.getStringExtra("user_name");
        et_password=findViewById(R.id.et_password);
        img_back=findViewById(R.id.img_back);
        et_confirm_password=findViewById(R.id.et_confirm_password);
        tv_submit=findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }


    private void apiForgotPassword() {
        loading.showDialog();
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST, Myconstant.Root_url+Myconstant.ReplacePasswordApi, response -> {
            try{
                loading.hideDialog();
                jsonObject = new JSONObject(response);
                Log.e("TAG", "onResponse: "+jsonObject);
                if (jsonObject.getString("status_code").equals("1"))
                {

                    Intent intent=new Intent(ResetPasswordActivity.this,LoginActivity.class);
                    intent.putExtra("otp",jsonObject.getString("otp"));
                    startActivity(intent);
                    finish();

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
                params.put("user_name", user_name);
                params.put("password", et_password.getText().toString());
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.tv_submit){
            validations();
        }
        else if (view.getId()==R.id.img_back){
            onBackPressed();
        }
    }

    private void validations() {
        if (et_password.getText().toString().trim().length()<8){
            messageToast.showDialog("Password Should be atleast 8 digits");
        }else if (!et_password.getText().toString().trim().equalsIgnoreCase(et_confirm_password.getText().toString().trim())){
            messageToast.showDialog("Your password and confirmation password do not match.");
        }else if (!isValidPassword(et_password.getText().toString().trim())){
            messageToast.showDialog("Password Must Contain a number,capital letter and a special character");
        }
        else {
            apiForgotPassword();
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
}