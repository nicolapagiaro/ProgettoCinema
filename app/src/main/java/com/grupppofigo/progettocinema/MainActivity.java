package com.grupppofigo.progettocinema;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.grupppofigo.progettocinema.database.Queries;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.entities.Programmazione;
import com.grupppofigo.progettocinema.entities.Sala;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Film> f = Queries.getAllFilms();
        ArrayList<Sala> s = Queries.getAllSalas();
        ArrayList<Programmazione> ps = Queries.getAllProgrammaziones();

        Log.d("Lista dei film", f.toString());
        Log.d("Lista delle sale", s.toString());
        Log.d("Lista program", ps.toString());
        Log.d("Program", Queries.getProgrammmazione(0).toString());
    }
}
