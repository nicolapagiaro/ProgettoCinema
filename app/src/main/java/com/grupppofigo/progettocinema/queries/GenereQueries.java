package com.grupppofigo.progettocinema.queries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.grupppofigo.progettocinema.database.DBHelper;
import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.Genere;

import java.util.ArrayList;

/**
 * Classe con tutte le query per i generi
 */
public class GenereQueries {
    private static DBHelper mDb = DataStore.getDB();

    /**
     * Metodo che aggiunge un genere
     * @param g oggetto genere
     * @return l'id appena inserito
     */
    public static long addGenere(Genere g) {
        long id = 0;
        SQLiteDatabase d = mDb.getWritableDatabase();

        ContentValues cv = genereToContentValues(g);
        // tolgo l'id
        cv.remove(DatabaseContract.GeneriContract._ID);

        try {
            d.insert(DatabaseContract.GeneriContract.TABLE_NAME,
                    null,
                    cv);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    /**
     * Converte un oggetto Genere in un content values
     * @param g oggetto genere
     * @return oggetto content values
     */
    protected static ContentValues genereToContentValues(Genere g) {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseContract.GeneriContract._ID, g.getId());
        cv.put(DatabaseContract.GeneriContract.NOME, g.getNome());

        return cv;
    }

    /**
     * Restuisce una lista di generi dal database
     * @return una lista di generi
     */
    public static ArrayList<Genere> getAllGeneri() {
        ArrayList<Genere> res = new ArrayList<>();
        SQLiteDatabase d = mDb.getReadableDatabase();

        Cursor c = d.query(DatabaseContract.GeneriContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while(c.moveToNext()) {
            res.add(genereFromCursor(c));
        }

        c.close();
        return res;
    }

    /**
     * Converte un cursore in un genere
     * @param c cursore
     * @return oggetto genere
     */
    protected static Genere genereFromCursor(Cursor c) {
        Genere g = new Genere();

        g.setId(c.getInt(c.getColumnIndex(DatabaseContract.GeneriContract._ID)));
        g.setNome(c.getString(c.getColumnIndex(DatabaseContract.GeneriContract.NOME)));

        return g;
    }
}
