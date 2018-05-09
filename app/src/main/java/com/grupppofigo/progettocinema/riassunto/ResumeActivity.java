package com.grupppofigo.progettocinema.riassunto;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.entities.PostoPrenotato;
import com.grupppofigo.progettocinema.entities.Prenotazione;
import com.grupppofigo.progettocinema.entities.Programmazione;
import com.grupppofigo.progettocinema.entities.Sala;
import com.grupppofigo.progettocinema.helpers.DateParser;
import com.grupppofigo.progettocinema.helpers.ExtrasDefinition;
import com.grupppofigo.progettocinema.helpers.SessionValidator;
import com.grupppofigo.progettocinema.lista_film.MainActivity;
import com.grupppofigo.progettocinema.queries.FilmQueries;
import com.grupppofigo.progettocinema.queries.PostoPrenotatoQueries;
import com.grupppofigo.progettocinema.queries.PrenotazioneQueries;
import com.grupppofigo.progettocinema.queries.ProgrammazioneQueries;
import com.grupppofigo.progettocinema.queries.SalaQueries;
import com.grupppofigo.progettocinema.queries.SessioneQueries;

import java.text.ParseException;
import java.util.ArrayList;

import static com.grupppofigo.progettocinema.helpers.ExtrasDefinition.EXTRA_DEFAULT_VALUE;

public class ResumeActivity extends AppCompatActivity {
    private final int SNACKBAR_CONFERMA_DURATION = 2000;
    private final int ANIMATION_DURATION = 550;
    private final int SECOND_ANIM_DURATION = ANIMATION_DURATION + 2000;
    private static final int VIBRATION_DURATE = 500;
    private ConstraintLayout prenotatoContainer;
    private boolean isBigliettoComprato = false;
    private long idSessione;
    private String startSession;
    private int idUtente;
    private ArrayList<Integer> posti;
    private boolean daAcquistare = true, isAnimating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        // id sessione
        idSessione = getIntent().getLongExtra(ExtrasDefinition.ID_TOKEN, EXTRA_DEFAULT_VALUE);
        if (idSessione == EXTRA_DEFAULT_VALUE) {
            SessionValidator.finishSession(this, idSessione);
        }

        // start della sessione
        startSession = getIntent().getStringExtra(ExtrasDefinition.START_SESSION);
        if (startSession == null) {
            SessionValidator.finishSession(this, idSessione);
        } else if (SessionValidator.isExpired(startSession)) {
            // se è scaduta la registro e chiudo tutto
            SessioneQueries.endSession(idSessione);
            SessionValidator.finishSession(this, idSessione);
        }

        // id della programmazione
        final int idProgrammazione = getIntent().getIntExtra(ExtrasDefinition.ID_PROGRAMMAZIONE, EXTRA_DEFAULT_VALUE);
        if (idProgrammazione == EXTRA_DEFAULT_VALUE) {
            // errore idProgrammazione non passata
            SessionValidator.finishSession(this, idSessione);
        }

        // id dell'utente passata dall'activity prima
        idUtente = getIntent().getIntExtra(ExtrasDefinition.ID_UTENTE, EXTRA_DEFAULT_VALUE);
        if (idUtente == EXTRA_DEFAULT_VALUE) {
            // errore idUtente non passato passo al login
            SessionValidator.finishSession(this, idSessione);
        }

        // array di posti prenotati
        posti = getIntent().getIntegerArrayListExtra(ExtrasDefinition.POSTI_PRENOTARE);
        if (posti == null || posti.isEmpty()) {
            // errore di qualosa
            SessionValidator.finishSession(this, idSessione);
        }

        // riempio lo schermo con i dati
        TextView tvTitolo = findViewById(R.id.tvTitolo);
        TextView tvData = findViewById(R.id.tvData);
        TextView tvOra = findViewById(R.id.tvOrarioLabel);
        TextView tvSala = findViewById(R.id.tvSala);
        TextView tvIdSessione = findViewById(R.id.tvId);
        TextView tvPostiPrenotati = findViewById(R.id.tvPosti);
        tvIdSessione.setText("" + idSessione);
        ConstraintLayout resumeContainer = findViewById(R.id.resumeContainer);
        prenotatoContainer = findViewById(R.id.doneReveal);

        final ImageView paidTicket = findViewById(R.id.paidTicket);

        // prendo le robe
        Programmazione pr = ProgrammazioneQueries.getProgrammmazione(idProgrammazione);
        if (pr == null) {
            SessionValidator.finishSession(this, idSessione);
        }

        Film film = FilmQueries.getFilm(pr.getIdFilm());
        Sala s = SalaQueries.getSala(pr.getIdSala());
        tvSala.setText(s.getNome());

        if (film != null) {
            tvTitolo.setText(film.getTitolo());
            try {
                tvData.setText(DateParser.getFormattedDate(pr.getData()));
            } catch (ParseException e) {
                tvData.setText(pr.getData());
            }
            tvOra.setText(pr.getOra());
        }

        // cose con i posti con i posti
        String charDelimit = "- ";
        StringBuilder postiString = new StringBuilder();
        for (int i = 0; i < posti.size(); i++) {
            postiString.append(posti.get(i));
            if (i != posti.size() - 1) {
                postiString.append(charDelimit);
            }
        }
        tvPostiPrenotati.setText(postiString.toString());

        // mostro il suggerimento
        Snackbar.make(findViewById(R.id.resume_container_1), R.string.hintPrenotazione, Snackbar.LENGTH_LONG).show();

        // QR code
        ImageView qrCode = findViewById(R.id.qrCode);
        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialog = getLayoutInflater().inflate(R.layout.dialog_show_qr_barcode, null);
                ((ImageView) dialog).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.qr_code));
                new AlertDialog.Builder(ResumeActivity.this)
                        .setView(dialog)
                        .show();
            }
        });

        // Barcode
        ImageView barcode = findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialog = getLayoutInflater().inflate(R.layout.dialog_show_qr_barcode, null);
                ((ImageView) dialog).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.barcode));
                new AlertDialog.Builder(ResumeActivity.this)
                        .setView(dialog)
                        .show();
            }
        });

        // quando clicca sul biglietto lo acquista
        resumeContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // se non è già acquistato
                if (!isBigliettoComprato) {
                    daAcquistare = true;

                    //vibrazione
                    Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if(vibe != null) {
                        vibe.vibrate(VIBRATION_DURATE);
                    }
                    // faccio l'animazione
                    doRevealAnimation();

                    //mostra la snackbar
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar snack = Snackbar.make(findViewById(R.id.resume_container_1), R.string.snackConfermaPrenotazione, Snackbar.LENGTH_LONG);
                            snack.setAction(android.R.string.cancel, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    daAcquistare = false;
                                }
                            });
                            snack.setDuration(SNACKBAR_CONFERMA_DURATION);
                            snack.show();

                            // prenoto veramente
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(daAcquistare) {
                                        // registro la PRENOTAZIONE
                                        long idPrenotazione = PrenotazioneQueries.addPrenotazione(new Prenotazione(0, idProgrammazione, idUtente));

                                        // scrivo nel db le modifiche
                                        for (Integer index : posti) {
                                            PostoPrenotato p = new PostoPrenotato(0, (int) idPrenotazione, index);
                                            PostoPrenotatoQueries.addPostoPrenotato(p);
                                        }

                                        isBigliettoComprato = !isBigliettoComprato;
                                        paidTicket.setVisibility(View.VISIBLE);
                                        isAnimating = false;
                                    }
                                    else {
                                        Snackbar.make(findViewById(R.id.resume_container_1), R.string.snackAnnullataPrenotazione, Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            }, SNACKBAR_CONFERMA_DURATION);
                        }
                    }, ANIMATION_DURATION + SECOND_ANIM_DURATION);
                }

                return true;
            }
        });

        resumeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (isBigliettoComprato) {
                Snackbar.make(findViewById(R.id.resume_container_1), R.string.hintPrenotazioneEffettuata, Snackbar.LENGTH_LONG).show();

            } else {
                Snackbar.make(findViewById(R.id.resume_container_1), R.string.hintPrenotazione, Snackbar.LENGTH_LONG).show();
            }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isAnimating) return;

        if (!isBigliettoComprato) {
            // mostro un messaggio di avviso
            new AlertDialog.Builder(this)
                    .setTitle(R.string.exitPrenotazioneDialogTitle)
                    .setMessage(R.string.exitPrenotazioneDialogDescr)
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ResumeActivity.super.onBackPressed();
                        }
                    })
                    .create()
                    .show();
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(ExtrasDefinition.START_SESSION, startSession);
            intent.putExtra(ExtrasDefinition.ID_UTENTE, idUtente);
            intent.putExtra(ExtrasDefinition.ID_TOKEN, idSessione);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    /**
     * Fa l'animazione quando viene acquistato il biglietto
     */
    private void doRevealAnimation() {
        isAnimating = true;
        final LinearLayout container = findViewById(R.id.resumeMainContainer);

        // get the center for the clipping circle
        final int x = container.getRight() / 2;
        final int y = container.getBottom() / 2;

        final float startRadius = 0F;
        final float endRadius = (float) Math.hypot(container.getWidth(), container.getHeight());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(prenotatoContainer, x, y, startRadius, endRadius);
            anim.setDuration(ANIMATION_DURATION);
            anim.start();
        }

        final ScrollView viewToHide = findViewById(R.id.resume_container_1);
        viewToHide.setVisibility(View.GONE);
        prenotatoContainer.setVisibility(View.VISIBLE);

        // dopo due secondi nascondo tutto
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    float startRadius = (float) Math.hypot(container.getWidth(), container.getHeight());
                    float endRadius = 0F;
                    Animator anim = ViewAnimationUtils.createCircularReveal(prenotatoContainer, x, y, startRadius, endRadius);
                    anim.setDuration(ANIMATION_DURATION);
                    anim.start();

                    anim.addListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            prenotatoContainer.setVisibility(View.GONE);
                            viewToHide.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }
        }, SECOND_ANIM_DURATION);
    }
}
