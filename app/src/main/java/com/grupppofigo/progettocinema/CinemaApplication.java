package com.grupppofigo.progettocinema;

import android.app.Application;

import com.grupppofigo.progettocinema.database.DataStore;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.entities.Genere;
import com.grupppofigo.progettocinema.queries.FilmQueries;
import com.grupppofigo.progettocinema.queries.GenereQueries;

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
        //fakeData();
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
                "The Avenger",
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
    }
}
