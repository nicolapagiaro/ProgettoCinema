package com.grupppofigo.progettocinema.riassunto;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.entities.PostoPrenotato;
import com.grupppofigo.progettocinema.entities.Prenotazione;
import com.grupppofigo.progettocinema.entities.Programmazione;
import com.grupppofigo.progettocinema.entities.Sala;
import com.grupppofigo.progettocinema.helpers.DateParser;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SessionValidator;
import com.grupppofigo.progettocinema.lista_film.MainActivity;
import com.grupppofigo.progettocinema.queries.FilmQueries;
import com.grupppofigo.progettocinema.queries.PostoPrenotatoQueries;
import com.grupppofigo.progettocinema.queries.PrenotazioneQueries;
import com.grupppofigo.progettocinema.queries.ProgrammazioneQueries;
import com.grupppofigo.progettocinema.queries.SalaQueries;
import com.grupppofigo.progettocinema.queries.SessioneQueries;

import java.text.ParseException;
import java.util.ArrayList;

import static com.grupppofigo.progettocinema.helpers.ExtrasDefinition.EXTRA_DEFAULT_VALUE;

public class ResumeActivity extends AppCompatActivity {

    ArrayList<Integer> posti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        // id sessione
        long idSessione = getIntent().getLongExtra(ExtrasDefinition.ID_TOKEN, EXTRA_DEFAULT_VALUE);
        if (idSessione == EXTRA_DEFAULT_VALUE) {
            SessionValidator.finishSession(this, idSessione);
        }

        // start della sessione
        String startSession = getIntent().getStringExtra(ExtrasDefinition.START_SESSION);
        if (startSession == null) {
            SessionValidator.finishSession(this, idSessione);
        } else if (SessionValidator.isExpired(startSession)) {
            // se è scaduta la registro e chiudo tutto
            SessioneQueries.endSession(idSessione);
            SessionValidator.finishSession(this, idSessione);
        }

        // id della programmazione
        final int idProgrammazione = getIntent().getIntExtra(ExtrasDefinition.ID_PROGRAMMAZIONE, EXTRA_DEFAULT_VALUE);
        if (idProgrammazione == EXTRA_DEFAULT_VALUE) {
            // errore idProgrammazione non passata
            SessionValidator.finishSession(this, idSessione);
        }

        // id dell'utente passata dall'activity prima
        final int idUtente = getIntent().getIntExtra(ExtrasDefinition.ID_UTENTE, EXTRA_DEFAULT_VALUE);
        if (idUtente == EXTRA_DEFAULT_VALUE) {
            // errore idUtente non passato passo al login
            SessionValidator.finishSession(this, idSessione);
        }

        // id dell'utente passata dall'activity prima
        final long idPrenotazione = getIntent().getLongExtra(ExtrasDefinition.ID_PRENOTAZIONE, EXTRA_DEFAULT_VALUE);
        if (idPrenotazione == EXTRA_DEFAULT_VALUE) {
            // errore idPrenotazione non passato passo al login
            SessionValidator.finishSession(this, idSessione);
        }

        // riempio lo schermo con i dati
        TextView tvTitolo = findViewById(R.id.tvTitolo);
        TextView tvGenere = findViewById(R.id.tvGenere);
        TextView tvDurata = findViewById(R.id.tvDurata);
        TextView tvData = findViewById(R.id.tvData);
        TextView tvOra = findViewById(R.id.tvOrario);
        TextView tvIdSessione = findViewById(R.id.tvId);
        tvIdSessione.setText("" + idSessione);

        // prendo le robe
        Programmazione pr = ProgrammazioneQueries.getProgrammmazione(idProgrammazione);
        if (pr == null) {
            SessionValidator.finishSession(this, idSessione);
        }

        Film film = FilmQueries.getFilm(pr.getIdFilm());
        Sala s = SalaQueries.getSala(pr.getIdSala());
        posti = getIntent().getIntegerArrayListExtra("postiDaPrenotare");
        Log.e("NP",posti.size()+"");
        //posti = PostoPrenotatoQueries.postiPrenotatiByPrenotazione((int) idPrenotazione);

        if (film != null) {
            tvTitolo.setText(film.getTitolo());
            tvGenere.setText(film.getGenere().getNome());
            tvDurata.setText(getString(R.string.tvDurataFilm, film.getDurata()));
            try {
                tvData.setText(DateParser.getFormattedDate(pr.getData()));
            } catch (ParseException e) {
                tvData.setText(pr.getData());
            }
            tvOra.setText(pr.getOra());
        }

        // listview con i posti
        ListView lista = findViewById(R.id.list);
        CustomListView customListView = new CustomListView(this, R.layout.resume_posto_item, posti, s);
        lista.setAdapter(customListView);

        // acquista btn
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {  // finto acquisto con paypal
                // inserisco nel db i posti prenotati
                for (Integer index : posti) {
                    PostoPrenotato p = new PostoPrenotato(0, (int) idPrenotazione, index);
                    PostoPrenotatoQueries.addPostoPrenotato(p);



                }
                Toast.makeText(getApplicationContext(), "Acquistato", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        });
    }
}
