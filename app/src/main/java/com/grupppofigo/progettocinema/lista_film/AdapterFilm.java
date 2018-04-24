package com.grupppofigo.progettocinema.lista_film;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.entities.Film;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter per la listview di film
 */
public class AdapterFilm extends ArrayAdapter<Film>{
    private Activity ctx;
    private int resID;
    private List<Film> listaFilm;

    AdapterFilm(@NonNull Context context, int resource, @NonNull List<Film> objects) {
        super(context, resource, objects);
        ctx = (Activity) context;
        resID = resource;
        listaFilm = objects;
    }


    @NonNull
    @Override //getView è un metodo della classe ArrayAdapter, converte ogni singola view
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        FilmViewHolder viewHolder;

        if (r == null) {
            LayoutInflater layoutInflater = ctx.getLayoutInflater();
            r = layoutInflater.inflate(resID, null);
            viewHolder = new FilmViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (FilmViewHolder) r.getTag();
        }

        // riempio le textview
        Film f = listaFilm.get(position);
        viewHolder.tv_titolo.setText(f.getTitolo());
        viewHolder.tv_genere.setText(f.getGenere().getNome());
        viewHolder.tv_durata.setText(ctx.getString(R.string.tvDurataFilm, f.getDurata()));
        Picasso.get().load(f.getImmagine()).into(viewHolder.iv_film);
        viewHolder.rb_stelle.setRating(f.getVoto());

        return r;
    }

    /**
     * ViewHolder per la ListView dei film
     */
    class FilmViewHolder {
        protected TextView tv_titolo;
        protected TextView tv_genere;
        protected  TextView tv_durata;
        protected ImageView iv_film;
        protected RatingBar rb_stelle;

        FilmViewHolder(View v) {
            tv_titolo = v.findViewById(R.id.tv_titolo);
            tv_genere = v.findViewById(R.id.tv_genere);
            tv_durata = v.findViewById(R.id.tv_durata);
            iv_film = v.findViewById(R.id.iv_film);
            rb_stelle = v.findViewById(R.id.rb_stelle);
        }
    }

}
