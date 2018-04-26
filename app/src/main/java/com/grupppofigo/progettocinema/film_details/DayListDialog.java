package com.grupppofigo.progettocinema.film_details;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.grupppofigo.progettocinema.R;

import java.util.ArrayList;

/**
 * Dialog per mostrare i giorni disponibili da prenotare
 * Created by jack on 26/04/18.
 */
class DayListDialog extends Dialog {
    private DescrizioneActivity vContext;
    private String[] vDates;

    /**
     * Coostrutto del dialog
     * @param aContext context
     * @param aItem lista di giorni
     */
    DayListDialog(Context aContext, final String[] aItem) {
        super(aContext);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.time_layout);

        ListView mData = findViewById(R.id.lv_time);
        vContext = (DescrizioneActivity) aContext;
        vDates = aItem;

        final ArrayAdapter<String> vAdapter = new ArrayAdapter<>(aContext, android.R.layout.simple_list_item_1, aItem);
        mData.setAdapter(vAdapter);
        mData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(vContext != null) {
                    vContext.setvDateSelected(true);
                    vContext.changeDate(vDates[position]);
                    vContext.getvDayListDialog().dismiss();
                }
            }
        });
    }
}
