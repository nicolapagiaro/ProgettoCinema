package com.grupppofigo.progettocinema.menu_activities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import static com.grupppofigo.progettocinema.helpers.ExtrasDefinition.EXTRA_DEFAULT_VALUE;

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


        // id dell'utente passata dall'activity prima
        final int idUtente = getIntent().getIntExtra(ExtrasDefinition.ID_UTENTE, EXTRA_DEFAULT_VALUE);

        Utente u = UtenteQueries.getUtente(idUtente);
        if (u == null) {
            finish();
        }

        String nome = u.getNome();
        String cognome = u.getCognome();
        String mail = u.getEmail();
        String password = u.getPassw();

        txNomeUtente.setText(nome + " " + cognome);
        txPassword.setText(password);
        txMail.setText(mail);


    }
}
