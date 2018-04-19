package com.grupppofigo.progettocinema.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.extras.ExtrasDefinition;
import com.grupppofigo.progettocinema.prenotazione_posti.PostiActivity;
import com.grupppofigo.progettocinema.queries.SessioneQueries;
import com.grupppofigo.progettocinema.queries.UtenteQueries;


public class LoginActivity extends AppCompatActivity {
    EditText mMail, mPassword, inputMail;
    Button mAccedi;
    TextView mRegistrati, getPsw;
    ConstraintLayout constraintLayout;
    String mail, pssw, verificaMail, verificaPassword, token, pswOttenuta, emailIns;
    long t;
    int stToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mMail = findViewById(R.id.editTextEmail);
        mPassword = findViewById(R.id.editTextPassword);
        mAccedi = findViewById(R.id.buttonAccedi);
        mRegistrati = findViewById(R.id.linkRegistrati);
        getPsw = findViewById(R.id.textGetPsw);

        mPassword.setTransformationMethod(new PasswordTransformationMethod());

        // forgot password
        getPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Inserisci la mail:");

                inputMail = new EditText(LoginActivity.this);
                inputMail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                builder.setView(inputMail);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                emailIns = inputMail.getText().toString();
                                String pssw = UtenteQueries.forgotPassword(emailIns);

                                if (pssw != null) {
                                    Snackbar.make(view, "La tua Password è: "+pswOttenuta, Snackbar.LENGTH_LONG)
                                            .show();
                                } else {
                                    Snackbar.make(view, "Questa mail non esiste!", Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .create()
                        .show();
            }
        });

        String testoIniz = " Non hai ancora un account?";
        String textCrea = "<font color='#488AFF'><br>Creane uno!</font>";
        mRegistrati.setText(Html.fromHtml(testoIniz + textCrea));

        constraintLayout = findViewById(R.id.constLayout);
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftKeyboard(LoginActivity.this);
                return false;
            }
        });

        // login
        mAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaMail = mMail.getText().toString();
                verificaPassword = mPassword.getText().toString();
                if (mMail.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(verificaMail).matches()) {
                    mMail.setError("Inserisci una mail valida!");
                } else if (mPassword.getText().toString().isEmpty() || verificaPassword.length() < 4) {
                    mPassword.setError("Inserisci una passeword valida!");
                } else {
                    mail = mMail.getText().toString();
                    pssw = mPassword.getText().toString();

                    long id = UtenteQueries.loginUtente(mail, pssw);
                    if(id != DatabaseContract.ID_NOT_FOUND) {
                        // l'utente c'è registro la sessione
                        SessioneQueries.startSession((int) id);

                        // faccio partire l'altra activity
                        Intent postLoginAct = new Intent(LoginActivity.this, PostiActivity.class);
                        postLoginAct.putExtra(ExtrasDefinition.START_SESSION, String.valueOf(t));
                        postLoginAct.putExtra(ExtrasDefinition.ID_TOKEN, token);
                        postLoginAct.putExtra(ExtrasDefinition.ID_UTENTE, (int) id);
                        startActivity(postLoginAct);
                    }
                }
            }
        });

        // registrazione
        mRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    /**
     * Per fare sparire la tastiera
     * @param activity activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}