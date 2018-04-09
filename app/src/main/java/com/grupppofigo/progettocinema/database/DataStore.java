package com.grupppofigo.progettocinema.database;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Classe per accedere all'istanza del database da qualsiasi posizione del programma
 * Created by Nicola on 08/03/2018.
 */
public final class DataStore {
    private DataStore() {}
    private static final Executor EXEC = Executors.newSingleThreadExecutor();

    private static DBHelper db;

    public static void init(Context context) {
        db = new DBHelper(context);
    }

    public static DBHelper getDB() {
        return db;
    }

    public static void execute(Runnable runnable) {
        EXEC.execute(runnable);
    }
}
