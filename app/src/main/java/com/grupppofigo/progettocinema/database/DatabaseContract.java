package com.grupppofigo.progettocinema.database;

/**
 * Classe che contiene tutte le specifiche del database
 * Created by Nicola on 28/03/2018.
 */
public class DatabaseContract {
    public static final int ID_NOT_FOUND = -1;

    /**
     * Specifiche della tabella SESSIONI
     */
    static final String CREATE_TABLE_SESSIONI = "CREATE TABLE "
            + SessioniContract.TABLE_NAME + "("
            + SessioniContract._ID + " INTEGER PRIMARY KEY, "
            + SessioniContract.START_SESSION + " TEXT NOT NULL, "
            + SessioniContract.END_SESSION + " TEXT,"
            + SessioniContract.ID_UTENTE + " INTEGER)";

    static final String DELETE_TABLE_SESSIONI = "DROP TABLE "
            + SessioniContract.TABLE_NAME;

    public  class SessioniContract {
        public static final String TABLE_NAME = "sessioni";
        public static final String _ID = "_id";
        public static final String START_SESSION = "startSession";
        public static final String END_SESSION = "endSession";
        public static final String ID_UTENTE = "idUtente";
    }

    /**
     * Specifiche della tabella UTENTI
     */
    static final String CREATE_TABLE_UTENTI = "CREATE TABLE "
            + UtentiContract.TABLE_NAME + "("
            + UtentiContract._ID + " INTEGER PRIMARY KEY,"
            + UtentiContract.NOME + " TEXT NOT NULL,"
            + UtentiContract.COGNOME + " TEXT NOT NULL,"
            + UtentiContract.EMAIL + " TEXT NOT NULL,"
            + UtentiContract.PASSW + " TEXT NOT NULL)";

    static final String DELETE_TABLE_UTENTI = "DROP TABLE "
            + UtentiContract.TABLE_NAME;

    public class UtentiContract {
        public static final String TABLE_NAME = "utenti";
        public static final String _ID = "_id";
        public  static final String NOME = "nome";
        public  static final String COGNOME = "cognome";
        public  static final String EMAIL = "email";
        public static final String PASSW = "passw";
    }

    /**
     * Specifiche della tabella GENERI
     */
    static final String CREATE_TABLE_GENERI = "CREATE TABLE "
            + GeneriContract.TABLE_NAME + "("
            + GeneriContract._ID + " INTEGER PRIMARY KEY,"
            + GeneriContract.NOME + " TEXT NOT NULL)";

    static final String DELETE_TABLE_NEGERI = "DROP TABLE "
            + GeneriContract.TABLE_NAME;

    public  class GeneriContract {
        public static final String TABLE_NAME = "generi";
        public static final String _ID = "_id";
        public static final String NOME = "nome";
    }


    /**
     * Specifiche della tabella del FILM
     */
    static final String CREATE_TABLE_FILM = "CREATE TABLE "
            + FilmContract.TABLE_NAME + "("
            + FilmContract._ID + " INTEGER PRIMARY KEY,"
            + FilmContract.TITOLO + " TEXT,"
            + FilmContract.DURATA + " INTEGER,"
            + FilmContract.ID_GENERE + " INTEGER,"
            + FilmContract.DESCRIZIONE + " TEXT,"
            + FilmContract.VOTO + " INTEGER,"
            + FilmContract.IMMAGINE + " TEXT)";

    static final String DELETE_TABLE_FILM = "DROP TABLE "
            + FilmContract.TABLE_NAME;

    public class FilmContract {
        public static final String TABLE_NAME = "film";
        public static final String _ID = "_id";
        public static final String TITOLO = "titolo";
        public static final String DURATA = "durata";
        public static final String ID_GENERE = "idGenere";
        public static final String DESCRIZIONE = "descrizione";
        public static final String VOTO = "voto";
        public static final String IMMAGINE = "immagine";
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
            + ProgrammazioneContract.TABLE_NAME;

    public class ProgrammazioneContract {
        public static final String TABLE_NAME = "programmazione";
        public static final String _ID = "_id";
        public static final String ID_FILM = "idFilm";
        public static final String ID_SALA = "idSala";
        public static final String DATA = "data";
        public static final String ORA = "ora";
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
            + SalaContract.TABLE_NAME;

    public class SalaContract {
        public  static final String TABLE_NAME = "sala";
        public static final String _ID = "_id";
        public static final String NOME = "nome";
        public static final String NUMERO_POSTI = "numeroPosti";
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
            + PrenotazioneContract.TABLE_NAME;

    public class PrenotazioneContract {
        public static final String TABLE_NAME = "prenotazione";
        public static final String _ID = "_id";
        public static final String ID_UTENTE = "idUtente";
        public static final String ID_PROGRAMMAZIONE = "idProgrammazione";
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
            + PostiPrenotatiContract.TABLE_NAME;

    public class PostiPrenotatiContract {
        public static final String TABLE_NAME = "postiPrenotati";
        public static final String _ID = "_id";
        public static final String ID_PRENOTAZIONE = "idUtente";
        public static final String NUMERO_POSTO = "numeroPosto";
    }

}

