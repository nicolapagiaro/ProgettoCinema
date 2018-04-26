package com.grupppofigo.progettocinema.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SnackBar;
import com.grupppofigo.progettocinema.lista_film.MainActivity;
import com.grupppofigo.progettocinema.queries.SessioneQueries;
import com.grupppofigo.progettocinema.queries.UtenteQueries;

import static com.grupppofigo.progettocinema.login.RegisterActivity.MIN_CHAR_PASSW;

public class LoginActivity extends AppCompatActivity {
    private EditText mMail, mPassword;
    private String mail, pssw;
    private Long token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mMail = findViewById(R.id.editTextEmail);
        mPassword = findViewById(R.id.editTextPassword);
        Button mAccedi = findViewById(R.id.buttonAccedi);
        TextView mRegistrati = findViewById(R.id.linkRegistrati);
        final TextView getPsw = findViewById(R.id.textGetPsw);

        mPassword.setTransformationMethod(new PasswordTransformationMethod());

        // forgot password
        getPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showForgotPsswDialog();
            }
        });

        // testo colorato solo una parte per il link per la registrazione
        String txtRegistra = getString(R.string.goToRegisterLink1) + getString(R.string.goToRegisterLink2);
        Spannable spannable = new SpannableString(txtRegistra);
        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
        spannable.setSpan(new ForegroundColorSpan(color), getString(R.string.goToRegisterLink1).length(), txtRegistra.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mRegistrati.setText(spannable, TextView.BufferType.SPANNABLE);

        final ConstraintLayout constraintLayout = findViewById(R.id.constLayout);
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

                if(validateInput()){
                    hideSoftKeyboard(LoginActivity.this);

                    long id = UtenteQueries.loginUtente(mail, pssw);

                    if(id != DatabaseContract.ID_NOT_FOUND) {
                        // l'utente c'è registro la sessione
                        long startTime = System.currentTimeMillis();
                        token = SessioneQueries.startSession((int) id, startTime);

                        // faccio partire l'altra activity
                        Intent postLoginAct = new Intent(LoginActivity.this, MainActivity.class);
                        postLoginAct.putExtra(ExtrasDefinition.START_SESSION, String.valueOf(startTime));
                        postLoginAct.putExtra(ExtrasDefinition.ID_TOKEN, token);
                        postLoginAct.putExtra(ExtrasDefinition.ID_UTENTE, (int) id);
                        startActivity(postLoginAct);
                        finish();
                    }
                    else {
                        // l'utente non c'è
                        SnackBar.with(getApplicationContext())
                                .show(constraintLayout, R.string.err_user_not_found, Snackbar.LENGTH_SHORT);
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
                finish();
            }
        });
    }

    /**
     * Metodo che verifica se i dati inseriti son validi
     * @return true/false
     */
    private boolean validateInput() {
        int errCount = 0;

        // controllo la mail
        mail = mMail.getText().toString().trim();
        TextInputLayout layoutMail = findViewById(R.id.textInputEmaiLogin);
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            layoutMail.setErrorEnabled(true);
            layoutMail.setError(getString(R.string.err_mail));
            errCount++;
        }
        else {
            layoutMail.setErrorEnabled(false);
        }

        // controllo la password
        pssw = mPassword.getText().toString().trim();
        TextInputLayout layoutPssw = findViewById(R.id.textInputPsswLogin);
        if(pssw.length() < MIN_CHAR_PASSW) {
            layoutPssw.setErrorEnabled(true);
            layoutPssw.setError(getString(R.string.err_passw));
            errCount++;
        }
        else {
            layoutPssw.setErrorEnabled(false);
        }

        return errCount == 0;
    }

    /**
     * Per mostrare il dialog per il recupero password
     */
    private void showForgotPsswDialog() {
        final View v = getLayoutInflater().inflate(R.layout.layout_recupero_pssw, null);

        AlertDialog dia = new AlertDialog.Builder(LoginActivity.this)
                            .setTitle(R.string.dialog_title)
                            .setView(v)
                            .setPositiveButton(android.R.string.ok, null)
                            .setNegativeButton(android.R.string.cancel, null)
                            .create();

        dia.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // prendo la mail e cerco la password nel DB
                        String mailInserita = ((EditText)v.findViewById(R.id.etPasswForgot)).getText().toString().trim();
                        String pssw = null;
                        TextView result = v.findViewById(R.id.tvResultPassw);

                        if(mailInserita.length() != 0) {
                            pssw = UtenteQueries.forgotPassword(mailInserita);
                        }

                        if (pssw != null) {
                            result.setText(getString(R.string.tvResultPssw, pssw));
                        } else {
                            result.setText(getString(R.string.tvNoResultPssw));
                        }

                        // dopo 2 secondi chiudo il dialog
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        }, 2000);

                    }
                });
            }
        });

        dia.show();
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