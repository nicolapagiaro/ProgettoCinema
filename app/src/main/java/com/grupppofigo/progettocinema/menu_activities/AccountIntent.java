package com.grupppofigo.progettocinema.menu_activities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.grupppofigo.progettocinema.CinemaApplication;
import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.database.DBHelper;
import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.Utente;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SessionValidator;
import com.grupppofigo.progettocinema.queries.SessioneQueries;
import com.grupppofigo.progettocinema.queries.UtenteQueries;

public class AccountIntent extends AppCompatActivity {

    TextView txNomeUtente, txPassword, txMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_intent);
        setTitle("Account");

        txNomeUtente = findViewById(R.id.nome_utente);
        txPassword = findViewById(R.id.password);
        txMail = findViewById(R.id.mail);

    }
}
