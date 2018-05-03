package com.grupppofigo.progettocinema.film_details;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.entities.Giorno;
import com.grupppofigo.progettocinema.entities.Programmazione;
import com.grupppofigo.progettocinema.helpers.DateParser;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Dialog per mostrare i giorni disponibili da prenotare
 * Created by jack on 26/04/18.
 */
class DayListDialog extends Dialog {
    private DescrizioneActivity vContext;

    /**
     * Coostrutto del dialog
     * @param aContext context
     * @param adate lista di giorni
     */
    DayListDialog(Context aContext, final ArrayList<Giorno> adate) {
        super(aContext);
        setContentView(R.layout.time_layout);
        setTitle(R.string.dialog_day_title);

        ListView mData = findViewById(R.id.lv_time);
        vContext = (DescrizioneActivity) aContext;

        if (adate == null) {
            dismiss();
        }

        final String[] progsText = new String[adate.size()];
        for(int i = 0; i<adate.size(); i++) {
            try {
                progsText[i] = DateParser.getFormattedDate(adate.get(i).getData());
            } catch (ParseException e) {
                progsText[i] = "";
            }
        }

        final ArrayAdapter<String> vAdapter = new ArrayAdapter<>(aContext, android.R.layout.simple_list_item_1, progsText);
        mData.setAdapter(vAdapter);
        mData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(vContext != null && vContext instanceof DescrizioneActivity) {
                    vContext.changeDate(progsText[position]);
                    vContext.getvDayListDialog().dismiss();
                    vContext.setSelectedDate(adate.get(position).getData(), position);
                }
            }
        });
    }
}
