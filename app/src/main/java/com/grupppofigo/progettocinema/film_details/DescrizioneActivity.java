package com.grupppofigo.progettocinema.film_details;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.database.DatabaseContract;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.entities.Programmazioni;
import com.grupppofigo.progettocinema.helpers.DateParser;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SessionValidator;
import com.grupppofigo.progettocinema.prenotazione_posti.PostiActivity;
import com.grupppofigo.progettocinema.queries.FilmQueries;
import com.grupppofigo.progettocinema.queries.ProgrammazioneQueries;
import com.grupppofigo.progettocinema.queries.SessioneQueries;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

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
    private int selectedDatePosition;
    private String selectedDate = "", selectedTime = "";

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
        final Film vFilm = FilmQueries.getFilm(idFilm);
        if(vFilm == null) {
            // errore film non trovato
            SessionValidator.finishSession(this, idSessione);
        }

        // prendo le programmazoni
        final Programmazioni progs = ProgrammazioneQueries.getProgrammaziones(vFilm.getId());


        //Collego gli elementi del layout
        mCopertina = findViewById(R.id.img_header);
        mSelectDate = findViewById(R.id.txt_data);
        mSelectTime = findViewById(R.id.txt_ora);
        Button mSumbit = findViewById(R.id.btn_submit);
        LinearLayout mLayoutOra = findViewById(R.id.oraContainer);
        LinearLayout mLayoutGiorno = findViewById(R.id.dataContainer);

        //setto l'immagine di copertina
        Picasso.get()
                .load(vFilm.getImmagine())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(mCopertina, new Callback() {
                    @Override
                    public void onSuccess() {
                        if(ViewCompat.isAttachedToWindow(mCopertina))
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
                vDayListDialog = new DayListDialog(DescrizioneActivity.this, progs.getGiorni());
                vDayListDialog.show();
            }
        });

        // selezione dell'ora
        mLayoutOra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vTimeDialog = new TimeDialog(DescrizioneActivity.this, progs.getGiorni().get(selectedDatePosition).getOrari());
                vTimeDialog.show();
            }
        });

        // metto il primo giorno selezionato ae la prima data
        try {
            if(progs != null && progs.getGiorni().size() > 0) {
                mSelectDate.setText(DateParser.getFormattedDate(progs.getGiorni().get(0).getData()));
                selectedDate = progs.getGiorni().get(0).getData();
            }
        } catch (ParseException e) {
            mSelectDate.setText("");
        }

        // pulsante per andare avanti
        mSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // controllo la selezione della data e dell'orea
                if(!selectedDate.isEmpty() && !selectedTime.isEmpty()) {
                    int idProgrammazione = ProgrammazioneQueries.getProgrammazioneId(selectedDate, selectedTime, vFilm.getId());

                    // se l'id è valido
                    if(idProgrammazione != DatabaseContract.ID_NOT_FOUND) {
                        Intent resume = new Intent(getApplicationContext(), PostiActivity.class);
                        resume.putExtra(START_SESSION, startSession);
                        resume.putExtra(ID_TOKEN, idSessione);
                        resume.putExtra(ID_UTENTE, idUtente);
                        resume.putExtra(ID_PROGRAMMAZIONE, idProgrammazione);
                        startActivity(resume);
                    }
                    else {
                        ConstraintLayout layout = findViewById(R.id.bottomContainer);
                        Snackbar.make(layout, R.string.prompt_error_general, Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    ConstraintLayout layout = findViewById(R.id.bottomContainer);
                    Snackbar.make(layout, R.string.prompt_error_prenotazione, Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate, int position) {
        this.selectedDate = selectedDate;
        this.selectedDatePosition = position;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public TimeDialog getvTimeDialog() {
        return vTimeDialog;
    }

    public DayListDialog getvDayListDialog() {
        return vDayListDialog;
    }

    public void changeDate(String aData) {
        mSelectDate.setText(aData);
        mSelectTime.setText("");
        selectedTime = "";
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(mCopertina, x, y, startRadius, endRadius);
            anim.start();
        }

        mCopertina.setVisibility(View.VISIBLE);
    }
}

