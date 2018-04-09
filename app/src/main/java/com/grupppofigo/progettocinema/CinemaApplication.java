package com.grupppofigo.progettocinema;

import android.app.Application;

import com.grupppofigo.progettocinema.database.DataStore;

/**
 * Classe dell'applicazione
 * Created by Nicola on 09/04/2018.
 */
public class CinemaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // init del database
        DataStore.init(getApplicationContext());
    }
}
