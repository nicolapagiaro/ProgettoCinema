package com.grupppofigo.progettocinema.film_details;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.grupppofigo.progettocinema.R;
import com.grupppofigo.progettocinema.entities.Programmazione;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Dialog per mostrare i giorni disponibili da prenotare
 * Created by jack on 26/04/18.
 */
class DayListDialog extends Dialog {
    private DescrizioneActivity vContext;

    /**
     * Coostrutto del dialog
     * @param aContext context
     * @param progs lista di giorni
     */
    DayListDialog(Context aContext, final ArrayList<Programmazione> progs) {
        super(aContext);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.time_layout);

        ListView mData = findViewById(R.id.lv_time);
        vContext = (DescrizioneActivity) aContext;
        ArrayList<Programmazione> vDates = progs;

        final String[] progsText = new String[vDates.size()];
        SimpleDateFormat format = new SimpleDateFormat("dd MMM", Locale.ITALY);
        SimpleDateFormat parse = new SimpleDateFormat("dd/mm/yyyy", Locale.ITALY);
        for(int i = 0; i<progs.size(); i++) {
            try {
                progsText[i] = format.format(parse.parse(vDates.get(i).getData()));
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
                    vContext.setvDateSelected(true);
                    vContext.changeDate(progsText[position]);
                    vContext.getvDayListDialog().dismiss();
                }
            }
        });
    }
}
