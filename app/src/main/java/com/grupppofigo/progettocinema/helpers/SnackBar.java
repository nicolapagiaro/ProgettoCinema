package com.grupppofigo.progettocinema.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.grupppofigo.progettocinema.R;

/**
 * Per fare la snackbar bianca invece che nera
 */
public class SnackBar {
    @SuppressLint("StaticFieldLeak")
    private static Context cx;

    /**
     * Costruttore vuoto
     */
    private SnackBar() {}

    @NonNull
    public static SnackBar with(Context c) {
        cx = c;
        return new SnackBar();
    }

    /**
     * Metodo per mostrare la snackbar
     * @param view view container
     * @param msg messaggio
     * @param duration durata
     */
    public void show(View view, String msg, int duration) {
        Snackbar snackbar;
        snackbar = Snackbar.make(view, msg, duration);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(cx, R.color.cardBackground));
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(cx, R.color.primaryTextColorBlack));
        snackbar.show();
    }

    /**
     * Metodo per mostrare la snackbar
     * @param view view container
     * @param idMsg id del messaggio
     * @param duration durata
     */
    public void show(View view, int idMsg, int duration) {
        Snackbar snackbar;
        snackbar = Snackbar.make(view, idMsg, duration);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(cx, R.color.cardBackground));
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(cx, R.color.primaryTextColorBlack));
        snackbar.show();
    }
}
