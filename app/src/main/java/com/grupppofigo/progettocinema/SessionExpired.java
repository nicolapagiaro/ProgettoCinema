package com.grupppofigo.progettocinema;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grupppofigo.progettocinema.login.LoginActivity;

public class SessionExpired extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_expired);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Button btn = findViewById(R.id.btnBackLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }
}
