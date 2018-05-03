package com.grupppofigo.progettocinema.queries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.grupppofigo.progettocinema.database.DBHelper;
import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.Giorno;
import com.grupppofigo.progettocinema.entities.Programmazione;
import com.grupppofigo.progettocinema.entities.Programmazioni;

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
    public static Programmazioni getProgrammaziones(int idFilm) {
        SQLiteDatabase d = mDb.getReadableDatabase();
        Programmazioni res= new Programmazioni();

        // prendo tutte le date
        Cursor c = d.query(true, DatabaseContract.ProgrammazioneContract.TABLE_NAME,
                new String[]{DatabaseContract.ProgrammazioneContract.DATA},
                DatabaseContract.ProgrammazioneContract.ID_FILM + "=?",
                new String[]{idFilm+""},
                null,
                null,
                null,
                null);
        while (c.moveToNext()) {
            Giorno g = new Giorno();
            g.setData(c.getString(0));
            res.getGiorni().add(g);
        }
        c.close();

        // prendo le date di tutti gli orari
        for (int i=0; i<res.getGiorni().size(); i++) {
            Cursor cTemp = d.query(DatabaseContract.ProgrammazioneContract.TABLE_NAME,
                    new String[]{DatabaseContract.ProgrammazioneContract.ORA},
                    DatabaseContract.ProgrammazioneContract.ID_FILM + "=? AND " + DatabaseContract.ProgrammazioneContract.DATA + "=?",
                    new String[]{idFilm+"", res.getGiorni().get(i).getData()},
                    null,
                    null,
                    DatabaseContract.ProgrammazioneContract.ORA + " ASC",
                    null);

            while (cTemp.moveToNext()) {
                res.getGiorni().get(i).getOrari().add(cTemp.getString(0));
            }

            cTemp.close();
        }

        return res;
    }

    /**
     * Restituisce l'id della programmazione selezionata
     * @param data data selezionata
     * @param ora ora selezionata
     * @param idFilm idFilm selezionato
     * @return id
     */
    public static int getProgrammazioneId(String data, String ora, int idFilm) {
        int res = DatabaseContract.ID_NOT_FOUND;
        SQLiteDatabase d = mDb.getReadableDatabase();

        Cursor c = d.query(DatabaseContract.ProgrammazioneContract.TABLE_NAME,
                null,
                DatabaseContract.ProgrammazioneContract.ID_FILM + "=? AND "
                        + DatabaseContract.ProgrammazioneContract.DATA + "=? AND "
                        + DatabaseContract.ProgrammazioneContract.ORA + "=?",
                new String[]{idFilm+"", data, ora},
                null,
                null,
                null,
                null);

        Log.d("ResQuery", c.getCount() + "");

        while (c.moveToNext()) {
            res = (int) c.getLong(0);
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
