package com.grupppofigo.progettocinema.riassunto;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.entities.Programmazione;
import com.grupppofigo.progettocinema.entities.Sala;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SessionValidator;
import com.grupppofigo.progettocinema.login.LoginActivity;
import com.grupppofigo.progettocinema.queries.FilmQueries;
import com.grupppofigo.progettocinema.queries.PostoPrenotatoQueries;
import com.grupppofigo.progettocinema.queries.ProgrammazioneQueries;
import com.grupppofigo.progettocinema.queries.SalaQueries;
import com.grupppofigo.progettocinema.queries.SessioneQueries;

import java.util.ArrayList;

import static com.grupppofigo.progettocinema.helpers.ExtrasDefinition.EXTRA_DEFAULT_VALUE;

public class ResumeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        // id sessione
        int idSessione = getIntent().getIntExtra(ExtrasDefinition.ID_TOKEN, EXTRA_DEFAULT_VALUE);
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

        // id della programmazione
        int idProgrammazione = getIntent().getIntExtra(ExtrasDefinition.ID_PROGRAMMAZIONE, EXTRA_DEFAULT_VALUE);
        /*if(idProgrammazione == EXTRA_DEFAULT_VALUE) {
            // errore idProgrammazione non passata
           finishSession();
        }*/

        // id dell'utente passata dall'activity prima
        int idUtente = getIntent().getIntExtra(ExtrasDefinition.ID_UTENTE, EXTRA_DEFAULT_VALUE);
        if(idUtente == EXTRA_DEFAULT_VALUE) {
            // errore idUtente non passato passo al login
            finishSession();
        }

        // riempio lo schermo con i dati
        TextView tvTitolo = findViewById(R.id.tvTitolo);
        TextView tvGenere = findViewById(R.id.tvGenere);
        TextView tvDurata = findViewById(R.id.tvDurata);
        TextView tvData = findViewById(R.id.tvData);
        TextView tvOra = findViewById(R.id.tvOrario);

        Programmazione pr = ProgrammazioneQueries.getProgrammmazione(idProgrammazione);
        Film  film = FilmQueries.getFilm(pr.getIdFilm());
        Sala s = SalaQueries.getSala(pr.getIdSala());
        ArrayList<Integer> posti = PostoPrenotatoQueries.getPostiPrenotati(pr.getId());

        if(film != null) {
            tvTitolo.setText(film.getTitolo());
            tvGenere.setText(film.getGenere().getNome());
            tvDurata.setText(getString(R.string.tvDurataFilm, film.getDurata()));
            tvData.setText(pr.getData());
            tvOra.setText(pr.getOra());
        }

        // listview con i posti
        ListView lista = findViewById(R.id.list);
        CustomListView customListView = new CustomListView(this, R.layout.resume_posto_item, posti, s);
        lista.setAdapter(customListView);
    }

    /**
     * Metodo che fa finire la sessione e riporta al LOGIN
     */
    private void finishSession() {
        Snackbar snack = Snackbar.make(findViewById(R.id.resumeContainer),
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
