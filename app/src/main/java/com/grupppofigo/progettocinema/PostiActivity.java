package com.grupppofigo.progettocinema;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.grupppofigo.progettocinema.database.Queries;
import com.grupppofigo.progettocinema.entities.PostoPrenotato;
import com.grupppofigo.progettocinema.entities.Prenotazione;
import com.grupppofigo.progettocinema.entities.Sala;

import java.util.ArrayList;

public class PostiActivity extends AppCompatActivity {
    /**
     * Span del grid della lista dei posti
     */
    private static final int GRID_SPAN_COUNT = 8;

    /**
     * Gestione degli extra
     */
    private static final int EXTRA_DEFAULT_VALUE = -1;
    private static final String PROGRAMMAZIONE_EXTRA_NAME = "idProgrammazione";
    private static final String UTENTE_EXTRA_NAME = "idUtente";

    /**
     * Gestione della prenotazione
     */
    private ArrayList<Integer> postiDaPrenotare;
    private ArrayList<Integer> postiPrenotati;

    /**
     * Extras passati
     */
    private int idProgrammazione, idUtente;

    /**
     * Tipi di oggetti della recyclerview
     */
    public static final int POSTO_LIBERO = 0;
    public static final int POSTO_OCCUPATO = 1;
    public static final int POSTO_PRENOTATO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posti);

        // id della programmazione passata dall'activity prima
        idProgrammazione = getIntent().getIntExtra(PROGRAMMAZIONE_EXTRA_NAME, EXTRA_DEFAULT_VALUE);
        /*if(idProgrammazione == EXTRA_DEFAULT_VALUE) {
            // errore idProgrammazione non passata
            finish();
        }*/

        // id dell'utente passata dall'activity prima
        idUtente = getIntent().getIntExtra(UTENTE_EXTRA_NAME, EXTRA_DEFAULT_VALUE);
        /*if(idUtente == EXTRA_DEFAULT_VALUE) {
            // errore idUtente non passato
            finish();
        }*/

        /*ArrayList<Film> f = Queries.getAllFilms();
        ArrayList<Programmazione> ps = Queries.getAllProgrammaziones();
        Log.d("Lista dei film", f.toString());
        Log.d("Lista program", ps.toString());
        Log.d("Program", Queries.getProgrammmazione(0).toString());*/

        // prendo i riferimenti
        final Button mBtnAvanti = findViewById(R.id.btnAvanti);
        RecyclerView r = findViewById(R.id.mainR);

        postiDaPrenotare = new ArrayList<>();
        idProgrammazione = 0;

        // sala associata
        final Sala currentSala = Queries.getSalaFromId(idProgrammazione);

        // posti già prenotati per quella programmazione
        postiPrenotati = Queries.getPostiPrenotati(idProgrammazione);

        // recyclerview set up
        GridLayoutManager recyclerGrid = new GridLayoutManager(getApplicationContext(), GRID_SPAN_COUNT);
        r.setLayoutManager(recyclerGrid);
        r.setAdapter(new PostiAdapter(currentSala, this));
        // per non far reciclare gli oggetti togglabili
        r.getRecycledViewPool().setMaxRecycledViews(POSTO_PRENOTATO, 0);
        r.getRecycledViewPool().setMaxRecycledViews(POSTO_LIBERO, 0);

        //btn avanti --> registro la prenotazione nel database
        mBtnAvanti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // registro la PRENOTAZIONE
                long idPrenotazione = Queries.addPrenotazione(new Prenotazione(0,idProgrammazione, idUtente));

                // faccio una lista di posti prenotati
                ArrayList<PostoPrenotato> ps = new ArrayList<>();
                for(Integer i : postiDaPrenotare) {
                    PostoPrenotato p = new PostoPrenotato(0, (int) idPrenotazione, i);
                    Queries.addPostoPrenotato(p);
                }

                mBtnAvanti.setClickable(false);
            }
        });
    }

    /**
     * Adapter della RecyclerView
     */
    class PostiAdapter extends RecyclerView.Adapter<PostiAdapter.AdapterViewHolder> {
        private Sala s;
        private Context cx;

        PostiAdapter(Sala s, Context cx) {
            this.s = s;
            this.cx = cx;
        }

        @Override
        public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            AdapterViewHolder viewHolder = null;

            switch (viewType) {
                case POSTO_LIBERO:
                    viewHolder = new AdapterViewHolder(LayoutInflater.from(parent.getContext())
                                                        .inflate(R.layout.posti_list_item, null));
                    viewHolder.isOccupato = false;
                    viewHolder.onClick();
                    break;
                case POSTO_OCCUPATO:
                    viewHolder = new AdapterViewHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.posti_list_item, null));
                    // metto l'icona occupata
                    viewHolder.icon.setImageResource(R.drawable.ic_cadrega_occupata);
                    break;
                case POSTO_PRENOTATO:
                    viewHolder = new AdapterViewHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.posti_list_item, null));
                    // metto il colore occupato
                    viewHolder.icon.setImageResource(R.drawable.ic_cadrega);
                    DrawableCompat.setTint(viewHolder.icon.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                    viewHolder.isOccupato = true;
                    viewHolder.onClick();
                    break;
            }

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(AdapterViewHolder holder, int position) {
            // passo l'id del posto
            holder.id = position+1;
        }

        @Override
        public int getItemCount() {
            return s.getnPosti();
        }

        @Override
        public int getItemViewType(int position) {
            return getPostoType(position+1);
        }

        /**
         * ViewHolder
         */
        class AdapterViewHolder extends RecyclerView.ViewHolder {
            int id;
            ImageView icon;
            boolean isOccupato;

            AdapterViewHolder(View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.iv);
            }

            /**
             * Per imposta un click listener per l'elemento
             */
            void onClick() {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isOccupato) {
                            // se ha gia selezionato 4 posti
                            if(postiDaPrenotare.size() == 4) {
                                Snackbar snack = Snackbar.make(findViewById(R.id.main_container),
                                        R.string.error_posti_max, Snackbar.LENGTH_LONG);
                                snack.show();
                                return;
                            }

                            // lo metto occupato
                            icon.setImageResource(R.drawable.ic_cadrega);
                            DrawableCompat.setTint(icon.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

                            // lo aggiungo alla lista
                            postiDaPrenotare.add(id);
                        }
                        else {
                            // lo mettono libero
                            icon.setImageResource(R.drawable.ic_cadrega_libera);

                            // lo rimuovo alla lista
                            postiDaPrenotare.remove(Integer.valueOf(id));
                        }

                        // toggle dell'occupato
                        isOccupato = !isOccupato;
                    }
                });
            }
        }

        /**
         * Metodo che verifica se il posto attuale èoccupato
         * @param nPosto numero di posto attuale
         * @return un tipo di robe
         */
        private int getPostoType(int nPosto) {
            // posti gia prenotati BLOCCATII
            for(Integer p : postiPrenotati) {
                if(nPosto == p)
                    return POSTO_OCCUPATO;
            }

            // posti prenotati ora
            for(Integer p : postiDaPrenotare) {
                if(nPosto == p)
                    return POSTO_PRENOTATO;
            }

            return POSTO_LIBERO;
        }
    }
}
