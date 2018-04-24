package com.grupppofigo.progettocinema.lista_film;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.extras.ExtrasDefinition;
import com.grupppofigo.progettocinema.extras.SessionValidator;
import com.grupppofigo.progettocinema.login.LoginActivity;
import com.grupppofigo.progettocinema.queries.FilmQueries;
import com.grupppofigo.progettocinema.queries.SessioneQueries;

import java.lang.*;
import java.util.ArrayList;

import static com.grupppofigo.progettocinema.extras.ExtrasDefinition.EXTRA_DEFAULT_VALUE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // id sessione
        long idSessione = getIntent().getLongExtra(ExtrasDefinition.ID_TOKEN, EXTRA_DEFAULT_VALUE);
        if(idSessione == EXTRA_DEFAULT_VALUE) {
            finishSession();
        }

        // start della sessione
        String startSession = getIntent().getStringExtra(ExtrasDefinition.START_SESSION);
        if(startSession == null) {
            finishSession();
        }
        else if(SessionValidator.isExpired(startSession)){
            // se è scaduta la registro e chiudo tutto
            SessioneQueries.endSession(idSessione);
            finishSession();
        }

        // id dell'utente passata dall'activity prima
        int idUtente = getIntent().getIntExtra(ExtrasDefinition.ID_UTENTE, EXTRA_DEFAULT_VALUE);
        if(idUtente == EXTRA_DEFAULT_VALUE) {
            // errore idUtente non passato passo al login
            finishSession();
        }

        //gestisco list view e adapter
        ListView lv_film = findViewById(R.id.lv_film);

        final ArrayList<Film> films = FilmQueries.getAllFilms();
        AdapterFilm adapterFilm = new AdapterFilm(MainActivity.this, R.layout.film_list_item, films);
        lv_film.setAdapter(adapterFilm);

        //per andare nell'altra activity
       /* lv_film.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                TextView tv_id = view.findViewById(R.id.tv_id);
                String stringFilm = tv_id.getText().toString();
                idFilm = Integer.parseInt(stringFilm);

                if (tv_id.getText().toString() != "") {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(IDFILM, idFilm); //id film
                    bundle.putInt(IDUTENTE, idUtente);
                    bundle.putString(IDTOKEN, idToken);
                    bundle.putString(STARTSESSION, startSession);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });*/
    }

    /**
     * Metodo che fa finire la sessione e riporta al LOGIN
     */
    private void finishSession() {
        Snackbar snack = Snackbar.make(findViewById(R.id.mainContainer),
                R.string.err_session_finished, Snackbar.LENGTH_LONG);
        snack.show();

        int delayTime = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
                // finisco l'activity
                finish();
            }
        }, delayTime);
    }
}
