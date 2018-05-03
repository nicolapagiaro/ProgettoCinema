package com.grupppofigo.progettocinema.helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import com.grupppofigo.progettocinema.SessionExpired;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.queries.SessioneQueries;

/**
 * Classe per validare la sessione dell'utente
 */
public class SessionValidator {
    /**
     * Tempo massimo di sessione in millisecondi (5 minuti)
     */
    private static final int EXPIRED_TIME = 300000;

    /**
     * Funzione che controlla se la sessione è scaduta
     * @param startSession stringa che contiene lo start della session in millis
     * @return true/false
     */
    public static boolean isExpired(String startSession) {
        long s = Long.parseLong(startSession);
        return (System.currentTimeMillis() - s) >= EXPIRED_TIME;
    }

    /**
     * Funzione che termina la sessione corrente e ti mostra la schermatina
     * @param activity activity
     */
    public static void finishSession(final Activity activity, long idSession) {
        if(idSession != DatabaseContract.ID_NOT_FOUND) {
            SessioneQueries.endSession(idSession);
        }

        Intent s = new Intent(activity, SessionExpired.class);
        activity.startActivity(s);
        activity.finish();
    }
}
