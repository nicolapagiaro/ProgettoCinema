package com.grupppofigo.progettocinema.queries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.grupppofigo.progettocinema.database.DBHelper;
import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.PostoPrenotato;

import java.util.ArrayList;

/**
 * Classe con tutte le query per i PostiPrenotati
 */
public class PostoPrenotatoQueries {
    private static DBHelper mDb = DataStore.getDB();

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
    public static ArrayList<Integer> postiPrenotatiByPrenotazione(int idPrenotazione) {
        ArrayList<Integer> res = new ArrayList<>();
        SQLiteDatabase d = mDb.getReadableDatabase();
        String[] args = {idPrenotazione + ""};

        Cursor c = d.query(DatabaseContract.PostiPrenotatiContract.TABLE_NAME,
                null,
                DatabaseContract.PostiPrenotatiContract.ID_PRENOTAZIONE + "=?",
                args,
                null,
                null,
                null);

        while (c.moveToNext()) {
            res.add(c.getInt(c.getColumnIndex(DatabaseContract.PostiPrenotatiContract.NUMERO_POSTO)));
        }

        c.close();
        return res;
    }
}
