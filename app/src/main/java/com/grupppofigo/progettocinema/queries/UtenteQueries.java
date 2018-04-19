package com.grupppofigo.progettocinema.queries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.grupppofigo.progettocinema.database.DBHelper;
import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.Utente;

import java.util.ArrayList;

/**
 * Classe per le query per gli Utenti
 */
public class UtenteQueries {
    private static DBHelper mDb = DataStore.getDB();

    /**
     * Inserisce un utente nel database
     * @param u oggetto utente
     */
    public static long addUtente(Utente u) {
        long id = 0;
        SQLiteDatabase d = mDb.getWritableDatabase();

        ContentValues cv = utenteToContentValues(u);
        // tolgo l'id
        cv.remove(DatabaseContract.UtentiContract._ID);

        try {
            id = d.insert(DatabaseContract.UtentiContract.TABLE_NAME,
                    null,
                    cv);
        }
        catch (SQLiteException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    /**
     * Converte un oggetto Utente in contentvalues
     * @param u oggetto utente
     * @return oggetto contentvalues
     */
    private static ContentValues utenteToContentValues(Utente u) {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseContract.UtentiContract._ID, u.getId());
        cv.put(DatabaseContract.UtentiContract.NOME, u.getNome());
        cv.put(DatabaseContract.UtentiContract.COGNOME, u.getCognome());
        cv.put(DatabaseContract.UtentiContract.EMAIL, u.getEmail());
        cv.put(DatabaseContract.UtentiContract.PASSW, u.getPassw());

        return cv;
    }

    /**
     * Restituisce una lista di utenti del database
     * @return lista di utenti
     */
    public static ArrayList<Utente> getAllUtenti() {
        ArrayList<Utente> res = new ArrayList<>();
        SQLiteDatabase d = mDb.getReadableDatabase();

        Cursor c = d.query(DatabaseContract.UtentiContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (c.moveToNext()) {
            res.add(utenteFromCursor(c));
        }

        c.close();
        return res;
    }

    /**
     * Converte un cursore in un oggetto Utente
     * @param c oggetto cursor
     * @return oggetto utente
     */
    private static Utente utenteFromCursor(Cursor c) {
        Utente u = new Utente();

        u.setId(c.getInt(c.getColumnIndex(DatabaseContract.UtentiContract._ID)));
        u.setNome(c.getString(c.getColumnIndex(DatabaseContract.UtentiContract.NOME)));
        u.setCognome(c.getString(c.getColumnIndex(DatabaseContract.UtentiContract.COGNOME)));
        u.setEmail(c.getString(c.getColumnIndex(DatabaseContract.UtentiContract.EMAIL)));
        u.setPassw(c.getString(c.getColumnIndex(DatabaseContract.UtentiContract.PASSW)));

        return u;
    }
}
