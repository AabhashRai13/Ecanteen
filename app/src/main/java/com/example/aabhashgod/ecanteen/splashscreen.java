package com.example.aabhashgod.ecanteen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class splashscreen extends AppCompatActivity {

    public static int SPLASH_TIME = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        splashScreen();
    }

    public void splashScreen() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent startWelcomeActivity = new Intent(splashscreen.this, MainActivity.class );
                startActivity(startWelcomeActivity);
                finish();

            }
        }, SPLASH_TIME);


    }
}
