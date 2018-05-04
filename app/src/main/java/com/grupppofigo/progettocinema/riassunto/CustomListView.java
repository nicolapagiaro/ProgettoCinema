package com.grupppofigo.progettocinema.riassunto;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.entities.PostoPrenotato;
import com.grupppofigo.progettocinema.entities.Sala;

import java.util.ArrayList;
import java.util.List;

public class CustomListView extends ArrayAdapter<Integer> {
    private Activity context;
    private ArrayList<Integer> posti;
    private Sala sala;
    private int resource;

    /**
     * Costruttore parametrico
     * @param context context dell'activity
     * @param resource layout da mostrare
     * @param objects lista di oggetti
     */
    public CustomListView(@NonNull Context context, int resource, @NonNull List<Integer> objects, Sala sala) {
        super(context, resource, objects);
        this.context = (Activity) context;
        posti = (ArrayList<Integer>) objects;
        this.resource = resource;
        this.sala = sala;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder;

        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(resource, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }

        // mostro le info nelle textview
        viewHolder.tvPosto.setText(context.getString(R.string.tvNumeroPosto, posti.get(position)));
        //viewHolder.tvSala.setText(sala.getNome());

        return r;
    }

    /**
     * ViewHolder per la ListView
     */
    class ViewHolder {
        TextView tvPosto;
        TextView tvSala;

        ViewHolder(View v) {
            tvPosto = v.findViewById(R.id.tvPosto);
            tvSala = v.findViewById(R.id.tvSala);
        }
    }
}