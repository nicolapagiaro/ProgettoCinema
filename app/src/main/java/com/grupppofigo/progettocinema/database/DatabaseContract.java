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
            + ProgrammazioneContract.ID_FILM + " INTEGER,"
            + ProgrammazioneContract.ID_SALA + " INTEGER,"
            + ProgrammazioneContract.DATA + " TEXT,"
            + ProgrammazioneContract.ORA + " TEXT)";

    static final String DELETE_TABLE_PROGRAMMAZIONE = "DROP TABLE "
            + FilmContract.TABLE_NAME + " IF EXIST";

    class ProgrammazioneContract {
        static final String TABLE_NAME = "programmazione";
        static final String _ID = "_id";
        static final String ID_FILM = "idFilm";
        static final String ID_SALA = "idSala";
        static final String DATA = "data";
        static final String ORA = "ora";
    }

    /**
     * Specifiche della tabella della SALA
     */
    static final String CREATE_TABLE_SALA = "CREATE TABLE "
            + SalaContract.TABLE_NAME + "("
            + SalaContract._ID + " INTEGER PRIMARY KEY,"
            + SalaContract.NOME + " TEXT,"
            + SalaContract.NUMERO_POSTI + " INTEGER)";

    static final String DELETE_TABLE_SALA = "DROP TABLE "
            + SalaContract.TABLE_NAME + " IF EXIST";

    class SalaContract {
        static final String TABLE_NAME = "sala";
        static final String _ID = "_id";
        static final String NOME = "nome";
        static final String NUMERO_POSTI = "numeroPosti";
    }

    /**
     * Specifiche della tabella della PRENOTAZIONE
     */
    static final String CREATE_TABLE_PRENOTAZIONE = "CREATE TABLE "
            + PrenotazioneContract.TABLE_NAME + "("
            + PrenotazioneContract._ID + " INTEGER PRIMARY KEY,"
            + PrenotazioneContract.ID_UTENTE + " INTEGER,"
            + PrenotazioneContract.ID_PROGRAMMAZIONE + " INTEGER)";

    static final String DELETE_TABLE_PRENOTAZIONE = "DROP TABLE "
            + PrenotazioneContract.TABLE_NAME + " IF EXIST";

    class PrenotazioneContract {
        static final String TABLE_NAME = "prenotazione";
        static final String _ID = "_id";
        static final String ID_UTENTE = "idUtente";
        static final String ID_PROGRAMMAZIONE = "idProgrammazione";
    }

    /**
     * Specifiche della tabella della POSTI PRENOTATI
     */
    static final String CREATE_TABLE_POSTI_PRENOTATI = "CREATE TABLE "
            + PostiPrenotatiContract.TABLE_NAME + "("
            + PostiPrenotatiContract._ID + " INTEGER PRIMARY KEY,"
            + PostiPrenotatiContract.ID_PRENOTAZIONE + " INTEGER,"
            + PostiPrenotatiContract.NUMERO_POSTO + " INTEGER)";

    static final String DELETE_TABLE_POSTI_PRENOTATI = "DROP TABLE "
            + PrenotazioneContract.TABLE_NAME + " IF EXIST";

    class PostiPrenotatiContract {
        static final String TABLE_NAME = "prenotazione";
        static final String _ID = "_id";
        static final String ID_PRENOTAZIONE = "idUtente";
        static final String NUMERO_POSTO = "numeroPosto";
    }

}

