package com.a16mb.vehiclemanager.vehiculum_manager;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPageActivity extends AppCompatActivity implements View.OnClickListener {

    //@BindView(R.id.editTextEmail)
    EditText editTextEmail;
    //@BindView(R.id.editTextPassword)
    EditText editTextPassword;
    //@BindView(R.id.textViewSignUp)
    TextView txtLinkSignup;
    
//    @BindView(R.id.textInputLayoutEmail)
//    TextInputLayout textInputLayoutEmail;
//    @BindView(R.id.textInputLayoutPassword)
//    TextInputLayout textInputLayoutPassword;

    AppCompatButton btnLogin;

    RequestQueue requestQueue;
    StringRequest stringRequest;
    ProgressDialog pd;

    UserBean userBean;

    private static final String TAG = "MainActivity";
    String email,password,token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_layout);
        initViews();
    }

    void initViews(){
        //ButterKnife.bind(this);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (AppCompatButton) findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
        pd = new ProgressDialog(this);
        pd.setMessage("\"Patience is All Maters\"");
        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]

       /* Button subscribeButton = (Button) findViewById(R.id.buttonLogin);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("news");
                // [END subscribe_topics]

                // Log and toast
                String msg = getString(R.string.msg_subscribed);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    boolean validate(){
        boolean valid = true;
//        String tempEmail= editTextEmail.getText().toString().trim();
//        String tempPassword = editTextPassword.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter Valid Email Address!");
            valid = false;
        }
        else{
            editTextEmail.setError(null);
//            email = tempEmail;
        }
        if (password.isEmpty() || password.length() < 5 || password.length() > 12){
            editTextPassword.setError("Enter a valid Password of Length 5-12");
        }
        else{
            editTextPassword.setError(null);
//            password = tempPassword;
        }
        return valid;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonLogin){
            if(validate()) {
                token = FirebaseInstanceId.getInstance().getToken();
                userBean = new UserBean(email,password,token);
                insert();
                //String msg = getString(R.string.msg_token_fmt, token);
            }
            else{
                Toast.makeText(this, "Enter Correct Details!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void insert(){
        // Get token
        //token = FirebaseInstanceId.getInstance().getToken();
        String msg = "Token :" + token;
        Log.d(TAG, msg);
        Toast.makeText(LoginPageActivity.this, msg, Toast.LENGTH_SHORT).show();
        stringRequest = new StringRequest(Request.Method.POST, Util.INSERT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            Toast.makeText(LoginPageActivity.this, message, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(LoginPageActivity.this, "Some Exception !", Toast.LENGTH_LONG).show();
                        } finally {
                            pd.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        pd.dismiss();
                        Toast.makeText(LoginPageActivity.this, "Volley Error !" + volleyError, Toast.LENGTH_LONG).show();
                        Log.i("Volley", volleyError.toString());
                        Log.i("Volley", volleyError.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("email", userBean.getEmail());
                map.put("password", userBean.getPassword());
                map.put("firebaseToken", userBean.getFirebaseToken());
                return map;
            }
        };
        pd.show();
        requestQueue.add(stringRequest);    // Execute Request
    }
}
