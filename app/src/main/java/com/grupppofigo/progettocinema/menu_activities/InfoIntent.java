package com.grupppofigo.progettocinema.menu_activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.grupppofigo.progettocinema.R;

public class InfoIntent extends AppCompatActivity {

    TextView textViewVersion;
    PackageInfo pInfo;
    String version;
    int verCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_intent);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        textViewVersion = findViewById(R.id.textViewVersion);
        try {
            pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        verCode = pInfo.versionCode;

        textViewVersion.setText("Versione "+verCode);
    }
}
