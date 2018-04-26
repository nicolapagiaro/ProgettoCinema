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
        idSessione =  getIntent().getIntExtra(ExtrasDefinition.ID_TOKEN, EXTRA_DEFAULT_VALUE);
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

        // id della programmazione passata dall'activity prima
        idProgrammazione = getIntent().getIntExtra(ExtrasDefinition.ID_PROGRAMMAZIONE, EXTRA_DEFAULT_VALUE);
        /*if(idProgrammazione == EXTRA_DEFAULT_VALUE) {
            // errore idProgrammazione non passata
           finishSession();
        }*/

        // id dell'utente passata dall'activity prima
        idUtente = getIntent().getIntExtra(ExtrasDefinition.ID_UTENTE, EXTRA_DEFAULT_VALUE);
        if(idUtente == EXTRA_DEFAULT_VALUE) {
            // errore idUtente non passato passo al login
            finishSession();
        }

        // prendo i riferimenti
        final Button mBtnAvanti = findViewById(R.id.btnAvanti);
        RecyclerView r = findViewById(R.id.mainR);

        postiDaPrenotare = new ArrayList<>();
        idProgrammazione = 1;
        idUtente = 1;

        // sala associata
        final Sala currentSala = SalaQueries.getSalaFromId(idProgrammazione);

        // posti già prenotati per quella programmazione
        ArrayList<Integer> postiPrenotati = PostoPrenotatoQueries.getPostiPrenotati(idProgrammazione);

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
                // registro la PRENOTAZIONE
                long idPrenotazione = PrenotazioneQueries.addPrenotazione(new Prenotazione(0,idProgrammazione, idUtente));

                // faccio una lista di posti prenotati
                for(Integer i : postiDaPrenotare) {
                    PostoPrenotato p = new PostoPrenotato(0, (int) idPrenotazione, i);
                    PostoPrenotatoQueries.addPostoPrenotato(p);
                }

                // faccio partire l'activity di riassunto
                Intent riassunto = new Intent(getApplicationContext(), ResumeActivity.class);
                riassunto.putExtra(ExtrasDefinition.ID_UTENTE, idUtente);
                riassunto.putExtra(ExtrasDefinition.ID_TOKEN, idSessione);
                riassunto.putExtra(ExtrasDefinition.ID_PRENOTAZIONE, idPrenotazione);
                riassunto.putExtra(ExtrasDefinition.ID_PROGRAMMAZIONE, idProgrammazione);
                startActivity(riassunto);

                finish();
            }
        });
    }

    /**
     * Metodo che fa finire la sessione e riporta al LOGIN
     */
    private void finishSession() {
        Snackbar snack = Snackbar.make(findViewById(R.id.main_container),
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
