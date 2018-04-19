package com.grupppofigo.progettocinema.queries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaCas;
import android.util.Log;

import com.grupppofigo.progettocinema.database.DBHelper;
import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.database.DatabaseContract.SessioniContract;
import com.grupppofigo.progettocinema.entities.Sessione;

import java.util.ArrayList;

public class SessioneQueries {
    private static DBHelper mDb = DataStore.getDB();

    /**
     * Crea una sessione nel database mettendo una data e tempo di inizio
     * @return l'id della sessione creata
     */
    public static long startSession() {
        long id = 0;
        SQLiteDatabase d = mDb.getWritableDatabase();

        ContentValues cv = sessioneToCV(0,String.valueOf(System.currentTimeMillis()), null);
        cv.remove(SessioniContract.END_SESSION);
        cv.remove(SessioniContract._ID);

        try {
            id = d.insert(SessioniContract.TABLE_NAME,
                    null,
                    cv);
        }catch (SQLiteException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    /**
     * Metodo per far terminare una sessione mettendo l'endSession con
     * il tempo in millisecondi del momento della chiamata del metodo
     * @param idSession id della sessione da fare finire
     */
    public static void endSession(long idSession) {
        SQLiteDatabase d = mDb.getWritableDatabase();

        ContentValues cv = sessioneToCV(idSession,null, String.valueOf(System.currentTimeMillis()));
        cv.remove(SessioniContract.START_SESSION);
        Log.d("CV", cv.toString());

        try {
            d.update(SessioniContract.TABLE_NAME,
                    cv,
                    SessioniContract._ID + "=?",
                    new String[]{idSession+""});
        }catch (SQLiteException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Converte una sessione in content values
     * @param id id sessione
     * @param startTime start time
     * @param endTime end time
     * @return oggetto content values
     */
    private static ContentValues sessioneToCV(long id, String startTime, String endTime) {
        ContentValues cv = new ContentValues();

        cv.put(SessioniContract._ID, id);
        cv.put(SessioniContract.START_SESSION, startTime);
        cv.put(SessioniContract.END_SESSION, endTime);
        return cv;
    }

    /**
     * Restituisce una lista di tutte le sessioni
     * @return lista delle sessioni
     */
    public static ArrayList<Sessione> getAllSessioni() {
        SQLiteDatabase d = mDb.getReadableDatabase();
        ArrayList<Sessione> res = new ArrayList<>();

        Cursor c = d.query(SessioniContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (c.moveToNext()) {
            res.add(sessioneFromCursor(c));
        }

        c.close();
        return res;
    }


    private static Sessione sessioneFromCursor(Cursor c) {
        Sessione s = new Sessione();

        s.setId(c.getInt(c.getColumnIndex(SessioniContract._ID)));
        s.setStartSessione(c.getString(c.getColumnIndex(SessioniContract.START_SESSION)));
        s.setEndSession(c.getString(c.getColumnIndex(SessioniContract.END_SESSION)));

        return s;
    }
}
