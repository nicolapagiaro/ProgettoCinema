package com.grupppofigo.progettocinema.queries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.grupppofigo.progettocinema.database.DBHelper;
import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.Film;

import java.util.ArrayList;

/**
 * Classe che contiene tutte le query per i film
 */
public class FilmQueries {
    private static DBHelper mDb = DataStore.getDB();

    /**
     * Aggiunge un film al database
     * @param f oggetto film
     */
    public static void addFilm(Film f) {
        // prendo l'oggetto scrivibile del database
        SQLiteDatabase d = mDb.getWritableDatabase();

        // tolgo l'id perché è autogenerato
        ContentValues cv = filmToContentValues(f);
        cv.remove(DatabaseContract.FilmContract._ID);

        // provo ad inserire il film
        d.beginTransaction();
        try {
            d.insert(DatabaseContract.FilmContract.TABLE_NAME,
                    null,
                    cv);
            d.setTransactionSuccessful();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            d.endTransaction();
        }
    }

    /**
     * Restituisce una lista di film
     * @return lista di film
     */
    public static ArrayList<Film> getAllFilms() {
        SQLiteDatabase d = mDb.getReadableDatabase();
        ArrayList<Film> res = new ArrayList<>();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DatabaseContract.FilmContract.TABLE_NAME + " INNER JOIN " + DatabaseContract.GeneriContract.TABLE_NAME
                + " ON " + DatabaseContract.FilmContract.ID_GENERE + "=" + DatabaseContract.GeneriContract.TABLE_NAME
                + "." + DatabaseContract.GeneriContract._ID);

        Cursor c = builder.query(d,
                null,
                null,
                null,
                null,
                null,
                null);

        while (c.moveToNext()) {
            res.add(filmFromCursor(c));
        }

        c.close();
        return res;
    }


    /**
     * Converte l'oggetto film in un oggetto contentvalues
     * @param f film da convertire
     * @return oggetto contentvalues
     */
    private static ContentValues filmToContentValues(Film f) {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseContract.FilmContract._ID, f.getId());
        cv.put(DatabaseContract.FilmContract.TITOLO, f.getTitolo());
        cv.put(DatabaseContract.FilmContract.DURATA, f.getDurata());
        cv.put(DatabaseContract.FilmContract.DESCRIZIONE, f.getDescrizione());
        cv.put(DatabaseContract.FilmContract.ID_GENERE, f.getGenere().getId());
        cv.put(DatabaseContract.FilmContract.VOTO, f.getVoto());
        cv.put(DatabaseContract.FilmContract.IMMAGINE, f.getImmagine());

        return cv;
    }

    /**
     * Da un cursore restituisce il film
     * @param c cursore
     * @return oggetto film dal cursore
     */
    private static Film filmFromCursor(Cursor c) {
        Film f = new Film();
        f.setId(c.getInt(c.getColumnIndex(DatabaseContract.FilmContract._ID)));
        f.setTitolo(c.getString(c.getColumnIndex(DatabaseContract.FilmContract.TITOLO)));
        f.setGenere(GenereQueries.genereFromCursor(c));
        f.setDurata(c.getInt(c.getColumnIndex(DatabaseContract.FilmContract.DURATA)));
        f.setDescrizione(c.getString(c.getColumnIndex(DatabaseContract.FilmContract.DESCRIZIONE)));
        f.setImmagine(c.getString(c.getColumnIndex(DatabaseContract.FilmContract.IMMAGINE)));
        f.setVoto(c.getInt(c.getColumnIndex(DatabaseContract.FilmContract.VOTO)));

        return f;
    }
}
