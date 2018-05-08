package com.grupppofigo.progettocinema.prenotazione_posti;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SessionValidator;
import com.grupppofigo.progettocinema.login.LoginActivity;
import com.grupppofigo.progettocinema.queries.PostoPrenotatoQueries;
import com.grupppofigo.progettocinema.queries.PrenotazioneQueries;
import com.grupppofigo.progettocinema.queries.SalaQueries;
import com.grupppofigo.progettocinema.entities.PostoPrenotato;
import com.grupppofigo.progettocinema.entities.Prenotazione;
import com.grupppofigo.progettocinema.entities.Sala;
import com.grupppofigo.progettocinema.queries.SessioneQueries;
import com.grupppofigo.progettocinema.riassunto.ResumeActivity;

import java.util.ArrayList;

import static com.grupppofigo.progettocinema.helpers.ExtrasDefinition.EXTRA_DEFAULT_VALUE;
import static com.grupppofigo.progettocinema.prenotazione_posti.PostiAdapter.POSTO_LIBERO;
import static com.grupppofigo.progettocinema.prenotazione_posti.PostiAdapter.POSTO_PRENOTATO;

public class PostiActivity extends AppCompatActivity {
    /**
     * Span del grid della lista dei posti
     */
    private static final int GRID_SPAN_COUNT = 8;

    /**
     * Gestione della prenotazione
     */
    private ArrayList<Integer> postiDaPrenotare;

    /**
     * Extras passati
     */
    private int idProgrammazione, idUtente;
    private long idSessione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posti);

        // id sessione
        idSessione = getIntent().getLongExtra(ExtrasDefinition.ID_TOKEN, EXTRA_DEFAULT_VALUE);
        if (idSessione == EXTRA_DEFAULT_VALUE) {
            SessionValidator.finishSession(this, idSessione);
        }

        // start della sessione
        final String startSession = getIntent().getStringExtra(ExtrasDefinition.START_SESSION);
        if (startSession == null) {
            SessionValidator.finishSession(this, idSessione);
        } else if (SessionValidator.isExpired(startSession)) {
            // se è scaduta la registro e chiudo tutto
            SessioneQueries.endSession(idSessione);
            SessionValidator.finishSession(this, idSessione);
        }

        // id della programmazione passata dall'activity prima
        idProgrammazione = getIntent().getIntExtra(ExtrasDefinition.ID_PROGRAMMAZIONE, EXTRA_DEFAULT_VALUE);
        if (idProgrammazione == EXTRA_DEFAULT_VALUE) {
            // errore idProgrammazione non passata
            SessionValidator.finishSession(this, idSessione);
        }

        // id dell'utente passata dall'activity prima
        idUtente = getIntent().getIntExtra(ExtrasDefinition.ID_UTENTE, EXTRA_DEFAULT_VALUE);
        if (idUtente == EXTRA_DEFAULT_VALUE) {
            // errore idUtente non passato passo al login
            SessionValidator.finishSession(this, idSessione);
        }

        // prendo i riferimenti
        final Button mBtnAvanti = findViewById(R.id.btnAvanti);
        RecyclerView r = findViewById(R.id.mainR);

        postiDaPrenotare = new ArrayList<>();

        // sala associata
        final Sala currentSala = SalaQueries.getSalaFromId(idProgrammazione);

        // posti già prenotati per quella programmazione
        final ArrayList<Integer> postiPrenotati = PostoPrenotatoQueries.getPostiPrenotati(idProgrammazione);

        // recyclerview set up
        GridLayoutManager recyclerGrid = new GridLayoutManager(getApplicationContext(), GRID_SPAN_COUNT);
        r.setLayoutManager(recyclerGrid);
        r.setAdapter(new PostiAdapter(currentSala, this, postiDaPrenotare, postiPrenotati));

        // per non far reciclare gli oggetti togglabili
        r.getRecycledViewPool().setMaxRecycledViews(POSTO_PRENOTATO, 0);
        r.getRecycledViewPool().setMaxRecycledViews(POSTO_LIBERO, 0);

        //btn avanti --> registro la prenotazione nel database
        mBtnAvanti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (postiDaPrenotare.size() == 0) {
                    Snackbar snack = Snackbar.make(findViewById(R.id.main_container),
                            R.string.error_posti_min, Snackbar.LENGTH_LONG);
                    snack.show();
                    return;
                }

                // faccio partire l'activity di riassunto
                Intent riassunto = new Intent(getApplicationContext(), ResumeActivity.class);
                riassunto.putExtra(ExtrasDefinition.START_SESSION, startSession);
                riassunto.putExtra(ExtrasDefinition.ID_UTENTE, idUtente);
                riassunto.putExtra(ExtrasDefinition.ID_TOKEN, idSessione);
                riassunto.putExtra(ExtrasDefinition.ID_PROGRAMMAZIONE, idProgrammazione);
                riassunto.putIntegerArrayListExtra(ExtrasDefinition.POSTI_PRENOTARE, postiDaPrenotare);
                startActivity(riassunto);
            }
        });
    }
}
