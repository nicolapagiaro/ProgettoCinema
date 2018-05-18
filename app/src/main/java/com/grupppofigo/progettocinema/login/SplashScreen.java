package com.grupppofigo.progettocinema.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SharedPrefHelper;
import com.grupppofigo.progettocinema.lista_film.MainActivity;
import com.grupppofigo.progettocinema.queries.SessioneQueries;

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
        int idUtente = SharedPrefHelper.with(getApplicationContext()).getUser();
        if(SharedPrefHelper.USER_NOT_LOGGED == idUtente) {
            // parto col login/register
            Intent vToLoginPage = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(vToLoginPage);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        else {
            // parto con la main activity
            long startTime = System.currentTimeMillis();
            long token = SessioneQueries.startSession(idUtente, startTime);

            // faccio partire l'altra activity
            Intent postLoginAct = new Intent(SplashScreen.this, MainActivity.class);
            postLoginAct.putExtra(ExtrasDefinition.START_SESSION, String.valueOf(startTime));
            postLoginAct.putExtra(ExtrasDefinition.ID_TOKEN, token);
            postLoginAct.putExtra(ExtrasDefinition.ID_UTENTE, idUtente);
            startActivity(postLoginAct);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }
}
