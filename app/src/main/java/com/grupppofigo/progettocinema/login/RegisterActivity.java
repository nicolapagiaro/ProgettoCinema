package com.grupppofigo.progettocinema.login;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.entities.Utente;
import com.grupppofigo.progettocinema.extras.ExtrasDefinition;
import com.grupppofigo.progettocinema.lista_film.MainActivity;
import com.grupppofigo.progettocinema.prenotazione_posti.PostiActivity;
import com.grupppofigo.progettocinema.queries.SessioneQueries;
import com.grupppofigo.progettocinema.queries.UtenteQueries;

public class RegisterActivity extends AppCompatActivity {
    String psw, conf, nom, cog, email;
    EditText mNome, mCognome, mEmail, mPassword, mConfPassword;
    CheckBox contratto;
    Button registrati;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mNome = findViewById(R.id.regNome);
        mCognome = findViewById(R.id.regCognome);
        mEmail = findViewById(R.id.regEmail);
        mPassword = findViewById(R.id.regPassword);
        mConfPassword = findViewById(R.id.regConferma);
        contratto = findViewById(R.id.policy);
        registrati = findViewById(R.id.buttonRegistrati);

        constraintLayout = findViewById(R.id.constLayout);
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftKeyboard(RegisterActivity.this);
                return false;
            }
        });

        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                psw = mPassword.getText().toString();
                conf = mConfPassword.getText().toString();

                if (mNome.getText().toString().isEmpty()) {
                    mNome.setError("Inserisci il Nome");
                } else if (mCognome.getText().toString().isEmpty()) {
                    mCognome.setError("Inserisci il Cognome");
                } else if (mEmail.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmail.setError("Inserisci una mail valida!");
                } else if (psw.length() < 4) {
                    mPassword.setError("Password troppo corta!");
                } else if (!psw.equals(conf)) {
                    mConfPassword.setError("Le Password sono diverse");
                } else if (!contratto.isChecked()) {
                    Snackbar.make(v, "Devi Accettare il contratto!", Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    nom = mNome.getText().toString();
                    cog = mCognome.getText().toString();
                    email = mEmail.getText().toString();
                    psw = mPassword.getText().toString();

                    int id = (int) UtenteQueries.addUtente(new Utente(0, nom, cog, email, psw));
                    long startTime = System.currentTimeMillis();
                    long idSession = SessioneQueries.startSession(id, startTime);

                    Intent toPostLog = new Intent(RegisterActivity.this, MainActivity.class);
                    toPostLog.putExtra(ExtrasDefinition.ID_UTENTE, id);
                    toPostLog.putExtra(ExtrasDefinition.START_SESSION, String.valueOf(startTime));
                    toPostLog.putExtra(ExtrasDefinition.ID_TOKEN, idSession);
                    startActivity(toPostLog);
                    finish();
                }
            }
        });
    }

    /**
     * Per fare andare via la tastiera
     * @param activity activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}