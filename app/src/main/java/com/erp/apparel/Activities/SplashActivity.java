package com.erp.apparel.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.erp.apparel.Data.Prefs;
import com.erp.apparel.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logintoapp();
            }
        }, SPLASH_TIME_OUT);
    }

    private void logintoapp() {
        boolean alreadyLogin =  Prefs.INSTANCE.isLogin();

        Intent i;
        if (alreadyLogin) {
            i = new Intent(SplashActivity.this, HomeActivity.class);
        } else {
            i = new Intent(SplashActivity.this, LoginActivity.class);
        }

        startActivity(i);
        finish();
    }
}