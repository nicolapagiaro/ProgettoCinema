package com.grupppofigo.progettocinema;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.entities.Genere;
import com.grupppofigo.progettocinema.entities.Programmazione;
import com.grupppofigo.progettocinema.entities.Sala;
import com.grupppofigo.progettocinema.queries.FilmQueries;
import com.grupppofigo.progettocinema.queries.GenereQueries;
import com.grupppofigo.progettocinema.queries.ProgrammazioneQueries;
import com.grupppofigo.progettocinema.queries.SalaQueries;

/**
 * Classe dell'applicazione
 * Created by Nicola on 09/04/2018.
 */
public class CinemaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // init del database
        DataStore.init(getApplicationContext());

        // robe random
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(!sharedPreferences.getBoolean("first", false)) {
            fakeData();
            SharedPreferences.Editor ed = sharedPreferences.edit();
            ed.putBoolean("first", true);
            ed.apply();
        }
    }

    /**
     * Genera film e generi random
     */
    private void fakeData() {
        // categorie random
        Genere g1 = new Genere(0, "Azione");
        Genere g2 = new Genere(0, "Animazione");
        Genere g3 = new Genere(0, "Comico");

        GenereQueries.addGenere(g1);
        GenereQueries.addGenere(g2);
        GenereQueries.addGenere(g3);

        // film random
        Film f1 = new Film(0,
                "The Avengers",
                124,
                new Genere(1,""),
                "Mitico film della marvel, supereroi wow",
                4,
                "https://i.annihil.us/u/prod/marvel/i/mg/6/50/521f70b81f7d3/portrait_incredible.jpg");

        Film f2 = new Film(0,
                "Tre uomini ed una gamba",
                101,
                new Genere(3,""),
                "Gran bel film",
                5,
                "https://pad.mymovies.it/filmclub/2006/06/251/locandina.jpg");

        FilmQueries.addFilm(f1);
        FilmQueries.addFilm(f2);

        // sala a caso
        SalaQueries.addSala(new Sala(0, "Sala rossa", 80));

        // programmazioni a caso
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "4/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "4/05/2018", "19:30"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "4/05/2018", "20:30"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "5/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "5/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "5/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "6/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "6/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "6/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "7/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "7/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "7/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "8/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "8/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "8/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "9/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "9/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "9/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "10/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "10/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "10/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "11/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "11/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "11/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "12/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "12/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "12/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "13/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "13/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "13/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "14/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "14/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "14/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "15/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "15/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "15/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "16/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "16/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "16/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "17/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "17/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "17/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "18/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "18/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "18/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "19/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "19/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "19/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "20/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "20/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "20/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "21/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "21/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "21/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "22/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "22/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "22/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "23/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "23/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "23/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "24/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "24/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "24/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "25/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "25/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "25/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "26/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "26/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "26/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "27/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "27/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "27/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "28/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "28/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "28/05/2018", "19:45"));

        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "29/05/2018", "18:25"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "29/05/2018", "18:55"));
        ProgrammazioneQueries.addProgrammazione(new Programmazione(0,1, 1, "29/05/2018", "19:45"));

    }
}
