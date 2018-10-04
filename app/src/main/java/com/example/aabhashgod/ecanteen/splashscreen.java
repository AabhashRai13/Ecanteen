package com.example.aabhashgod.ecanteen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class splashscreen extends AppCompatActivity {

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
                    Intent startMenuActivity = new Intent(splashscreen.this, MenuActivity.class);
                    startActivity(startMenuActivity);

                } else {
                    Intent startMainActivity = new Intent(splashscreen.this, MainActivity.class);
                    startActivity(startMainActivity);

                }
            }
        }, SPLASH_TIME);

    }

}


