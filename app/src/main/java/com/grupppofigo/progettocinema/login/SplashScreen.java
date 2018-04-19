package com.grupppofigo.progettocinema.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.grupppofigo.progettocinema.R;

public class SplashScreen extends AppCompatActivity {
    private static final int DELAY_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // nascondo la Support Action Bar se c'è
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // finto caricamento
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();
                finish();
            }
        }, DELAY_TIME);
    }

    private void startApp(){
        Intent vToLoginPage = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(vToLoginPage);
    }
}
