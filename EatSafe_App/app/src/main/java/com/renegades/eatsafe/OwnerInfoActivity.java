package com.renegades.eatsafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class OwnerInfoActivity extends AppCompatActivity {
    String name,dob,gender,state;
    TextView tvName,tvGender,tvState,tvDoB;
    Button btSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_info);
        tvName = findViewById(R.id.tvName);
        tvDoB = findViewById(R.id.tvDoB);
        tvGender = findViewById(R.id.tvGender);
        tvState = findViewById(R.id.tvState);
        btSubmit = findViewById(R.id.btSubmit);
        String lang = Locale.getDefault().getLanguage();

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OwnerInfoActivity.this,OwnerBTActivity.class));
            }
        });

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        dob = intent.getStringExtra("yob");
        gender = intent.getStringExtra("gender");
        state = intent.getStringExtra("state");

        if(lang == "en") {
            if (gender.contains("M")) {
                tvGender.setText("Gender - Male");
            } else if (gender.contains("F")) {
                tvGender.setText("Gender - Female");
            }

            tvName.setText("Name - " + name);
            tvState.setText("State - " + state);
            tvDoB.setText("YoB - " + dob);

        }else if(lang == "hi"){
            if (gender.contains("M")) {
                tvGender.setText("लिंग - पुरुष");
            } else if (gender.contains("F")) {
                tvGender.setText("लिंग - महिला");
            }

            tvName.setText("नाम - " + name);
            tvState.setText("राज्य - " + state);
            tvDoB.setText("जन्म का साल - " + dob);
        }



    }
}
