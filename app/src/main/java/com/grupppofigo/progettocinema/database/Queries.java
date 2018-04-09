package com.grupppofigo.progettocinema.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.database.DatabaseContract.FilmContract;
import com.grupppofigo.progettocinema.entities.Sala;

import java.util.ArrayList;

/**
 * Classe con tutti i metodi che fanno le query al database
 * Created by Nicola on 09/04/2018.
 */
public class Queries {
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
        cv.remove(FilmContract._ID);

        // provo ad inserire il film
        d.beginTransaction();
        try {
            d.insert(FilmContract.TABLE_NAME,
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

        Cursor c = d.query(FilmContract.TABLE_NAME,
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
        // creo un contenitore di valori con tutti i campi
        // da inserire nella tabella FILM
        ContentValues cv = new ContentValues();

        cv.put(FilmContract._ID, f.getId());
        cv.put(FilmContract.TITOLO, f.getTitolo());
        cv.put(FilmContract.DURATA, f.getDurata());
        cv.put(FilmContract.DESCRIZIONE, f.getDescrizione());
        cv.put(FilmContract.GENERE, f.getGenere());

        return cv;
    }

    /**
     * Da un cursore restituisce il film
     * @param c cursore
     * @return oggetto film dal cursore
     */
    private static Film filmFromCursor(Cursor c) {
        Film f = new Film();

        f.setId(c.getInt(c.getColumnIndex(FilmContract._ID)));
        f.setTitolo(c.getString(c.getColumnIndex(FilmContract.TITOLO)));
        f.setGenere(c.getString(c.getColumnIndex(FilmContract.GENERE)));
        f.setDurata(c.getInt(c.getColumnIndex(FilmContract.DURATA)));
        f.setDescrizione(c.getString(c.getColumnIndex(FilmContract.DESCRIZIONE)));

        return f;
    }


    /**
     * Aggiugne una sala al database
     * @param s sala da aggiunger
     */
    public static void addSala(Sala s) {

    }
}
