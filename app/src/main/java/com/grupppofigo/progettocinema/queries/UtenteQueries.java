package com.grupppofigo.progettocinema.queries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.ContactsContract;

import com.grupppofigo.progettocinema.database.DBHelper;
import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.database.DatabaseContract.UtentiContract;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.Utente;

import java.util.ArrayList;

import static com.grupppofigo.progettocinema.database.DatabaseContract.ID_NOT_FOUND;

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
     * Restituisce un utente passato l'id
     * @return l'oggetto utente se c'è se no NULL
     */
    public static Utente getUtente(int idUtente) {
        Utente res = null;
        SQLiteDatabase d = mDb.getReadableDatabase();

        Cursor c = d.query(DatabaseContract.UtentiContract.TABLE_NAME,
                null,
                UtentiContract._ID + "=?",
                new String[]{idUtente+""},
                null,
                null,
                null);

        if (c.moveToNext()) {
            res = utenteFromCursor(c);
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

    /**
     * Metodo che effettua il login dell'utente, cioè verifica se c'è un utente
     * passato
     * @return restituisce un ID se c'è o -1 se non c'è
     */
    public static long loginUtente(String email, String pssw) {
        long id = ID_NOT_FOUND;
        SQLiteDatabase d = mDb.getReadableDatabase();

        Cursor c = d.query(UtentiContract.TABLE_NAME,
                null,
                UtentiContract.EMAIL + "=? and " + UtentiContract.PASSW + "=?",
                new String[]{email, pssw},
                null,
                null,
                null);

        if(c.moveToNext()) {
            id = c.getInt(c.getColumnIndex(UtentiContract._ID));
        }

        c.close();
        return id;
    }

    /**
     * Metodo che restituisce la password di un utente registrato se se l'è
     * dimenticata
     * @param email email dell'utente
     * @return una stringa con la PASSWORD o NULL se niente
     */
    public static String forgotPassword(String email) {
        String pssw = null;
        SQLiteDatabase d = mDb.getReadableDatabase();

        Cursor c = d.query(UtentiContract.TABLE_NAME,
                null,
                UtentiContract.EMAIL + "= ?",
                new String[] {email},
                null,
                null,
                null);

        if(c.moveToNext()) {
            pssw = c.getString(c.getColumnIndex(UtentiContract.PASSW));
        }

        c.close();
        return pssw;
    }
}
