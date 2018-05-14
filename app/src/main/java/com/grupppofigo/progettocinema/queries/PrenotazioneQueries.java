package com.grupppofigo.progettocinema.queries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.grupppofigo.progettocinema.database.DBHelper;
import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.Prenotazione;

import java.util.ArrayList;

/**
 * Classe che gestisce le query per la Prenotazione
 */
public class PrenotazioneQueries {
    private static DBHelper mDb = DataStore.getDB();

    /**
     * Aggiunge la prenotazione corrente al database
     * @param p oggetto prenotazione
     */
    public static long addPrenotazione(Prenotazione p) {
        SQLiteDatabase d = mDb.getWritableDatabase();
        long id = 0;

        ContentValues cv = prenotazioneToCV(p);
        // tolgo l'id perchè e autogenerato
        cv.remove(DatabaseContract.PrenotazioneContract._ID);

        try {
            id = d.insert(DatabaseContract.PrenotazioneContract.TABLE_NAME,
                    null,
                    cv);
        }
        catch (SQLiteException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    /**
     * Metodo che elimina una prenotazione dal database passato l'id
     * @param idPrenotazione id della prenotazione da eliminare
     * @return true/false se eliminata o no
     */
    public static boolean removePrenotazione(long idPrenotazione) {
        SQLiteDatabase d = mDb.getWritableDatabase();
        int modifedRows = 0;

        try {
            modifedRows = d.delete(DatabaseContract.PrenotazioneContract.TABLE_NAME,
                    DatabaseContract.PrenotazioneContract._ID + "=?",
                    new String[]{idPrenotazione+""});
        }
        catch (SQLiteException ex) {
            ex.printStackTrace();
        }

        return modifedRows != 0;
    }

    /**
     * Converte un oggetto Prenotazione in contentValues
     * @param p oggetto prenotazione
     * @return un contentvalues
     */
    private static ContentValues prenotazioneToCV(Prenotazione p) {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseContract.PrenotazioneContract._ID, p.getId());
        cv.put(DatabaseContract.PrenotazioneContract.ID_PROGRAMMAZIONE, p.getIdProgrammazione());
        cv.put(DatabaseContract.PrenotazioneContract.ID_UTENTE, p.getIdUtente());

        return cv;
    }

    /**
     * Restituisce una lista di prenotazioni
     * @return lista di prenotazioni
     */
    public static ArrayList<Prenotazione> getAllPrenotazioni() {
        SQLiteDatabase d = mDb.getReadableDatabase();
        ArrayList<Prenotazione> res = new ArrayList<>();

        Cursor c = d.query(DatabaseContract.PrenotazioneContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (c.moveToNext()) {
            res.add(prenotazioneFromCursor(c));
        }

        c.close();
        return res;
    }

    /**
     * Converte il cursore in una prentoazione
     * @param c cursore
     * @return una prenotazione
     */
    private static Prenotazione prenotazioneFromCursor(Cursor c) {
        Prenotazione p = new Prenotazione();

        p.setId(c.getInt(c.getColumnIndex(DatabaseContract.PrenotazioneContract._ID)));
        p.setIdProgrammazione(c.getInt(c.getColumnIndex(DatabaseContract.PrenotazioneContract.ID_PROGRAMMAZIONE)));
        p.setIdUtente(c.getInt(c.getColumnIndex(DatabaseContract.PrenotazioneContract.ID_UTENTE)));

        return p;
    }

}
