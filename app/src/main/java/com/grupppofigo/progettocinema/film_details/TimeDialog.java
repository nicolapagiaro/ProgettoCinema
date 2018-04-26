package com.grupppofigo.progettocinema.film_details;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.grupppofigo.progettocinema.R;

/**
 * Dialog che mostra l'orario da selezionare
 * Created by jack on 26/04/18.
 */
class TimeDialog extends Dialog {
    private DescrizioneActivity vContext;
    private String[] vData;

    TimeDialog(Context aContext, String[] aItem) {
        super(aContext);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.time_layout);

        vContext = (DescrizioneActivity) aContext;
        ArrayAdapter<String> vAdapter = new ArrayAdapter<>(aContext, android.R.layout.simple_list_item_1, aItem);
        vData = aItem;
        ListView mOre = findViewById(R.id.lv_time);
        mOre.setAdapter(vAdapter);
        mOre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vContext.changeOra(vData[position]);
                vContext.getvTimeDialog().dismiss();
            }
        });
    }
}
