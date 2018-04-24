package com.grupppofigo.progettocinema.queries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.grupppofigo.progettocinema.database.DBHelper;
import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.Sala;

import java.util.ArrayList;

/**
 * Classe con tutte le query per le sale
 */
public class SalaQueries {
    private static DBHelper mDb = DataStore.getDB();


    /**
     * Aggiugne una sala al database
     * @param s sala da aggiunger
     */
    public static void addSala(Sala s) {
        SQLiteDatabase d = mDb.getWritableDatabase();

        ContentValues cv = salaToContentValues(s);
        // tolgo l'id
        cv.remove(DatabaseContract.SalaContract._ID);

        d.beginTransaction();
        try {
            d.insert(DatabaseContract.SalaContract.TABLE_NAME,
                    null,
                    cv);
            d.setTransactionSuccessful();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            d.endTransaction();
        }
    }

    /**
     * Restituisce una lista di sale
     * @return una lista di sale
     */
    public static ArrayList<Sala> getAllSalas() {
        SQLiteDatabase d = mDb.getReadableDatabase();
        ArrayList<Sala> res = new ArrayList<>();

        Cursor c = d.query(DatabaseContract.SalaContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (c.moveToNext()) {
            res.add(salaFromCursor(c));
        }

        c.close();
        return res;
    }

    /**
     * Preleva una sala dato un id
     * @param idProgrammazione id della programmazione
     * @return oggetto sala o null
     */
    public static Sala getSalaFromId(int idProgrammazione) {
        SQLiteDatabase d = mDb.getReadableDatabase();
        Sala s = null;

        // prendo l'id della sala associata
        String[] args = {idProgrammazione+""};
        Cursor cProgrammazione = d.query(DatabaseContract.ProgrammazioneContract.TABLE_NAME,
                null,
                DatabaseContract.ProgrammazioneContract._ID + " = ?",
                args,
                null,
                null,
                null);

        int idSala = 0;
        if(cProgrammazione.moveToNext()) {
            idSala = cProgrammazione.getInt(cProgrammazione.getColumnIndex(DatabaseContract.ProgrammazioneContract.ID_SALA));
        }

        cProgrammazione.close();


        // prendo la sala dall'id
        String[] args1 = {idSala+""};
        Cursor c = d.query(DatabaseContract.SalaContract.TABLE_NAME,
                null,
                DatabaseContract.SalaContract._ID + " = ?",
                args1,
                null,
                null,
                null);

        if(c.moveToNext()) {
            s = salaFromCursor(c);
        }

        c.close();
        return s;
    }

    /**
     * Preleva una sala dato un id della sala
     * @param idSala id della sala
     * @return oggetto sala o null
     */
    public static Sala getSala(int idSala) {
        SQLiteDatabase d = mDb.getReadableDatabase();
        Sala s = null;

        // prendo la sala dall'id
        String[] args1 = {idSala+""};
        Cursor c = d.query(DatabaseContract.SalaContract.TABLE_NAME,
                null,
                DatabaseContract.SalaContract._ID + " = ?",
                args1,
                null,
                null,
                null);

        if(c.moveToNext()) {
            s = salaFromCursor(c);
        }

        c.close();
        return s;
    }

    /**
     * Dal cursore prende un oggetto sala
     * @param c cursore
     * @return oggetto sala
     */
    private static Sala salaFromCursor(Cursor c) {
        Sala s = new Sala();

        s.setId(c.getInt(c.getColumnIndex(DatabaseContract.SalaContract._ID)));
        s.setNome(c.getString(c.getColumnIndex(DatabaseContract.SalaContract.NOME)));
        s.setnPosti(c.getInt(c.getColumnIndex(DatabaseContract.SalaContract.NUMERO_POSTI)));

        return s;
    }

    /**
     * Converte un oggetto sala in un oggetto contentvalues
     * @param s oggetto sala
     * @return oggetto contentvalues
     */
    private static ContentValues salaToContentValues(Sala s) {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseContract.SalaContract._ID, s.getId());
        cv.put(DatabaseContract.SalaContract.NOME, s.getNome());
        cv.put(DatabaseContract.SalaContract.NUMERO_POSTI, s.getnPosti());

        return cv;
    }
}
