package com.a16mb.vehiclemanager.vehiculum_manager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        initViews();
    }
    void initViews(){
        Toast.makeText(this, "Splash Activity", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SplashScreenActivity.this, LoginPageActivity.class);
        startActivity(intent);
        finish();
        ProgressDialog pd;
        pd= new ProgressDialog(this);
        pd.setMessage("Loading....");

    }
}
