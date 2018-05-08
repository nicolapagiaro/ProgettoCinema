package com.grupppofigo.progettocinema.prenotazione_posti;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.entities.Sala;

import java.util.ArrayList;

/**
 * Adapter della RecyclerView
 */
class PostiAdapter extends RecyclerView.Adapter<PostiAdapter.AdapterViewHolder> {
    /**
     * Tipi di oggetti della recyclerview
     */
    static final int POSTO_LIBERO = 0;
    private static final int POSTO_OCCUPATO = 1;
    static final int POSTO_PRENOTATO = 2;

    /**
     * Cose dell'adapter
     */
    private Sala s;
    private AppCompatActivity cx;
    private ArrayList<Integer> postiDaPrenotare;
    private ArrayList<Integer> postiPrenotati;

    public PostiAdapter(Sala s, AppCompatActivity cx, ArrayList<Integer> postiDaPrenotare, ArrayList<Integer> postiPrenotati) {
        this.s = s;
        this.cx = cx;
        this.postiDaPrenotare = postiDaPrenotare;
        this.postiPrenotati = postiPrenotati;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterViewHolder viewHolder = null;

        switch (viewType) {
            case POSTO_LIBERO:
                viewHolder = new PostiAdapter.AdapterViewHolder(LayoutInflater.from(parent.getContext())
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
                DrawableCompat.setTint(viewHolder.icon.getDrawable(), ContextCompat.getColor(cx, R.color.colorAccent));
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
        TextView tv_numeroPosti;

        AdapterViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv);
            tv_numeroPosti = itemView.findViewById(R.id.tv_numeroPosto);
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
                            Snackbar snack = Snackbar.make(cx.findViewById(R.id.main_container),
                                    R.string.error_posti_max, Snackbar.LENGTH_LONG);
                            snack.show();
                            return;
                        }

                        // lo metto occupato
                        icon.setImageResource(R.drawable.ic_cadrega);
                        DrawableCompat.setTint(icon.getDrawable(), ContextCompat.getColor(cx, R.color.colorAccent));
                        // si vede id posto
                        tv_numeroPosti.setText(id+"");

                        // lo aggiungo alla lista
                        postiDaPrenotare.add(id);
                    }
                    else {
                        // lo mettono libero
                        icon.setImageResource(R.drawable.ic_cadrega_libera);
                        // nascondo id posto
                        tv_numeroPosti.setText("");

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