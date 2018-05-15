package com.grupppofigo.progettocinema.menu_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.entities.Prenotazione;
import com.grupppofigo.progettocinema.entities.Programmazione;
import com.grupppofigo.progettocinema.entities.Utente;
import com.grupppofigo.progettocinema.helpers.DateParser;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SessionValidator;
import com.grupppofigo.progettocinema.queries.FilmQueries;
import com.grupppofigo.progettocinema.queries.PrenotazioneQueries;
import com.grupppofigo.progettocinema.queries.ProgrammazioneQueries;
import com.grupppofigo.progettocinema.queries.SessioneQueries;
import com.grupppofigo.progettocinema.queries.UtenteQueries;

import java.text.ParseException;
import java.util.ArrayList;

public class AccountIntent extends AppCompatActivity {
    private static final int MAX_RES = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_intent);

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

        Utente utente = UtenteQueries.getUtente(idUtente);
        if(utente == null) {
            // errore nessun utente trovato
            SessionValidator.finishSession(this, idSessione);
        }

        // prendo le sue prentoazioni
        ArrayList<Prenotazione> prenotazioni = PrenotazioneQueries.getPrenotazioni(idUtente, MAX_RES);
        ArrayList<Programmazione> programmazioni = new ArrayList<>();
        ArrayList<Film> films = new ArrayList<>();
        for (int i = 0; i < prenotazioni.size(); i++) {
            programmazioni.add(ProgrammazioneQueries.getProgrammmazione(prenotazioni.get(i).getIdProgrammazione()));
            films.add(FilmQueries.getFilm(programmazioni.get(i).getIdFilm()));
        }

        // riferimenti alle view
        TextView tvNomeCognome = findViewById(R.id.tvNomeCognomeUtente);
        TextView tvEmail = findViewById(R.id.tvMailUtente);
        TextView tvPassword = findViewById(R.id.tvPasswordUtente);
        Button btn = findViewById(R.id.btnShowAllBiglietti);
        LinearLayout bigliettiContainer = findViewById(R.id.prenotazioniList);

        // info utente
        tvNomeCognome.setText(utente.getNome() + " " + utente.getCognome());
        tvEmail.setText(utente.getEmail());

        // le sue prenotazioni
        for (int i = 0; i < films.size(); i++) {
            View v = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.info_biglietti_item, bigliettiContainer, false);

            ((TextView) v.findViewById(R.id.tvNomeFilm)).setText(films.get(i).getTitolo());
            try {
                ((TextView) v.findViewById(R.id.tvDataFilm)).setText(DateParser.getFormattedDate(programmazioni.get(i).getData(), true));
            } catch (ParseException e) {
                ((TextView) v.findViewById(R.id.tvDataFilm)).setText(programmazioni.get(i).getData());
            }
            ((TextView) v.findViewById(R.id.tvOraFilm)).setText(programmazioni.get(i).getOra());
            bigliettiContainer.addView(v);
        }

        // click listener sul pulsante MOSTRA TUTTI
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allBiglietti = new Intent(getApplicationContext(), MostraBiglietti.class);
                allBiglietti.putExtra(ExtrasDefinition.ID_TOKEN, idSessione);
                allBiglietti.putExtra(ExtrasDefinition.ID_UTENTE, idUtente);
                allBiglietti.putExtra(ExtrasDefinition.START_SESSION, startSession);
                startActivity(allBiglietti);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
