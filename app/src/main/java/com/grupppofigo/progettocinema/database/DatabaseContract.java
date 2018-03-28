package com.grupppofigo.progettocinema.database;

/**
 * Classe che contiene tutte le specifiche del database
 * Created by Nicola on 28/03/2018.
 */
public class DatabaseContract {

    /**
     * Specifiche della tabella del FILM
     */
    static final String CREATE_TABLE_FILM = "CREATE TABLE "
            + FilmContract.TABLE_NAME + "("
            + FilmContract._ID + " INTEGER PRIMARY KEY,"
            + FilmContract.TITOLO + " TEXT,"
            + FilmContract.DURATA + " TEXT,"
            + FilmContract.GENERE + " TEXT,"
            + FilmContract.DESCRIZIONE + " TEXT,"
            + FilmContract.IMMAGINE + " BLOB)";

    static final String DELETE_TABLE_FILM = "DROP TABLE "
            + FilmContract.TABLE_NAME + " IF EXIST";

    class FilmContract {
        static final String TABLE_NAME = "film";
        static final String _ID = "_id";
        static final String TITOLO = "titolo";
        static final String DURATA = "durata";
        static final String GENERE = "genere";
        static final String DESCRIZIONE = "descrizione";
        static final String IMMAGINE = "immagine";
    }

    /**
     * Specifiche della tabella della PROGRAMMAZIONE
     */
    static final String CREATE_TABLE_PROGRAMMAZIONE = "CREATE TABLE "
            + ProgrammazioneContract.TABLE_NAME + "("
            + ProgrammazioneContract._ID + " INTEGER PRIMARY KEY,"
            + ProgrammazioneContract.TITOLO + " TEXT,"
            + ProgrammazioneContract.DURATA + " TEXT,"
            + ProgrammazioneContract.GENERE + " TEXT,"
            + ProgrammazioneContract.DESCRIZIONE + " TEXT,"
            + ProgrammazioneContract.IMMAGINE + " BLOB)";

    static final String DELETE_TABLE_PROGRAMMAZIONE = "DROP TABLE "
            + FilmContract.TABLE_NAME + " IF EXIST";

    class ProgrammazioneContract {
        static final String TABLE_NAME = "film";
        static final String _ID = "_id";
        static final String TITOLO = "titolo";
        static final String DURATA = "durata";
        static final String GENERE = "genere";
        static final String DESCRIZIONE = "descrizione";
        static final String IMMAGINE = "immagine";
    }
}
