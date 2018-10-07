package com.example.aabhashgod.ecanteen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    public static int SPLASH_TIME = 2000;
    private FirebaseAuth auth;
    private Context context;
    private ConstraintLayout splahView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        auth = FirebaseAuth.getInstance();
        splahView = findViewById(R.id.splash_view);
        context = getApplicationContext();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (auth.getCurrentUser() != null) {
                    String loggedUserName;
                    loggedUserName = auth.getCurrentUser().getDisplayName();
                    Toast.makeText(context, "Welcome back  " + auth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                    Intent startMenuActivity = new Intent(SplashScreenActivity.this, MenuActivity.class);
                    startActivity(startMenuActivity);

                } else {
                    Intent startMainActivity = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(startMainActivity);

                }
            }
        }, SPLASH_TIME);

    }

}


