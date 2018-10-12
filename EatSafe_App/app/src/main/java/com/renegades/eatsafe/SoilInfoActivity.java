package com.renegades.eatsafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SoilInfoActivity extends AppCompatActivity {

    TextView tvTemp,tvHumidity,tvPressure,tvSoilMoisture,tvAirQuality;
    Button btResult;

    ProgressDialog progressDialog;
    Snackbar snackbar;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_info);

        coordinatorLayout = findViewById(R.id.coordinator);
        snackbar = Snackbar.make(coordinatorLayout,getString(R.string.soli_is)+""+getString(R.string.fertile),Snackbar.LENGTH_LONG)
                .setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(SoilInfoActivity.this,MenuActivity.class));
                        finish();
                    }
                });
        View sbView = snackbar.getView();
        snackbar.setActionTextColor(Color.BLACK);
        sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_orange_dark));


        progressDialog = new ProgressDialog(this);
        tvTemp = findViewById(R.id.tvTemperature);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvPressure = findViewById(R.id.tvPressure);
        tvSoilMoisture = findViewById(R.id.tvSoilMoisture);
        tvAirQuality = findViewById(R.id.tvAirQuality);

        btResult = findViewById(R.id.btResult);
        btResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();
                        snackbar.show();


                    }
                },4000);

                progressDialog.setMessage(getString(R.string.gathering_result));
                progressDialog.show();
            }
        });
    }
}
