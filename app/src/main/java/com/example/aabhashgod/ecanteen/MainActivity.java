package com.example.aabhashgod.ecanteen;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 0;
    private static int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            String loggedUserName;
            loggedUserName = auth.getCurrentUser().getDisplayName();
            Toast.makeText(this, "You are SignedIn as " + auth.getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(mainIntent);
                }
            }, SPLASH_DISPLAY_LENGTH);
        } else {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setTheme(R.style.LoginTheme)

                            .build(),
                    RC_SIGN_IN);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                //user is loggedIn
                Log.d("MainActivity", "onActivityResult: " + auth.getCurrentUser().getEmail());
                Intent startUserConfirmation = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(startUserConfirmation);

            } else {
                //user not Authenticated
                Log.d("MainActivity", "User not LoggedIn");
            }
        }
    }

}


