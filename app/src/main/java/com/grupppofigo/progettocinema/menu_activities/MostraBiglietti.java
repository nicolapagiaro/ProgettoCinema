package com.grupppofigo.progettocinema.menu_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SessionValidator;
import com.grupppofigo.progettocinema.queries.SessioneQueries;

public class MostraBiglietti extends AppCompatActivity {

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
