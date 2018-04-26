package com.grupppofigo.progettocinema.film_details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.entities.Film;

import org.w3c.dom.Text;

/**
 * Fragment per vedere le informazioni del film
 * Created by jack on 11/04/18.
 */
public class FragmentDesc extends Fragment {
    private Film vFilm;

    public FragmentDesc() {
    }

    @SuppressLint("ValidFragment")
    public FragmentDesc(Film aFilm) {
        vFilm = aFilm;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(container == null)
            return null;

        View vView = inflater.inflate(R.layout.fragment_info, null);
        Context vContext = container.getContext();
        TextView mTitolo = vView.findViewById(R.id.txt_titolo);
        TextView mGenere = vView.findViewById(R.id.txt_genere);
        TextView mDescrizione = vView.findViewById(R.id.txt_descr);
        TextView mDurata = vView.findViewById(R.id.txt_durata);

        mTitolo.setText(vFilm.getTitolo());
        mGenere.setText(vFilm.getGenere().getNome());
        mDurata.setText(getString(R.string.tvDurataFilm, vFilm.getDurata()));
        mDescrizione.setText(vFilm.getDescrizione());

        return vView;
    }

}

