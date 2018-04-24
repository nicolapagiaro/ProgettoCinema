package com.grupppofigo.progettocinema.extras;

import android.util.Log;

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
}
