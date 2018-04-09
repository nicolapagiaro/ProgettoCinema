package com.grupppofigo.progettocinema;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.grupppofigo.progettocinema.database.Queries;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.entities.Sala;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Lista dei film", Queries.getAllFilms().toString());
        Log.d("Lista delle sale", Queries.getAllSalas().toString());
    }
}
