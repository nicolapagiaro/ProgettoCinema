package com.grupppofigo.progettocinema;

import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grupppofigo.progettocinema.database.Queries;
import com.grupppofigo.progettocinema.entities.Film;
import com.grupppofigo.progettocinema.entities.Programmazione;
import com.grupppofigo.progettocinema.entities.Sala;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Film> f = Queries.getAllFilms();
        ArrayList<Sala> s = Queries.getAllSalas();
        ArrayList<Programmazione> ps = Queries.getAllProgrammaziones();

        Log.d("Lista dei film", f.toString());
        Log.d("Lista delle sale", s.toString());
        Log.d("Lista program", ps.toString());
        Log.d("Program", Queries.getProgrammmazione(0).toString());

        // provo la recyclerview
        RecyclerView r = findViewById(R.id.mainR);
        r.setLayoutManager(new GridLayoutManager(getApplicationContext(), 8));
        r.setAdapter(new Adpter(s.get(0)));
    }

    /**
     * Adapter
     */
    class Adpter extends RecyclerView.Adapter<Adpter.AdapterViewHolder> {
        private Sala s;

        Adpter(Sala s) {
            this.s = s;
        }

        @Override
        public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AdapterViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, null, false));
        }

        @Override
        public void onBindViewHolder(AdapterViewHolder holder, int position) {
            holder.id = position+1;
            holder.onClick();
        }

        @Override
        public int getItemCount() {
            return s.getnPosti();
        }

        /**
         * ViewHolder
         */
        class AdapterViewHolder extends RecyclerView.ViewHolder {
            int id;
            ImageView imageView;

            AdapterViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.iv);
            }

            void onClick() {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageView.setImageResource(R.drawable.ic_cadrega);
                        DrawableCompat.setTint(imageView.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.red));
                    }
                });
            }
        }
    }
}
