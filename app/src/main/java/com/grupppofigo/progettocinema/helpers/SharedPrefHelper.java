package com.grupppofigo.progettocinema.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Classe con dei metodi per interagire con le shared preferences di default
 */
public class SharedPrefHelper {
    public static final int USER_NOT_LOGGED = -1;
    private static final String USER_KEY = "idUtente";
    private Context context;

    public SharedPrefHelper(Context context) {
        this.context = context;
    }

    public static SharedPrefHelper with(Context cx) {
        return new SharedPrefHelper(cx);
    }

    /**
     * Per registrare un utente nelle shared preferences
     * @param idUtente id dell'utente
     */
    public void rememberUser(int idUtente) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_KEY, idUtente);
        editor.apply();
    }

    /**
     * Restituisce l'id dell'utente se c'è
     * @return id dell'utente
     */
    public int getUser() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(USER_KEY, USER_NOT_LOGGED);
    }

    /**
     * Per rimuovere l'id
     */
    public void removeUser() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_KEY, USER_NOT_LOGGED);
        editor.apply();
    }
}
