package com.renegades.eatsafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QualityResultActivity extends AppCompatActivity {

    TextView tvGrade;
    Button btDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_result);

        tvGrade = findViewById(R.id.tvGrade);
        btDone = findViewById(R.id.btDone);

        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QualityResultActivity.this,MenuActivity.class));
                finish();
            }
        });
    }
}
