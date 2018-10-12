package com.renegades.eatsafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btFertility,btQuality,btHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btQuality = findViewById(R.id.btQuality);
        btFertility = findViewById(R.id.btFertility);
        btHistory = findViewById(R.id.btHistory);

        btFertility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,BTActivity.class));
            }
        });

        btQuality.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,OwnerAadharActivity.class));
            }
        });
    }
}
