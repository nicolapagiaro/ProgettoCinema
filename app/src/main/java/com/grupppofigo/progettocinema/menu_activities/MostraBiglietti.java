package com.grupppofigo.progettocinema.menu_activities;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.entities.Prenotazione;
import com.grupppofigo.progettocinema.entities.Programmazione;
import com.grupppofigo.progettocinema.helpers.DateParser;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SessionValidator;
import com.grupppofigo.progettocinema.queries.FilmQueries;
import com.grupppofigo.progettocinema.queries.PrenotazioneQueries;
import com.grupppofigo.progettocinema.queries.ProgrammazioneQueries;
import com.grupppofigo.progettocinema.queries.SessioneQueries;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;

public class MostraBiglietti extends AppCompatActivity {
    private RecyclerView mainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_biglietti);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // id della sessione
        final long idSessione = getIntent().getLongExtra(ExtrasDefinition.ID_TOKEN, DatabaseContract.ID_NOT_FOUND);
        if(idSessione == DatabaseContract.ID_NOT_FOUND) {
            SessionValidator.finishSession(this, idSessione);
        }

        // start della sessione
        final String startSession = getIntent().getStringExtra(ExtrasDefinition.START_SESSION);
        if(startSession == null) {
            SessionValidator.finishSession(this, idSessione);
        }
        else if(SessionValidator.isExpired(startSession)){
            // se è scaduta la registro e chiudo tutto
            SessioneQueries.endSession(idSessione);
            SessionValidator.finishSession(this, idSessione);
        }

        // id dell'utente
        final int idUtente = getIntent().getIntExtra(ExtrasDefinition.ID_UTENTE, DatabaseContract.ID_NOT_FOUND);
        if(idUtente == DatabaseContract.ID_NOT_FOUND) {
            SessionValidator.finishSession(this, idSessione);
        }

        // prendo le sue prentoazioni
        ArrayList<Prenotazione> prenotazioni = PrenotazioneQueries.getPrenotazioni(idUtente);
        ArrayList<Programmazione> programmazioni = new ArrayList<>();
        ArrayList<Film> films = new ArrayList<>();
        for (int i = 0; i < prenotazioni.size(); i++) {
            programmazioni.add(ProgrammazioneQueries.getProgrammmazione(prenotazioni.get(i).getIdProgrammazione()));
            films.add(FilmQueries.getFilm(programmazioni.get(i).getIdFilm()));
        }

        // recycler view con tutti i biglietti
        mainList = findViewById(R.id.mostraBigliettiList);
        mainList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mainList.setAdapter(new BigliettiListAdapter(films, programmazioni));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Adapter per la lista dei biglietti
     */
    class BigliettiListAdapter extends RecyclerView.Adapter<BigliettiListAdapter.BigliettiViewHolder> {
        private ArrayList<Film> films;
        private ArrayList<Programmazione> programmazioni;

        /**
         * Costruttore dell'adapter
         * @param films lista film
         * @param programmazioni programmazioni
         */
        BigliettiListAdapter(ArrayList<Film> films, ArrayList<Programmazione> programmazioni) {
            this.films = films;
            this.programmazioni = programmazioni;
        }

        @Override
        public BigliettiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(MostraBiglietti.this)
                    .inflate(R.layout.info_biglietti_item, parent, false);
            return new BigliettiViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final BigliettiViewHolder holder, int position) {
            // metto i dati
            holder.tvNomeFilm.setText(films.get(position).getTitolo());
            try {
                holder.tvDataFilm.setText(DateParser.getFormattedDate(programmazioni.get(position).getData(), true));
            } catch (ParseException e) {
                holder.tvDataFilm.setText(programmazioni.get(position).getData());
            }
            holder.tvOraFilm.setText(programmazioni.get(position).getOra());

            // click listener
            final int i = position;
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Prenotazione nr." + i, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return programmazioni.size();
        }

        /**
         * ViewHolder per i biglietti
         */
        class BigliettiViewHolder extends RecyclerView.ViewHolder {
            ConstraintLayout container;
            TextView tvNomeFilm;
            TextView tvOraFilm;
            TextView tvDataFilm;

            /**
             * Costruttore
             * @param itemView view creata
             */
            BigliettiViewHolder(View itemView) {
                super(itemView);
                container = itemView.findViewById(R.id.infoBigliettiItemContainer);
                tvNomeFilm = itemView.findViewById(R.id.tvNomeFilm);
                tvOraFilm = itemView.findViewById(R.id.tvOraFilm);
                tvDataFilm = itemView.findViewById(R.id.tvDataFilm);
            }
        }
    }
}
