package com.grupppofigo.progettocinema.queries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.grupppofigo.progettocinema.database.DBHelper;
import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.Programmazione;

import java.util.ArrayList;

/**
 * Classe con tutte le query per le Programmazione
 */
public class ProgrammazioneQueries {
    private static DBHelper mDb = DataStore.getDB();


    /**
     * Aggiunge una programmazione al database
     * @param p programmazione
     */
    public static void addProgrammazione(Programmazione p) {
        SQLiteDatabase d = mDb.getWritableDatabase();

        ContentValues cv = programmazioneToContentValues(p);
        // tolgo l'id
        cv.remove(DatabaseContract.ProgrammazioneContract._ID);

        d.beginTransaction();
        try {
            d.insert(DatabaseContract.ProgrammazioneContract.TABLE_NAME,
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
     * Restituisce tutte le programmazioni dal database
     * @return le programmazioni dal database
     */
    public static ArrayList<Programmazione> getAllProgrammaziones() {
        SQLiteDatabase d = mDb.getReadableDatabase();
        ArrayList<Programmazione> res = new ArrayList<>();

        Cursor c = d.query(DatabaseContract.ProgrammazioneContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (c.moveToNext()) {
            res.add(programmazioneFromContentValues(c));
        }

        c.close();
        return res;
    }

    /**
     * Restituisce tutte le programmazioni dal database
     * @return le programmazioni dal database
     */
    public static ArrayList<Programmazione> getProgrammaziones(int idFilm) {
        SQLiteDatabase d = mDb.getReadableDatabase();
        ArrayList<Programmazione> res = new ArrayList<>();

        Cursor c = d.query(DatabaseContract.ProgrammazioneContract.TABLE_NAME,
                null,
                DatabaseContract.ProgrammazioneContract.ID_FILM + "=?",
                new String[]{idFilm+""},
                null,
                null,
                null);

        while (c.moveToNext()) {
            res.add(programmazioneFromContentValues(c));
        }

        c.close();
        return res;
    }

    /**
     * Metodo che restituisce una programmazione dato un id
     * @param id id della programmazione
     * @return la programmazione se esiste
     */
    public static Programmazione getProgrammmazione(int id) {
        SQLiteDatabase d = mDb.getReadableDatabase();
        String[] args = {String.valueOf(id)};

        Cursor c = d.query(DatabaseContract.ProgrammazioneContract.TABLE_NAME,
                null,
                DatabaseContract.ProgrammazioneContract._ID + "=?",
                args,
                null,
                null,
                null);

        Programmazione p = null;

        while (c.moveToNext()) {
            p = programmazioneFromContentValues(c);
        }

        c.close();
        return p;
    }

    /**
     * Converte un contentvalues in programmazione
     * @param c cursore
     * @return una programmazione
     */
    private static Programmazione programmazioneFromContentValues(Cursor c) {
        Programmazione p = new Programmazione();

        p.setId(c.getInt(c.getColumnIndex(DatabaseContract.ProgrammazioneContract._ID)));
        p.setIdFilm(c.getInt(c.getColumnIndex(DatabaseContract.ProgrammazioneContract.ID_FILM)));
        p.setIdSala(c.getInt(c.getColumnIndex(DatabaseContract.ProgrammazioneContract.ID_SALA)));
        p.setData(c.getString(c.getColumnIndex(DatabaseContract.ProgrammazioneContract.DATA)));
        p.setOra(c.getString(c.getColumnIndex(DatabaseContract.ProgrammazioneContract.ORA)));

        return p;
    }

    /**
     * Converte un oggett Programmazione in un contentvalues
     * @param p programmazione
     * @return contentvalues
     */
    private static ContentValues programmazioneToContentValues(Programmazione p) {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseContract.ProgrammazioneContract._ID, p.getId());
        cv.put(DatabaseContract.ProgrammazioneContract.ID_FILM, p.getIdFilm());
        cv.put(DatabaseContract.ProgrammazioneContract.ID_SALA, p.getIdSala());
        cv.put(DatabaseContract.ProgrammazioneContract.DATA, p.getData());
        cv.put(DatabaseContract.ProgrammazioneContract.ORA, p.getOra());

        return cv;
    }
}
