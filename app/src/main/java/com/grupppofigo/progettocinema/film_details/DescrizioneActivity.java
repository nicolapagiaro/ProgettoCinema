package com.grupppofigo.progettocinema.film_details;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.SessionExpired;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.entities.Programmazione;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SessionValidator;
import com.grupppofigo.progettocinema.helpers.SnackBar;
import com.grupppofigo.progettocinema.queries.FilmQueries;
import com.grupppofigo.progettocinema.queries.ProgrammazioneQueries;
import com.grupppofigo.progettocinema.queries.SessioneQueries;
import com.grupppofigo.progettocinema.riassunto.ResumeActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.grupppofigo.progettocinema.helpers.ExtrasDefinition.EXTRA_DEFAULT_VALUE;
import static com.grupppofigo.progettocinema.helpers.ExtrasDefinition.ID_PROGRAMMAZIONE;
import static com.grupppofigo.progettocinema.helpers.ExtrasDefinition.ID_TOKEN;
import static com.grupppofigo.progettocinema.helpers.ExtrasDefinition.ID_UTENTE;
import static com.grupppofigo.progettocinema.helpers.ExtrasDefinition.START_SESSION;

/**
 * Activity che mostra le informazioni del film
 */
public class DescrizioneActivity extends AppCompatActivity {
    private TimeDialog vTimeDialog;
    private DayListDialog vDayListDialog;
    private TextView mSelectDate, mSelectTime;
    private ImageView mCopertina;
    private LinearLayout mLayoutOra;
    private boolean vDateSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_descrizione);

        Toolbar t = findViewById(R.id.toolbar);
        if(t != null) {
            t.setTitle(R.string.film_details_title);
            setSupportActionBar(t);

            //this line shows back button
            if(getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            t.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

        // id sessione
        final long idSessione = getIntent().getLongExtra(ExtrasDefinition.ID_TOKEN, EXTRA_DEFAULT_VALUE);
        if(idSessione == EXTRA_DEFAULT_VALUE) {
            SessionValidator.finishSession(this, idSessione);
        }

        // start della sessione
        final String startSession = getIntent().getStringExtra(ExtrasDefinition.START_SESSION);
        if(startSession == null) {
            SessionValidator.finishSession(this, idSessione);
        }
        else if(SessionValidator.isExpired(startSession)){
            // se è scaduta la registro e chiudo tutto
            SessioneQueries.endSession(idSessione);
            SessionValidator.finishSession(this, idSessione);
        }

        // id dell'utente passata dall'activity prima
        final int idUtente = getIntent().getIntExtra(ExtrasDefinition.ID_UTENTE, EXTRA_DEFAULT_VALUE);
        if(idUtente == EXTRA_DEFAULT_VALUE) {
            // errore idUtente non passato passo al login
            SessionValidator.finishSession(this, idSessione);
        }

        final int idFilm = getIntent().getIntExtra(ExtrasDefinition.ID_FILM, EXTRA_DEFAULT_VALUE);
        if(idFilm == EXTRA_DEFAULT_VALUE) {
            // errore film non passato
            SessionValidator.finishSession(this, idSessione);
        }

        // prendo il film
        Film vFilm = FilmQueries.getFilm(idFilm);
        if(vFilm == null) {
            // errore film non trovato
            SessionValidator.finishSession(this, idSessione);
        }
        final ArrayList<Programmazione> progs = ProgrammazioneQueries.getProgrammaziones(vFilm.getId());

        vDateSelected = false; // Controllo se è stata selezionata una data

        //Collego gli elementi del layout
        mCopertina = findViewById(R.id.img_header);
        mSelectDate = findViewById(R.id.txt_data);
        mSelectTime = findViewById(R.id.txt_ora);
        Button mSumbit = findViewById(R.id.btn_submit);
        mLayoutOra = findViewById(R.id.oraContainer);
        LinearLayout mLayoutGiorno = findViewById(R.id.dataContainer);
        mLayoutOra.setClickable(vDateSelected);

        //setto l'immagine di copertina
        Picasso.get()
                .load(vFilm.getImmagine())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(mCopertina, new Callback() {
                    @Override
                    public void onSuccess() {
                        showImage();
                    }

                    @Override
                    public void onError(Exception e) {}
                });

        // descrizione del film
        FragmentDesc vFrag = new FragmentDesc(vFilm);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_zone, vFrag).commit();

        // selezione del giorno
        mLayoutGiorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vDayListDialog = new DayListDialog(DescrizioneActivity.this, progs);
                vDayListDialog.show();
            }
        });

        // selezione dell'ora
        mLayoutOra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*vTimeDialog = new TimeDialog(DescrizioneActivity.this, vOre);
                vTimeDialog.show();*/
            }
        });

        // pulsante per andare avanti
        mSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent resume = new Intent(getApplicationContext(), ResumeActivity.class);
                resume.putExtra(START_SESSION, startSession);
                resume.putExtra(ID_TOKEN, idSessione);
                resume.putExtra(ID_UTENTE, idUtente);
                resume.putExtra(ID_PROGRAMMAZIONE, 0);
                startActivity(resume);*/
                Toast.makeText(getApplicationContext(), "Prenota", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setvDateSelected(boolean aDateSelected) {
        this.vDateSelected = aDateSelected;
        mLayoutOra.setClickable(aDateSelected);
    }

    public boolean isvDateSelected() {
        return vDateSelected;
    }

    public TimeDialog getvTimeDialog() {
        return vTimeDialog;
    }

    public DayListDialog getvDayListDialog() {
        return vDayListDialog;
    }

    public void changeDate(String aData) {
        mSelectDate.setText(aData);
    }

    public void changeOra(String aData) {
        mSelectTime.setText(aData);
    }

    /**
     * Mostra l'immagine con l'effetto
     */
    private void showImage() {
        AppBarLayout app_bar = findViewById(R.id.app_bar);

        // get the center for the clipping circle
        int x = app_bar.getRight() / 2;
        int y = app_bar.getBottom() / 2;

        float startRadius = 0F;
        float endRadius  = (float) Math.hypot(app_bar.getWidth(), app_bar.getHeight());

        Animator anim = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(mCopertina, x, y, startRadius, endRadius);
            mCopertina.setVisibility(View.VISIBLE);
            anim.start();
        }
    }
}

