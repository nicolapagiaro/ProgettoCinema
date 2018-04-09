package com.grupppofigo.progettocinema.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe per il DBHelper
 * Created by Nicola on 28/03/2018.
 */
public class DBHelper extends SQLiteOpenHelper {
    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "cinema.db";

    /**
     * Costruttore parametrico
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.CREATE_TABLE_FILM);
        db.execSQL(DatabaseContract.CREATE_TABLE_PROGRAMMAZIONE);
        db.execSQL(DatabaseContract.CREATE_TABLE_SALA);
        db.execSQL(DatabaseContract.CREATE_TABLE_PRENOTAZIONE);
        db.execSQL(DatabaseContract.CREATE_TABLE_POSTI_PRENOTATI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.DELETE_TABLE_FILM);
        db.execSQL(DatabaseContract.DELETE_TABLE_PROGRAMMAZIONE);
        db.execSQL(DatabaseContract.DELETE_TABLE_SALA);
        db.execSQL(DatabaseContract.DELETE_TABLE_PRENOTAZIONE);
        db.execSQL(DatabaseContract.DELETE_TABLE_POSTI_PRENOTATI);

        onCreate(db);
    }
}