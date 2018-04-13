package com.grupppofigo.progettocinema.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.database.DatabaseContract.FilmContract;
import com.grupppofigo.progettocinema.database.DatabaseContract.SalaContract;
import com.grupppofigo.progettocinema.database.DatabaseContract.ProgrammazioneContract;
import com.grupppofigo.progettocinema.entities.PostoPrenotato;
import com.grupppofigo.progettocinema.entities.Prenotazione;
import com.grupppofigo.progettocinema.entities.Programmazione;
import com.grupppofigo.progettocinema.entities.Sala;

import java.util.ArrayList;
import java.util.List;

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
        SQLiteDatabase d = mDb.getWritableDatabase();

        ContentValues cv = salaToContentValues(s);
        // tolgo l'id
        cv.remove(SalaContract._ID);

        d.beginTransaction();
        try {
            d.insert(SalaContract.TABLE_NAME,
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

        Cursor c = d.query(SalaContract.TABLE_NAME,
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
        Cursor cProgrammazione = d.query(ProgrammazioneContract.TABLE_NAME,
                null,
                ProgrammazioneContract._ID + " = ?",
                args,
                null,
                null,
                null);

        int idSala = 0;
        if(cProgrammazione.moveToNext()) {
            idSala = cProgrammazione.getInt(cProgrammazione.getColumnIndex(ProgrammazioneContract.ID_SALA));
        }

        cProgrammazione.close();


        // prendo la sala dall'id
        String[] args1 = {idSala+""};
        Cursor c = d.query(SalaContract.TABLE_NAME,
                null,
                SalaContract._ID + " = ?",
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

        s.setId(c.getInt(c.getColumnIndex(SalaContract._ID)));
        s.setNome(c.getString(c.getColumnIndex(SalaContract.NOME)));
        s.setnPosti(c.getInt(c.getColumnIndex(SalaContract.NUMERO_POSTI)));

        return s;
    }

    /**
     * Converte un oggetto sala in un oggetto contentvalues
     * @param s oggetto sala
     * @return oggetto contentvalues
     */
    private static ContentValues salaToContentValues(Sala s) {
        ContentValues cv = new ContentValues();

        cv.put(SalaContract._ID, s.getId());
        cv.put(SalaContract.NOME, s.getNome());
        cv.put(SalaContract.NUMERO_POSTI, s.getnPosti());

        return cv;
    }

    /**
     * Aggiunge una programmazione al database
     * @param p programmazione
     */
    public static void addProgrammazione(Programmazione p) {
        SQLiteDatabase d = mDb.getWritableDatabase();

        ContentValues cv = programmazioneToContentValues(p);
        // tolgo l'id
        cv.remove(ProgrammazioneContract._ID);

        d.beginTransaction();
        try {
            d.insert(ProgrammazioneContract.TABLE_NAME,
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

        Cursor c = d.query(ProgrammazioneContract.TABLE_NAME,
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
     * Metodo che restituisce una programmazione dato un id
     * @param id id della programmazione
     * @return la programmazione se esiste
     */
    public static Programmazione getProgrammmazione(int id) {
        SQLiteDatabase d = mDb.getReadableDatabase();
        String[] args = {String.valueOf(id)};

        Cursor c = d.query(ProgrammazioneContract.TABLE_NAME,
                null,
                ProgrammazioneContract._ID + "=?",
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

        p.setId(c.getInt(c.getColumnIndex(ProgrammazioneContract._ID)));
        p.setIdFilm(c.getInt(c.getColumnIndex(ProgrammazioneContract.ID_FILM)));
        p.setIdSala(c.getInt(c.getColumnIndex(ProgrammazioneContract.ID_SALA)));
        p.setData(c.getString(c.getColumnIndex(ProgrammazioneContract.DATA)));
        p.setOra(c.getString(c.getColumnIndex(ProgrammazioneContract.ORA)));

        return p;
    }

    /**
     * Converte un oggett Programmazione in un contentvalues
     * @param p programmazione
     * @return contentvalues
     */
    private static ContentValues programmazioneToContentValues(Programmazione p) {
        ContentValues cv = new ContentValues();

        cv.put(ProgrammazioneContract._ID, p.getId());
        cv.put(ProgrammazioneContract.ID_FILM, p.getIdFilm());
        cv.put(ProgrammazioneContract.ID_SALA, p.getIdSala());
        cv.put(ProgrammazioneContract.DATA, p.getData());
        cv.put(ProgrammazioneContract.ORA, p.getOra());

        return cv;
    }

    /**
     * Aggiunge un PostoPrenotati al database
     * @param pp postiprenotati
     */
    public static void addPostoPrenotato(PostoPrenotato pp) {
        SQLiteDatabase d = mDb.getWritableDatabase();

        ContentValues cv = postiPrenotatiToCV(pp);
        // tolgo l'id
        cv.remove(DatabaseContract.PostiPrenotatiContract._ID);

        d.beginTransaction();
        try {
            d.insert(DatabaseContract.PostiPrenotatiContract.TABLE_NAME,
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
     * Converte un oggetto PostoPrenotato to contentvalues
     * @param pp posti prenotati oggetto
     * @return oggetto contentValues
     */
    private static ContentValues postiPrenotatiToCV(PostoPrenotato pp) {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseContract.PostiPrenotatiContract._ID, pp.getId());
        cv.put(DatabaseContract.PostiPrenotatiContract.ID_PRENOTAZIONE, pp.getIdPrenotazione());
        cv.put(DatabaseContract.PostiPrenotatiContract.NUMERO_POSTO, pp.getNumeroPosto());

        return cv;
    }

    /**
     * Converte un cursore in un postoPrenotato
     * @param c cursore
     * @return un posto prenotato
     */
    private static PostoPrenotato postoPrenotatoFromCursor(Cursor c) {
        PostoPrenotato p = new PostoPrenotato();

        p.setId(c.getInt(c.getColumnIndex(DatabaseContract.PostiPrenotatiContract._ID)));
        p.setIdPrenotazione(c.getInt(c.getColumnIndex(DatabaseContract.PostiPrenotatiContract.ID_PRENOTAZIONE)));
        p.setNumeroPosto(c.getInt(c.getColumnIndex(DatabaseContract.PostiPrenotatiContract.NUMERO_POSTO)));

        return p;
    }

    /**
     * Preleva una lista di posti prenotati per quella programmazione
     * @param idProgrammazione id della programmazione passata
     * @return la lista di posti prenotati per quella programmazione
     */
    public static ArrayList<Integer> getPostiPrenotati(int idProgrammazione) {
        SQLiteDatabase d = mDb.getReadableDatabase();
        ArrayList<Integer> pp = new ArrayList<>();
        String[] args = {idProgrammazione + ""};

        // prendo tutte le prenotazioni per quealla programmazione
        Cursor c = d.query(DatabaseContract.PrenotazioneContract.TABLE_NAME,
                null,
                DatabaseContract.PrenotazioneContract.ID_PROGRAMMAZIONE + "=?",
                args,
                null,
                null,
                null);

        // per ogni prenotazione mi tiro fuori i posti prenotati
        while (c.moveToNext()) {
            int idPrenotazione = c.getInt(c.getColumnIndex(DatabaseContract.PrenotazioneContract._ID));

            // prendo tutti i posti prenotati da quella prenotazione
            pp.addAll(postiPrenotatiByPrenotazione(idPrenotazione));
        }

        c.close();
        return pp;
    }

    /**
     * Prente tutti posti prenotati da una prenotazione
     * @param idPrenotazione id della prenotazione
     * @return lista di posti occupati
     */
    private static ArrayList<Integer> postiPrenotatiByPrenotazione(int idPrenotazione) {
        ArrayList<Integer> res = new ArrayList<>();
        SQLiteDatabase d = mDb.getReadableDatabase();
        String[] args = {idPrenotazione + ""};

        Cursor c = d.query(DatabaseContract.PostiPrenotatiContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (c.moveToNext()) {
            res.add(c.getInt(c.getColumnIndex(DatabaseContract.PostiPrenotatiContract.NUMERO_POSTO)));
        }

        c.close();
        return res;
    }

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
