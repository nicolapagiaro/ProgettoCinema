package com.grupppofigo.progettocinema.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.entities.Utente;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SnackBar;
import com.grupppofigo.progettocinema.lista_film.MainActivity;
import com.grupppofigo.progettocinema.queries.SessioneQueries;
import com.grupppofigo.progettocinema.queries.UtenteQueries;

public class RegisterActivity extends AppCompatActivity {
    /**
     * Lunghezze minime per i campi di imput
     */
    public static final int MIN_CHAR_NOME_COGNOME = 4;
    public static final int MIN_CHAR_PASSW = 5;

    private String psw;
    private String nome;
    private String cognome;
    private String email;
    private EditText mNome, mCognome, mEmail, mPassword, mConfPassword;
    private CheckBox contratto;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // riferimenti
        mNome = findViewById(R.id.regNome);
        mCognome = findViewById(R.id.regCognome);
        mEmail = findViewById(R.id.regEmail);
        mPassword = findViewById(R.id.regPassword);
        mConfPassword = findViewById(R.id.regConferma);
        contratto = findViewById(R.id.policy);
        Button registrati = findViewById(R.id.buttonRegistrati);

        constraintLayout = findViewById(R.id.constLayout);
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftKeyboard(RegisterActivity.this);
                return false;
            }
        });

        // btn REGISTRAZIONE
        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    int id = (int) UtenteQueries.addUtente(new Utente(0, nome, cognome, email, psw));
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

        // link Torna al Login
        TextView backLogin = findViewById(R.id.tvBackLogin);
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        // DISPLAY TERMINI DEL CONTRATTO
        contratto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder
                        .setTitle("Termini e Condizioni:")
                        .setMessage(R.string.terms)
                        .setPositiveButton("Accetto", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                contratto.setChecked(true);
                            }
                        })
                        .setNegativeButton("Rifiuto", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                contratto.setChecked(false);
                                SnackBar.with(getApplicationContext())
                                        .show(constraintLayout, R.string.must_accept_contrat, Snackbar.LENGTH_SHORT);
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    /**
     * Metodo che valida l'input immesso per la registrazione
     * @return true se puoi proseguire, false se no
     */
    private boolean validateInput() {
        int errCount = 0;

        // controllo il nome
        nome = mNome.getText().toString().trim();
        TextInputLayout layoutNome = findViewById(R.id.textInputNome);
        if (nome.length() < MIN_CHAR_NOME_COGNOME) {
            layoutNome.setErrorEnabled(true);
            layoutNome.setError(getString(R.string.err_nome));
            errCount++;
        }
        else {
            layoutNome.setErrorEnabled(false);
        }

        // controllo il cognome
        cognome = mCognome.getText().toString().trim();
        TextInputLayout layoutCognome = findViewById(R.id.textInputPsswLogin);
        if (cognome.length() < MIN_CHAR_NOME_COGNOME) {
            layoutCognome.setErrorEnabled(true);
            layoutCognome.setError(getString(R.string.err_cognome));
            errCount++;
        }
        else {
            layoutCognome.setErrorEnabled(false);
        }

        // controllo la mail
        email = mEmail.getText().toString().trim();
        TextInputLayout layoutEmail = findViewById(R.id.textInputEmaiLogin);
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError(getString(R.string.err_mail));
            errCount++;
        }
        else {
            layoutEmail.setErrorEnabled(false);
        }

        // controllo la prima password
        psw = mPassword.getText().toString().trim();
        TextInputLayout layoutPssw = findViewById(R.id.textInputPassw);
        if (psw.length() < MIN_CHAR_PASSW) {
            layoutPssw.setErrorEnabled(true);
            layoutPssw.setError(getString(R.string.err_passw));
            errCount++;
        }
        else {
            layoutPssw.setErrorEnabled(false);
        }

        // controllo la seconda password
        String pswConf = mConfPassword.getText().toString().trim();
        TextInputLayout layoutPsswConf = findViewById(R.id.textInputPasswConf);
        if (!pswConf.equals(psw)) {
            layoutPsswConf.setErrorEnabled(true);
            layoutPsswConf.setError(getString(R.string.err_passw_conf));
            errCount++;
        }
        else {
            layoutPsswConf.setErrorEnabled(false);
        }

        // check box del contratto
        if (!contratto.isChecked()) {
            if(errCount == 0)
                SnackBar.with(getApplicationContext())
                        .show(constraintLayout, R.string.must_accept_contrat, Snackbar.LENGTH_SHORT);
            errCount++;
        }

        return errCount == 0;
    }

    @Override
    public void onBackPressed() {
        // se preme indietro torna al login
        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(login);
        finish();
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