package com.grupppofigo.progettocinema.prenotazione_posti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.extras.ExtrasDefinition;
import com.grupppofigo.progettocinema.queries.PostoPrenotatoQueries;
import com.grupppofigo.progettocinema.queries.PrenotazioneQueries;
import com.grupppofigo.progettocinema.queries.SalaQueries;
import com.grupppofigo.progettocinema.entities.PostoPrenotato;
import com.grupppofigo.progettocinema.entities.Prenotazione;
import com.grupppofigo.progettocinema.entities.Sala;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posti);

        // id della programmazione passata dall'activity prima
        idProgrammazione = getIntent().getIntExtra(ExtrasDefinition.ID_PROGRAMMAZIONE, ExtrasDefinition.EXTRA_DEFAULT_VALUE);
        /*if(idProgrammazione == EXTRA_DEFAULT_VALUE) {
            // errore idProgrammazione non passata
            finish();
        }*/

        // id dell'utente passata dall'activity prima
        idUtente = getIntent().getIntExtra(ExtrasDefinition.ID_UTENTE, ExtrasDefinition.EXTRA_DEFAULT_VALUE);
        /*if(idUtente == EXTRA_DEFAULT_VALUE) {
            // errore idUtente non passato
            finish();
        }*/

        //ArrayList<Film> f = FilmQueries.getAllFilms();
        //ArrayList<Programmazione> ps = Queries.getAllProgrammaziones();
        //Log.d("Lista dei film", f.toString());
        //Log.d("Lista program", ps.toString());
        //Log.d("Program", Queries.getProgrammmazione(0).toString());
        //Log.d("Lista generi", Queries.getAllGeneri().toString());

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

                mBtnAvanti.setClickable(false);
            }
        });
    }
}
