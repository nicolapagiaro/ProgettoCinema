package com.grupppofigo.progettocinema.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.grupppofigo.progettocinema.R;

class ForgotPasswDialog extends AlertDialog {

    /**
     * Costruttore matching super
     * @param context context
     */
    ForgotPasswDialog(@NonNull Context context) {
        super(context);

        // titolo
        setTitle(R.string.dialog_title);

        // la view
        View v = LayoutInflater.from(context).inflate(R.layout.layout_recupero_pssw, null, false);
        setView(v);
    }

}
